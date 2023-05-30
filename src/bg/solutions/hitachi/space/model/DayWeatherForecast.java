package bg.solutions.hitachi.space.model;

import bg.solutions.hitachi.space.enums.Cloud;

public record DayWeatherForecast(int dayNumber, double temperature, double windSpeed, double humidity,
                                 double precipitation, boolean lightning, Cloud clouds) {
    public DayWeatherForecast {
        // add logic
    }
}
