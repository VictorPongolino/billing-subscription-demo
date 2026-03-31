package br.com.pongolino.infrastructure.config;

import br.com.pongolino.application.port.outbound.SubscriptionRepository;
import br.com.pongolino.application.usecases.CreateSubscriptionUseCase;
import br.com.pongolino.application.usecases.SendPendingSubscriptionUseCase;
import br.com.pongolino.infrastructure.adapters.outbound.broker.RabbitMQProcessSubscriptionSender;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class UseCaseConfig {

    @Bean
    public CreateSubscriptionUseCase createSubscriptionUseCase(final SubscriptionRepository springDataSubscriptionRepository) {
        return new CreateSubscriptionUseCase(springDataSubscriptionRepository);
    }

    @Bean
    public SendPendingSubscriptionUseCase sendPendingSubscriptionUseCase(final SubscriptionRepository springDataSubscriptionRepository,
                                                                         final RabbitMQProcessSubscriptionSender processSubscriptionSender) {
        return new SendPendingSubscriptionUseCase(springDataSubscriptionRepository, processSubscriptionSender);
    }

}
