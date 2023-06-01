package bg.solutions.hitachi.space.generators;

import bg.solutions.hitachi.space.entities.DayWeatherForecast;

import java.util.List;

public class LightningsGenerator {
    private final List<DayWeatherForecast> forecasts;

    public LightningsGenerator(List<DayWeatherForecast> dayWeatherForecasts) {
        this.forecasts = dayWeatherForecasts;
    }

    public int getPerfectDay() {
        return forecasts.stream()
            .filter(this::areThereNoLightnings)
            .mapToInt(DayWeatherForecast::dayNumber)
            .findFirst()
            .orElse(-1);
    }

    public boolean areThereNoLightnings(DayWeatherForecast dayWeatherForecast) {
        return !dayWeatherForecast.lightning();
    }
}