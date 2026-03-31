package br.com.pongolino.application.usecases;

import br.com.pongolino.application.port.outbound.BillingEventPublisher;
import br.com.pongolino.application.port.outbound.SendPendingSubscriptionService;
import br.com.pongolino.application.port.outbound.SubscriptionRepository;
import reactor.core.publisher.Mono;

public final class SendPendingSubscriptionUseCase implements SendPendingSubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final BillingEventPublisher eventPublisher;

    public SendPendingSubscriptionUseCase(final SubscriptionRepository subscriptionRepository,
                                          final BillingEventPublisher eventPublisher) {
        this.subscriptionRepository = subscriptionRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Mono<Void> execute() {
        var pendingSubscriptionPay = subscriptionRepository.lockAndMarkAsProcessingRecursively();
        return eventPublisher.publish(pendingSubscriptionPay);
    }
}
