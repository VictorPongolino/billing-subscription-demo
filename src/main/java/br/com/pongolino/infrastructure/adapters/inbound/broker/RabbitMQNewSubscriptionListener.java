package br.com.pongolino.infrastructure.adapters.inbound.broker;

import br.com.pongolino.application.usecases.CreateSubscriptionUseCase;
import br.com.pongolino.infrastructure.adapters.inbound.dto.SubscriptionCreationRequest;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.AcknowledgableDelivery;
import reactor.rabbitmq.ConsumeOptions;
import reactor.rabbitmq.Receiver;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMQNewSubscriptionListener {
    private final CreateSubscriptionUseCase createSubscriptionUseCase;
    private final ObjectMapper objectMapper;
    private final Receiver receiver;

    @PostConstruct
    public void startListening() {
        receiver.consumeManualAck("billing.new_subscription", new ConsumeOptions())
                .flatMap(this::processMessage)
                .subscribe();
    }

    private Mono<Void> processMessage(final AcknowledgableDelivery acknowledgableDelivery) {
        return Mono.fromCallable(() -> objectMapper.readValue(acknowledgableDelivery.getBody(), SubscriptionCreationRequest.class))
                .flatMap(createSubscriptionUseCase::createSubscription)
                .doOnSuccess(result -> acknowledgableDelivery.ack())
                .doOnError(e -> {
                    log.error("Error during subscription creation", e);
                    acknowledgableDelivery.nack(false);
                })
                .then()
                .onErrorResume(e -> Mono.empty());
    }
}
