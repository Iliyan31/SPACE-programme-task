package bg.solutions.hitachi.space.generators;

import bg.solutions.hitachi.space.criteria.HumidityConfigurator;
import bg.solutions.hitachi.space.entities.DayWeatherForecast;

import java.util.Comparator;
import java.util.List;

public class HumidityGenerator implements StatisticOperations {
    private final List<DayWeatherForecast> forecasts;

    private final HumidityConfigurator humidityConfigurator;

    public HumidityGenerator(List<DayWeatherForecast> dayWeatherForecasts, HumidityConfigurator humidityConfigurator) {
        this.forecasts = dayWeatherForecasts;
        this.humidityConfigurator = humidityConfigurator;
    }

    @Override
    public double findAverage() {
        return forecasts.stream()
            .mapToDouble(DayWeatherForecast::humidity)
            .average()
            .orElse(0.0);
    }

    @Override
    public double findMax() {
        return forecasts.stream()
            .mapToDouble(DayWeatherForecast::humidity)
            .max()
            .orElse(0.0);
    }

    @Override
    public double findMin() {
        return forecasts.stream()
            .mapToDouble(DayWeatherForecast::humidity)
            .min()
            .orElse(0.0);
    }

    @Override
    public double findMedian() {
        List<Double> newList = List.copyOf(forecasts.stream()
            .mapToDouble(DayWeatherForecast::humidity)
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
            .filter(this::isHumiditySuitable)
            .min(Comparator.comparing(DayWeatherForecast::humidity))
            .orElse(null);

        if (dayWeatherForecast != null) {
            return dayWeatherForecast.dayNumber();
        }

        return -1;
    }

    private boolean isHumiditySuitable(DayWeatherForecast dayWeatherForecast) {
        return humidityConfigurator.isHumiditySuitable(dayWeatherForecast);
    }
}