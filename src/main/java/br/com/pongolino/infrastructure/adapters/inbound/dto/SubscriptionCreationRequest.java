package br.com.pongolino.infrastructure.adapters.inbound.dto;

import org.springframework.boot.context.properties.bind.Name;

import java.math.BigDecimal;

public record SubscriptionCreationRequest(
        @Name("subscription_type") String subscriptionType,
        @Name("subscriber_id") Long subscriberId,
        @Name("amount") BigDecimal amount,
        @Name("currency_code") String currencyCode,
        @Name("last_paid") String lastPaidAt,
        @Name("next_billing_at") String nextBillingAt,
        @Name("billing_type") String billingType) {}
