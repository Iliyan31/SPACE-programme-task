package bg.solutions.hitachi.space.enums;

public enum Constraints {
    MIN_TEMPERATURE_VALUE(2.),
    MAX_TEMPERATURE_VALUE(31.),
    MAX_WIND_SPEED(10.),
    MAX_HUMIDITY_PERCENT(60.),
    MAX_PRECIPITATION_PERCENTS(0.);

    private final double value;
    Constraints(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}