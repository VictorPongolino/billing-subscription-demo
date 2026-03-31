package br.com.pongolino.domain;

public final class BillingPeriod{
    private Unit unit;

    private BillingPeriod(final Unit unit) {
        this.unit = unit;
    }

    public static BillingPeriod of(final String unit) {
        return new BillingPeriod(Unit.valueOf(unit));
    }

    public enum Unit {
        WEEKS, MONTHS, YEARS
    }

    public Unit getPeriodUnit() {
        return unit;
    }
}
