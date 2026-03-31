package br.com.pongolino.application.usecases;

import br.com.pongolino.application.port.outbound.SubscriptionRepository;
import br.com.pongolino.domain.*;
import br.com.pongolino.infrastructure.adapters.inbound.dto.SubscriptionCreationRequest;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Currency;

public final class CreateSubscriptionUseCase {
    private final SubscriptionRepository subscriptionRepository;

    public CreateSubscriptionUseCase(final SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public Mono<Subscription> createSubscription(final SubscriptionCreationRequest subscriptionCreationRequest) {
        var subscription = Subscription.create(
                SubscriptionType.of(subscriptionCreationRequest.subscriptionType()),
                SubscriberId.of(subscriptionCreationRequest.subscriberId()),
                Instant.parse(subscriptionCreationRequest.lastPaidAt()),
                Instant.parse(subscriptionCreationRequest.nextBillingAt()),
                BillingPeriod.of(subscriptionCreationRequest.billingType()),
                Money.of(
                        subscriptionCreationRequest.amount(),
                        Currency.getInstance(subscriptionCreationRequest.currencyCode())
                )
        );

        return subscriptionRepository.save(subscription);
    }
}

