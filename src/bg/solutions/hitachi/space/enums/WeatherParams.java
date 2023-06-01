package bg.solutions.hitachi.space.enums;

public enum WeatherParams {
    DAYS(0),
    TEMPERATURES(1),
    WIND_SPEEDS(2),
    HUMIDITIES(3),
    PRECIPITATIONS(4),
    LIGHTNINGS(5),
    CLOUDS(6);

    private final int value;

    WeatherParams(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}