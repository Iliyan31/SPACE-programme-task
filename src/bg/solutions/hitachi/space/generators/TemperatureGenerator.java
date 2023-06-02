package bg.solutions.hitachi.space.generators;

import bg.solutions.hitachi.space.criteria.TemperatureConfigurator;
import bg.solutions.hitachi.space.entities.DayWeatherForecast;

import java.util.List;

public class TemperatureGenerator implements StatisticOperations {
    private static final int NO_APPROPRIATE_DAY_TO_LAUNCH = -1;
    private static final double NO_STATS_PROVIDED = 0.0;
    private final List<DayWeatherForecast> forecasts;

    private final TemperatureConfigurator temperatureConfigurator;

    public TemperatureGenerator(List<DayWeatherForecast> dayWeatherForecasts,
                                TemperatureConfigurator temperatureConfigurator) {
        this.forecasts = dayWeatherForecasts;
        this.temperatureConfigurator = temperatureConfigurator;
    }

    @Override
    public double findAverage() {
        return forecasts.stream()
            .mapToDouble(DayWeatherForecast::temperature)
            .average()
            .orElse(NO_STATS_PROVIDED);
    }

    @Override
    public double findMax() {
        return forecasts.stream()
            .mapToDouble(DayWeatherForecast::temperature)
            .max()
            .orElse(NO_STATS_PROVIDED);
    }

    @Override
    public double findMin() {
        return forecasts.stream()
            .mapToDouble(DayWeatherForecast::temperature)
            .min()
            .orElse(NO_STATS_PROVIDED);
    }

    @Override
    public double findMedian() {
        List<Double> newList = List.copyOf(forecasts.stream()
            .mapToDouble(DayWeatherForecast::temperature)
            .sorted()
            .boxed()
            .toList());

        int size = newList.size();
        if (size % 2 == 1) {
            return newList.get(((size + 1) / 2) - 1);
        }
        return (newList.get((size / 2) - 1) + newList.get(size / 2)) / 2;
    }

    @Override
    public int getPerfectDay() {
        return forecasts.stream()
            .filter(this::isTemperatureSuitable)
            .mapToInt(DayWeatherForecast::dayNumber)
            .findFirst()
            .orElse(NO_APPROPRIATE_DAY_TO_LAUNCH);
    }

    private boolean isTemperatureSuitable(DayWeatherForecast dayWeatherForecast) {
        return temperatureConfigurator.isTemperatureSuitable(dayWeatherForecast);
    }
}