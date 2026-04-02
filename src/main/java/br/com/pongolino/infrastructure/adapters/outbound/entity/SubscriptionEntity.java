package br.com.pongolino.infrastructure.adapters.outbound.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table(name = "subscription")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class SubscriptionEntity {
    @Id
    private Long id;
    @Column("type")
    private String type;
    @Column("subscriber_id")
    private Long subscriberId;
    @Column("process_state")
    private String processState;
    @Column("last_paid")
    private Instant lastPaid;
    @Column("next_billing_at")
    private Instant nextBillingAt;
    @Column("processing_started_at")
    private Instant processingStartedAt;
    @Column("billing_interval")
    private String billingPeriod;
    @Transient
    private CurrencyEntity currency;
}

