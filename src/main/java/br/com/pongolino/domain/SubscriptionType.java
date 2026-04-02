package br.com.pongolino.domain;

import java.util.Objects;

public final class SubscriptionType {
    private final String type;

    private SubscriptionType(final String type) {
        this.type = Objects.requireNonNull(type, "Type must not be null");
        if (type.isBlank()) {
            throw new IllegalArgumentException("Type must not be blank");
        }
    }

    public static SubscriptionType of(final String type) {
        return new SubscriptionType(type);
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final SubscriptionType that = (SubscriptionType) o;
        return Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    @Override
    public String toString() {
        return "SubscriptionType{" +
                "type='" + type + '\'' +
                '}';
    }
}
