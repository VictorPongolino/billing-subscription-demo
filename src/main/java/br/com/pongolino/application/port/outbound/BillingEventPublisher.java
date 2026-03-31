package br.com.pongolino.application.port.outbound;

import br.com.pongolino.domain.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BillingEventPublisher {
    Mono<Void> publish(Flux<Subscription> subscription);
}
