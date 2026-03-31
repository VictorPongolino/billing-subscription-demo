package br.com.pongolino.infrastructure.config;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "billing", ignoreUnknownFields = false, ignoreInvalidFields = false)
public record BillingConfig(Processing processing) {

    public record Processing(int timeout, int batchLimitSize, Duration batchDelay) {
    }
}
