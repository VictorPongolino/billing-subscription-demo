package br.com.pongolino.application.port.outbound;

import br.com.pongolino.domain.Subscription;
import reactor.core.publisher.Mono;

public interface SendPendingSubscriptionService {
    Mono<Void> execute();
}
