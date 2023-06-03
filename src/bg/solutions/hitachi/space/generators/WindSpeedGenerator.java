package bg.solutions.hitachi.space.generators;

import bg.solutions.hitachi.space.criteria.WindSpeedConfigurator;
import bg.solutions.hitachi.space.entities.DayWeatherForecast;

import java.util.Comparator;
import java.util.List;

public class WindSpeedGenerator implements StatisticOperations {
    private static final int NO_APPROPRIATE_DAY_TO_LAUNCH = -1;
    private final List<DayWeatherForecast> forecasts;

    private final WindSpeedConfigurator windSpeedConfigurator;

    public WindSpeedGenerator(List<DayWeatherForecast> dayWeatherForecasts,
                              WindSpeedConfigurator windSpeedConfigurator) {
        this.forecasts = dayWeatherForecasts;
        this.windSpeedConfigurator = windSpeedConfigurator;
    }

    @Override
    public double findAverage() {
        return forecasts.stream()
            .mapToDouble(DayWeatherForecast::windSpeed)
            .average()
            .orElse(0.0);
    }

    @Override
    public double findMax() {
        return forecasts.stream()
            .mapToDouble(DayWeatherForecast::windSpeed)
            .max()
            .orElse(0.0);
    }

    @Override
    public double findMin() {
        return forecasts.stream()
            .mapToDouble(DayWeatherForecast::windSpeed)
            .min()
            .orElse(0.0);
    }

    @Override
    public double findMedian() {
        List<Double> newList = List.copyOf(forecasts.stream()
            .mapToDouble(DayWeatherForecast::windSpeed)
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
        DayWeatherForecast dayWeatherForecast = forecasts.stream()
            .filter(this::isWindSpeedSuitable)
            .min(Comparator.comparing(DayWeatherForecast::windSpeed))
            .orElse(null);

        if (dayWeatherForecast != null) {
            return dayWeatherForecast.dayNumber();
        }

        return NO_APPROPRIATE_DAY_TO_LAUNCH;
    }

    private boolean isWindSpeedSuitable(DayWeatherForecast dayWeatherForecast) {
        return windSpeedConfigurator.isWindSpeedSuitable(dayWeatherForecast);
    }
}