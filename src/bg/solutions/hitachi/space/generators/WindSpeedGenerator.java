package bg.solutions.hitachi.space.generators;

import bg.solutions.hitachi.space.entities.DayWeatherForecast;
import bg.solutions.hitachi.space.enums.Constraints;

import java.util.Comparator;
import java.util.List;

public class WindSpeedGenerator implements StatisticOperations {
    private final List<DayWeatherForecast> forecasts;

    public WindSpeedGenerator(List<DayWeatherForecast> dayWeatherForecasts) {
        this.forecasts = dayWeatherForecasts;
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

        return -1;
    }

    private boolean isWindSpeedSuitable(DayWeatherForecast dayWeatherForecast) {
        return dayWeatherForecast.windSpeed() <= Constraints.MAX_WIND_SPEED.getValue();
    }
}