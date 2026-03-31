package br.com.pongolino.infrastructure.config;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeoutException;

@Configuration
public class ConnectionConfig {

    @Bean
    public com.rabbitmq.client.Connection createConnection(final RabbitMQTLSConfiguration rabbitMQTLSConfiguration) throws GeneralSecurityException, IOException, TimeoutException {
        var tls = rabbitMQTLSConfiguration.tls();
        var connection = rabbitMQTLSConfiguration.connection();

        var keyManagers = createKeyManagers(
            tls.clientSecretFile(),
            tls.clientSecret().toCharArray(),
        "PKCS12"
        );

        var trustManagers = createTrustManagers(
            tls.keyStoreFile(),
            tls.keyStoreSecret().toCharArray(),
        "JKS"
        );

        var sslContext = SSLContext.getInstance(tls.protocol());
        sslContext.init(keyManagers, trustManagers, null);

        ConnectionFactory cf = new ConnectionFactory();
        cf.setHost(connection.host());
        cf.setPort(connection.port());
        cf.useSslProtocol(sslContext);
        cf.enableHostnameVerification();

        return cf.newConnection();
    }

    private KeyManager[] createKeyManagers(final Resource resource, final char[] password, final String type) throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException {
        var keyStore = KeyStore.getInstance(type);
        try (var is = resource.getInputStream()) {
            keyStore.load(is, password);
        }

        var kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(keyStore, password);
        return kmf.getKeyManagers();
    }

    private TrustManager[] createTrustManagers(final Resource resource, final char[] password, final String type) throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException {
        var keyStore = KeyStore.getInstance(type);
        try (var is = resource.getInputStream()) {
            keyStore.load(is, password);
        }

        var kmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        kmf.init(keyStore);
        return kmf.getTrustManagers();
    }
}