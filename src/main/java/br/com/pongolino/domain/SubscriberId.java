package br.com.pongolino.domain;

import java.util.Objects;

public final class SubscriberId {
    private final long value;

    private SubscriberId(final long value) {
        if (value <= 0) {
            throw new IllegalArgumentException("Invalid owner ID");
        }
        this.value = value;
    }

    public static SubscriberId of(final long value) {
        return new SubscriberId(value);
    }

    public long getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final SubscriberId that = (SubscriberId) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "SubscriberId{" +
                "value=" + value +
                '}';
    }
}
