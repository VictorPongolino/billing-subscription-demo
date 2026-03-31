package br.com.pongolino.infrastructure.config;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.rabbitmq.*;

@Component
public class RabbitMQConfig {

    @Bean
    public Receiver receiver(final ConnectionFactory connectionFactory) {
        return RabbitFlux.createReceiver(new ReceiverOptions()
                .connectionFactory(connectionFactory));
    }

    @Bean
    public Sender sender(final ConnectionFactory connectionFactory) {
        return RabbitFlux.createSender(new SenderOptions()
                .connectionFactory(connectionFactory)
        );
    }

}
