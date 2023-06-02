package bg.solutions.hitachi.space.generators;

import bg.solutions.hitachi.space.criteria.LightningsConfigurator;
import bg.solutions.hitachi.space.entities.DayWeatherForecast;

import java.util.List;

public class LightningsGenerator {
    private final List<DayWeatherForecast> forecasts;

    private final LightningsConfigurator lightningsConfigurator;

    public LightningsGenerator(List<DayWeatherForecast> dayWeatherForecasts,
                               LightningsConfigurator lightningsConfigurator) {
        this.forecasts = dayWeatherForecasts;
        this.lightningsConfigurator = lightningsConfigurator;
    }

    public int getPerfectDay() {
        return forecasts.stream()
            .filter(this::areThereNoLightnings)
            .mapToInt(DayWeatherForecast::dayNumber)
            .findFirst()
            .orElse(-1);
    }

    public boolean areThereNoLightnings(DayWeatherForecast dayWeatherForecast) {
        return lightningsConfigurator.areThereNoLightnings(dayWeatherForecast);
    }
}