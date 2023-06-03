package bg.solutions.hitachi.space.generators;

import bg.solutions.hitachi.space.criteria.HumidityConfigurator;
import bg.solutions.hitachi.space.entities.DayWeatherForecast;
import bg.solutions.hitachi.space.enums.Cloud;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HumidityGeneratorTest {
    private static HumidityGenerator humidityGenerator;
    private static HumidityConfigurator humidityConfigurator;
    private static List<DayWeatherForecast> forecasts;

    @BeforeAll
    static void setUp() {
        DayWeatherForecast dayWeatherForecast1 = new DayWeatherForecast(1, 28., 15., 20., 10, false, Cloud.CUMULUS);
        DayWeatherForecast dayWeatherForecast2 = new DayWeatherForecast(2, 28., 13, 30, 10, false, Cloud.CUMULUS);
        DayWeatherForecast dayWeatherForecast3 = new DayWeatherForecast(3, 29., 12, 30, 15, false, Cloud.STRATUS);
        DayWeatherForecast dayWeatherForecast4 = new DayWeatherForecast(4, 30., 14, 35, 40, false, Cloud.STRATUS);
        DayWeatherForecast dayWeatherForecast5 = new DayWeatherForecast(5, 31., 11, 60, 20, false, Cloud.STRATUS);

        humidityConfigurator = HumidityConfigurator.builder()
            .build();

        forecasts = new ArrayList<>();

        forecasts.add(dayWeatherForecast1);
        forecasts.add(dayWeatherForecast2);
        forecasts.add(dayWeatherForecast3);
        forecasts.add(dayWeatherForecast4);
        forecasts.add(dayWeatherForecast5);

        humidityGenerator = new HumidityGenerator(forecasts, humidityConfigurator);
    }

    @Test
    void testFindAverage() {
        assertEquals(humidityGenerator.findAverage(), 35, 0.001,
            "The HumidityGenerator must calculate correct average sum!");
    }

    @Test
    void testFindMax() {
        assertEquals(humidityGenerator.findMax(), 60., 0,
            "The HumidityGenerator must correctly find max value!");
    }

    @Test
    void testFindMin() {
        assertEquals(humidityGenerator.findMin(), 20., 0,
            "The HumidityGenerator must correctly find min value!");
    }

    @Test
    void testFindMedianOdd() {
        assertEquals(humidityGenerator.findMedian(), 30., 0,
            "The HumidityGenerator must correctly find median value!");
    }

    @Test
    void testFindMedianEven() {
        List<DayWeatherForecast> newForecasts = new ArrayList<>(forecasts);
        newForecasts.add(new DayWeatherForecast(6, 20, 12, 30, 0, false, Cloud.CUMULUS));
        HumidityGenerator precipitationGenerator1 =
            new HumidityGenerator(newForecasts, humidityConfigurator);

        assertEquals(precipitationGenerator1.findMedian(), 30, 0.01,
            "The HumidityGenerator must correctly find median value!");
    }

    @Test
    void testGetPerfectDay() {
        assertEquals(humidityGenerator.getPerfectDay(), 1, 0,
            "The HumidityGenerator must correctly find prefect day!");
    }

    @Test
    void testGetPerfectDayNoSuch() {
        HumidityGenerator precipitationGenerator1 =
            new HumidityGenerator(forecasts, HumidityConfigurator.builder()
                .setHumidity(10.)
                .build());

        assertEquals(precipitationGenerator1.getPerfectDay(), -1, 0,
            "The HumidityGenerator must correctly find prefect day!");
    }
}