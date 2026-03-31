package br.com.pongolino.domain;

public final class SubscriberId {
    private final long value;

    private SubscriberId(final long value) {
        if  (value <= 0) throw new IllegalArgumentException("Invalid owner ID");
        this.value = value;
    }

    public static SubscriberId of(final long value) {
        return new SubscriberId(value);
    }

    public long getValue() {
        return value;
    }
}
