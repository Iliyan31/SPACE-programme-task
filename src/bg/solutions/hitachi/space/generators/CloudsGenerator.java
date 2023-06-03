package bg.solutions.hitachi.space.generators;

import bg.solutions.hitachi.space.criteria.CloudsConfigurator;
import bg.solutions.hitachi.space.entities.DayWeatherForecast;

import java.util.List;

public class CloudsGenerator {
    private static final int NO_APPROPRIATE_DAY_TO_LAUNCH = -1;
    private final List<DayWeatherForecast> forecasts;
    private final CloudsConfigurator cloudsConfigurator;

    public CloudsGenerator(List<DayWeatherForecast> dayWeatherForecasts, CloudsConfigurator cloudsConfigurator) {
        this.forecasts = dayWeatherForecasts;
        this.cloudsConfigurator = cloudsConfigurator;
    }

    public int getPerfectDay() {
        return forecasts.stream()
            .filter(this::areCloudsSuitable)
            .mapToInt(DayWeatherForecast::dayNumber)
            .findFirst()
            .orElse(NO_APPROPRIATE_DAY_TO_LAUNCH);
    }

    private boolean areCloudsSuitable(DayWeatherForecast dayWeatherForecast) {
        return cloudsConfigurator.areCloudsSuitable(dayWeatherForecast);
    }
}