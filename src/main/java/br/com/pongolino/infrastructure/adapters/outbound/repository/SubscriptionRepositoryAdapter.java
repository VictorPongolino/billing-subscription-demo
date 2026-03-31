package br.com.pongolino.infrastructure.adapters.outbound.repository;

import br.com.pongolino.application.port.outbound.SubscriptionRepository;
import br.com.pongolino.domain.Subscription;
import br.com.pongolino.infrastructure.adapters.outbound.entity.SubscriptionEntity;
import br.com.pongolino.infrastructure.adapters.outbound.mappers.SubscriptionMapper;
import br.com.pongolino.infrastructure.config.BillingConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SubscriptionRepositoryAdapter implements SubscriptionRepository {
    private final SpringDataSubscriptionRepository springDataSubscriptionRepository;
    private final TransactionalOperator transactionalOperator;
    private final SubscriptionMapper subscriptionMapper;
    private final BillingConfig billingConfig;

    @Override
    public Mono<Subscription> save(final Subscription subscription) {
        var entity = subscriptionMapper.toEntity(subscription);
        return springDataSubscriptionRepository.save(entity)
                .map(subscriptionMapper::toDomain)
                .as(transactionalOperator::transactional);
    }

    @Override
    public Flux<Subscription> lockAndMarkAsProcessingRecursively() {
        var timeout = billingConfig.processing().timeout();
        var limit = billingConfig.processing().batchLimitSize();
        var batchDelay = billingConfig.processing().batchDelay();

        return lockAndMarkAsProcessing(timeout, limit)
                .repeatWhen(completed ->
                        completed.flatMap(signal -> Mono.delay(batchDelay))
                );
    }

    private Flux<Subscription> lockAndMarkAsProcessing(int timeout, int limit) {
        return transactionalOperator.transactional(
                springDataSubscriptionRepository.lockSubscriptions(timeout, limit)
                        .collectList()
                        .flatMapMany(subscriptions -> {
                            var subscriptionsIds = subscriptions.stream().map(SubscriptionEntity::getId).toList();
                            if (subscriptions.isEmpty()) {
                                return Flux.empty();
                            }

                            return springDataSubscriptionRepository.markAsProcessing(subscriptionsIds)
                                    .thenMany(Flux.fromIterable(subscriptions));
                        })
                        .map(subscriptionMapper::toDomain)
        );
    }
}

