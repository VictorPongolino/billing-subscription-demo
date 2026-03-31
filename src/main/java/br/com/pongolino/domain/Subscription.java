package br.com.pongolino.domain;

import java.time.Instant;

public final class Subscription {
    private SubscriptionType subscriptionType;
    private SubscriberId subscriberId;
    private ProcessState processState;
    private Instant lastPaid;
    private Instant nextBillingAt;
    private Instant processingStartedAt;
    private BillingPeriod billingPeriod;
    private Money money;

    public enum ProcessState {
        NONE,
        PROCESSING,
        PROCESSED,
        FAILED
    }

    private Subscription() {}

    private Subscription(final SubscriptionType subscriptionType,
                        final SubscriberId subscriberId,
                        final ProcessState processState,
                        final Instant lastPaid,
                        final Instant nextBillingAt,
                        final Instant processingStartedAt,
                        final BillingPeriod billingPeriod,
                        final Money money) {
        this.subscriptionType = subscriptionType;
        this.subscriberId = subscriberId;
        this.processState = processState;
        this.lastPaid = lastPaid;
        this.nextBillingAt = nextBillingAt;
        this.processingStartedAt = processingStartedAt;
        this.billingPeriod = billingPeriod;
        this.money = money;
    }

    public static Subscription create(
            SubscriptionType type,
            SubscriberId subscriberId,
            Instant lastPaid,
            Instant nextBillingAt,
            BillingPeriod billingPeriod,
            Money money
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

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public SubscriberId getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(SubscriberId subscriberId) {
        this.subscriberId = subscriberId;
    }

    public ProcessState getProcessState() {
        return processState;
    }

    public void setProcessState(ProcessState processState) {
        this.processState = processState;
    }

    public Instant getLastPaid() {
        return lastPaid;
    }

    public void setLastPaid(Instant lastPaid) {
        this.lastPaid = lastPaid;
    }

    public Instant getNextBillingAt() {
        return nextBillingAt;
    }

    public void setNextBillingAt(Instant nextBillingAt) {
        this.nextBillingAt = nextBillingAt;
    }

    public Instant getProcessingStartedAt() {
        return processingStartedAt;
    }

    public void setProcessingStartedAt(Instant processingStartedAt) {
        this.processingStartedAt = processingStartedAt;
    }

    public BillingPeriod getBillingPeriod() {
        return billingPeriod;
    }

    public void setBillingPeriod(BillingPeriod billingPeriod) {
        this.billingPeriod = billingPeriod;
    }

    public Money getMoney() {
        return money;
    }

    public void setMoney(Money money) {
        this.money = money;
    }
}