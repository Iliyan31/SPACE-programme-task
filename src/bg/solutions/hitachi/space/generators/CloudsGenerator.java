package bg.solutions.hitachi.space.generators;

import bg.solutions.hitachi.space.entities.DayWeatherForecast;
import bg.solutions.hitachi.space.enums.Cloud;

import java.util.List;

public class CloudsGenerator {
    private final List<DayWeatherForecast> forecasts;

    public CloudsGenerator(List<DayWeatherForecast> dayWeatherForecasts) {
        this.forecasts = dayWeatherForecasts;
    }

    public int getPerfectDay() {
        return forecasts.stream()
            .filter(this::areCloudsSuitable)
            .mapToInt(DayWeatherForecast::dayNumber)
            .findFirst()
            .orElse(-1);
    }

    private boolean areCloudsSuitable(DayWeatherForecast dayWeatherForecast) {
        return dayWeatherForecast.clouds() == Cloud.CIRRUS || dayWeatherForecast.clouds() == Cloud.STRATUS;
    }
}