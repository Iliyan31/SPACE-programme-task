package bg.solutions.hitachi.space.generators;

import bg.solutions.hitachi.space.criteria.WindSpeedConfigurator;
import bg.solutions.hitachi.space.entities.DayWeatherForecast;
import bg.solutions.hitachi.space.enums.Cloud;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WindSpeedGeneratorTest {
    private static WindSpeedGenerator windSpeedGenerator;
    private static WindSpeedConfigurator windSpeedConfigurator;
    private static List<DayWeatherForecast> forecasts;

    @BeforeAll
    static void setUp() {
        DayWeatherForecast dayWeatherForecast1 = new DayWeatherForecast(1, 28., 15., 20., 0, false, Cloud.CUMULUS);
        DayWeatherForecast dayWeatherForecast2 = new DayWeatherForecast(2, 28., 13, 30, 0, false, Cloud.CUMULUS);
        DayWeatherForecast dayWeatherForecast3 = new DayWeatherForecast(3, 29., 12, 30, 0, false, Cloud.STRATUS);
        DayWeatherForecast dayWeatherForecast4 = new DayWeatherForecast(4, 30., 14, 35, 0, false, Cloud.STRATUS);
        DayWeatherForecast dayWeatherForecast5 = new DayWeatherForecast(5, 31., 11, 60, 20, false, Cloud.STRATUS);

        windSpeedConfigurator = WindSpeedConfigurator.builder()
            .setWindSpeed(20.)
            .build();

        forecasts = new ArrayList<>();

        forecasts.add(dayWeatherForecast1);
        forecasts.add(dayWeatherForecast2);
        forecasts.add(dayWeatherForecast3);
        forecasts.add(dayWeatherForecast4);
        forecasts.add(dayWeatherForecast5);

        windSpeedGenerator = new WindSpeedGenerator(forecasts, windSpeedConfigurator);
    }

    @Test
    void testFindAverage() {
        assertEquals(windSpeedGenerator.findAverage(), 13., 0.001,
            "The WindSpeedGenerator must calculate correct average sum!");
    }

    @Test
    void testFindMax() {
        assertEquals(windSpeedGenerator.findMax(), 15, 0,
            "The WindSpeedGenerator must correctly find max value!");
    }

    @Test
    void testFindMin() {
        assertEquals(windSpeedGenerator.findMin(), 11, 0,
            "The WindSpeedGenerator must correctly find min value!");
    }

    @Test
    void testFindMedianOdd() {
        assertEquals(windSpeedGenerator.findMedian(), 13, 0,
            "The WindSpeedGenerator must correctly find median value!");
    }

    @Test
    void testFindMedianEven() {
        List<DayWeatherForecast> newForecasts = new ArrayList<>(forecasts);
        newForecasts.add(new DayWeatherForecast(6, 20, 12, 30, 0, false, Cloud.CUMULUS));
        WindSpeedGenerator windSpeedGenerator1 = new WindSpeedGenerator(newForecasts, windSpeedConfigurator);

        assertEquals(windSpeedGenerator1.findMedian(), 12.5, 0.01,
            "The WindSpeedGenerator must correctly find median value!");
    }

    @Test
    void testGetPerfectDay() {
        assertEquals(windSpeedGenerator.getPerfectDay(), 5, 0,
            "The WindSpeedGenerator must correctly find prefect day!");
    }

    @Test
    void testGetPerfectDayNoSuch() {
        WindSpeedGenerator newWindSpeedGenerator = new WindSpeedGenerator(forecasts, WindSpeedConfigurator.builder()
            .build());

        assertEquals(newWindSpeedGenerator.getPerfectDay(), -1, 0,
            "The WindSpeedGenerator must correctly find prefect day!");
    }
}