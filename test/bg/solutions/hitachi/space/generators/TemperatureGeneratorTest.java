package bg.solutions.hitachi.space.generators;

import bg.solutions.hitachi.space.criteria.TemperatureConfigurator;
import bg.solutions.hitachi.space.entities.DayWeatherForecast;
import bg.solutions.hitachi.space.enums.Cloud;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TemperatureGeneratorTest {
    private static TemperatureGenerator temperatureGenerator;
    private static TemperatureConfigurator temperatureConfigurator;
    private static List<DayWeatherForecast> forecasts;

    @BeforeAll
    static void setUp() {
        DayWeatherForecast dayWeatherForecast1 = new DayWeatherForecast(1, 28., 15., 20., 0, false, Cloud.CUMULUS);
        DayWeatherForecast dayWeatherForecast2 = new DayWeatherForecast(2, 28., 13, 30, 0, false, Cloud.CUMULUS);
        DayWeatherForecast dayWeatherForecast3 = new DayWeatherForecast(3, 29., 12, 30, 0, false, Cloud.STRATUS);
        DayWeatherForecast dayWeatherForecast4 = new DayWeatherForecast(4, 30., 14, 35, 0, false, Cloud.STRATUS);
        DayWeatherForecast dayWeatherForecast5 = new DayWeatherForecast(5, 31., 11, 60, 20, false, Cloud.STRATUS);

        temperatureConfigurator = TemperatureConfigurator.builder()
            .build();

        forecasts = new ArrayList<>();

        forecasts.add(dayWeatherForecast1);
        forecasts.add(dayWeatherForecast2);
        forecasts.add(dayWeatherForecast3);
        forecasts.add(dayWeatherForecast4);
        forecasts.add(dayWeatherForecast5);

        temperatureGenerator = new TemperatureGenerator(forecasts, temperatureConfigurator);
    }

    @Test
    void testFindAverage() {
        assertEquals(temperatureGenerator.findAverage(), 29.2, 0.001,
            "The TemperatureGenerator must calculate correct average sum!");
    }

    @Test
    void testFindMax() {
        assertEquals(temperatureGenerator.findMax(), 31., 0,
            "The TemperatureGenerator must correctly find max value!");
    }

    @Test
    void testFindMin() {
        assertEquals(temperatureGenerator.findMin(), 28., 0,
            "The TemperatureGenerator must correctly find min value!");
    }

    @Test
    void testFindMedianOdd() {
        assertEquals(temperatureGenerator.findMedian(), 29., 0,
            "The TemperatureGenerator must correctly find median value!");
    }

    @Test
    void testFindMedianEven() {
        List<DayWeatherForecast> newForecasts = new ArrayList<>(forecasts);
        newForecasts.add(new DayWeatherForecast(6, 20, 12, 30, 0, false, Cloud.CUMULUS));
        TemperatureGenerator temperatureGenerator1 = new TemperatureGenerator(newForecasts, temperatureConfigurator);

        assertEquals(temperatureGenerator1.findMedian(), 28.5, 0.01,
            "The TemperatureGenerator must correctly find median value!");
    }

    @Test
    void testGetPerfectDay() {
        assertEquals(temperatureGenerator.getPerfectDay(), 1, 0,
            "The TemperatureGenerator must correctly find prefect day!");
    }

    @Test
    void testGetPerfectDayNoSuch() {
        TemperatureGenerator temperatureGenerator1 =
            new TemperatureGenerator(forecasts, TemperatureConfigurator.builder()
                .setMaxTemperature(20)
                .build());

        assertEquals(temperatureGenerator1.getPerfectDay(), -1, 0,
            "The TemperatureGenerator must correctly find prefect day!");
    }
}