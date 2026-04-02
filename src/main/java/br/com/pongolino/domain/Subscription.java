package br.com.pongolino.domain;

import java.time.Instant;
import java.util.Objects;

public final class Subscription {

    private final SubscriptionType subscriptionType;
    private final SubscriberId subscriberId;
    private final BillingPeriod billingPeriod;
    private final Money money;

    private ProcessState processState;
    private Instant lastPaid;
    private Instant nextBillingAt;
    private Instant processingStartedAt;

    public enum ProcessState {
        NONE,
        PROCESSING,
        PROCESSED,
        FAILED
    }

    private Subscription(final SubscriptionType subscriptionType,
                         final SubscriberId subscriberId,
                         final ProcessState processState,
                         final Instant lastPaid,
                         final Instant nextBillingAt,
                         final Instant processingStartedAt,
                         final BillingPeriod billingPeriod,
                         final Money money) {
        this.subscriptionType = Objects.requireNonNull(subscriptionType, "SubscriptionType must not be null");
        this.subscriberId = Objects.requireNonNull(subscriberId, "SubscriberId must not be null");
        this.billingPeriod = Objects.requireNonNull(billingPeriod, "BillingPeriod must not be null");
        this.money = Objects.requireNonNull(money, "Money must not be null");
        this.processState = Objects.requireNonNull(processState, "ProcessState must not be null");
        this.lastPaid = lastPaid;
        this.nextBillingAt = nextBillingAt;
        this.processingStartedAt = processingStartedAt;
    }

    public static Subscription create(
            final SubscriptionType type,
            final SubscriberId subscriberId,
            final Instant lastPaid,
            final Instant nextBillingAt,
            final BillingPeriod billingPeriod,
            final Money money
    ) {
        return new Subscription(
                type,
                subscriberId,
                ProcessState.NONE,
                lastPaid,
                nextBillingAt,
                null,
                billingPeriod,
                money
        );
    }

    public void startProcessing() {
        if (this.processState == ProcessState.PROCESSING) {
            throw new IllegalStateException("Subscription is already being processed");
        }
        this.processState = ProcessState.PROCESSING;
        this.processingStartedAt = Instant.now();
    }

    public void markAsProcessed(final Instant paidAt, final Instant nextBillingAt) {
        if (this.processState != ProcessState.PROCESSING) {
            throw new IllegalStateException("Cannot mark as processed: subscription is not being processed");
        }
        this.processState = ProcessState.PROCESSED;
        this.lastPaid = Objects.requireNonNull(paidAt, "PaidAt must not be null");
        this.nextBillingAt = Objects.requireNonNull(nextBillingAt, "NextBillingAt must not be null");
    }

    public void markAsFailed() {
        if (this.processState != ProcessState.PROCESSING) {
            throw new IllegalStateException("Cannot mark as failed: subscription is not being processed");
        }
        this.processState = ProcessState.FAILED;
    }

    public void resetProcessState() {
        this.processState = ProcessState.NONE;
        this.processingStartedAt = null;
    }

    public boolean isProcessing() {
        return this.processState == ProcessState.PROCESSING;
    }

    public boolean isDue(final Instant now) {
        return this.nextBillingAt != null && !this.nextBillingAt.isAfter(now);
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public SubscriberId getSubscriberId() {
        return subscriberId;
    }

    public ProcessState getProcessState() {
        return processState;
    }

    public Instant getLastPaid() {
        return lastPaid;
    }

    public Instant getNextBillingAt() {
        return nextBillingAt;
    }

    public Instant getProcessingStartedAt() {
        return processingStartedAt;
    }

    public BillingPeriod getBillingPeriod() {
        return billingPeriod;
    }

    public Money getMoney() {
        return money;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Subscription that = (Subscription) o;
        return Objects.equals(subscriberId, that.subscriberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subscriberId);
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "subscriptionType=" + subscriptionType +
                ", subscriberId=" + subscriberId +
                ", processState=" + processState +
                ", lastPaid=" + lastPaid +
                ", nextBillingAt=" + nextBillingAt +
                ", processingStartedAt=" + processingStartedAt +
                ", billingPeriod=" + billingPeriod +
                ", money=" + money +
                '}';
    }
}