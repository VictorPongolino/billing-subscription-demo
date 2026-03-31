package br.com.pongolino.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Name;
import org.springframework.core.io.Resource;

@ConfigurationProperties(prefix = "rabbitmq")
public record RabbitMQTLSConfiguration(TLS tls, RabbitMQConnection connection) {

    record TLS(
            @Name("client_file") Resource clientSecretFile,
            @Name("client_secret") String clientSecret,
            @Name("keystore_file") Resource keyStoreFile,
            @Name("keystore_secret") String keyStoreSecret,
            @Name("protocol") String protocol) {}

    record RabbitMQConnection(@Name("host") String host, @Name("port") int port) {}
}
