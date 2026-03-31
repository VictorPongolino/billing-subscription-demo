package br.com.pongolino.domain;

public final class SubscriptionType {
    private final String type;

    private SubscriptionType(final String type) {
        this.type = type;
    }

    public static SubscriptionType of(String type) {
        return new  SubscriptionType(type);
    }

    public String getType() {
        return type;
    }
}
