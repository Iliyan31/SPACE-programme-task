package bg.solutions.hitachi.space.generators;

import bg.solutions.hitachi.space.entities.DayWeatherForecast;
import bg.solutions.hitachi.space.enums.Constraints;

import java.util.List;

public class PrecipitationGenerator implements StatisticOperations {
    private final List<DayWeatherForecast> forecasts;

    public PrecipitationGenerator(List<DayWeatherForecast> precipitations) {
        this.forecasts = precipitations;
    }

    @Override
    public double findAverage() {
        return forecasts.stream()
            .mapToDouble(DayWeatherForecast::precipitation)
            .average()
            .orElse(0.0);
    }

    @Override
    public double findMax() {
        return forecasts.stream()
            .mapToDouble(DayWeatherForecast::precipitation)
            .max()
            .orElse(0.0);
    }

    @Override
    public double findMin() {
        return forecasts.stream()
            .mapToDouble(DayWeatherForecast::precipitation)
            .min()
            .orElse(0.0);
    }

    @Override
    public double findMedian() {
        List<Double> newList = List.copyOf(forecasts.stream()
            .mapToDouble(DayWeatherForecast::precipitation)
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
            .filter(this::isThereNoPrecipitation)
            .mapToInt(DayWeatherForecast::dayNumber)
            .findFirst()
            .orElse(-1);
    }

    private boolean isThereNoPrecipitation(DayWeatherForecast dayWeatherForecast) {
        return dayWeatherForecast.precipitation() <= Constraints.MAX_PRECIPITATION_PERCENTS.getValue();
    }
}