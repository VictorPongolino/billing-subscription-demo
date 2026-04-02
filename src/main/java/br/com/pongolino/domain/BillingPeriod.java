package br.com.pongolino.domain;

import java.util.Objects;

public final class BillingPeriod {

    private final Unit unit;

    private BillingPeriod(final Unit unit) {
        this.unit = Objects.requireNonNull(unit, "Unit must not be null");
    }

    public static BillingPeriod of(final String unit) {
        Objects.requireNonNull(unit, "Unit must not be null");
        return new BillingPeriod(Unit.valueOf(unit));
    }

    public static BillingPeriod of(final Unit unit) {
        return new BillingPeriod(unit);
    }

    public Unit getPeriodUnit() {
        return unit;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final BillingPeriod that = (BillingPeriod) o;
        return unit == that.unit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(unit);
    }

    @Override
    public String toString() {
        return "BillingPeriod{" +
                "unit=" + unit +
                '}';
    }

    public enum Unit {
        WEEKS, MONTHS, YEARS
    }
}
