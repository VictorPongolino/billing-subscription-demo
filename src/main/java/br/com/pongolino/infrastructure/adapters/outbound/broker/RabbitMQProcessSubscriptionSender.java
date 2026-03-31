package br.com.pongolino.infrastructure.adapters.outbound.broker;

import br.com.pongolino.application.port.outbound.BillingEventPublisher;
import br.com.pongolino.domain.Subscription;
import com.rabbitmq.client.ShutdownSignalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.rabbitmq.*;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public final class RabbitMQProcessSubscriptionSender implements BillingEventPublisher {
    private final Sender sender;
    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> publish(final Flux<Subscription> subscriptions) {
        var sendOptions = getRetryOptions();

        return sender.sendWithPublishConfirms(convertPayloadOutboundMessage(subscriptions), sendOptions)
                .handle((result, sink) -> {
                    if (!result.isAck()) {
                        sink.error(new RuntimeException("Received NACK. Payload=" + result));
                    }
                })
                .then();
    }

    private Flux<OutboundMessage> convertPayloadOutboundMessage(Flux<Subscription> subscriptions) {
        return subscriptions.flatMap(subscription ->
                Mono.fromCallable(() -> objectMapper.writeValueAsBytes(subscription))
                        .subscribeOn(Schedulers.boundedElastic())
                        .onErrorMap(throwable -> new RuntimeException("Failed to serialize payload"))
                        .map(payload ->
                                new OutboundMessage("ex.billing_charge",
                                        subscription.getSubscriptionType().getType(),
                                        payload)
                        )
        );
    }

    private SendOptions getRetryOptions() {
        return new SendOptions()
                .exceptionHandler(new ExceptionHandlers.RetrySendingExceptionHandler(
                        Duration.ofSeconds(30),
                        Duration.ofMillis(1000),
                        ex -> ex instanceof IOException || ex instanceof ShutdownSignalException || ex instanceof RabbitFluxException
                ));
    }
}
