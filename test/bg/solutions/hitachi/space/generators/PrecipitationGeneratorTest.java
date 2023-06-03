package bg.solutions.hitachi.space.generators;

import bg.solutions.hitachi.space.criteria.PrecipitationConfigurator;
import bg.solutions.hitachi.space.entities.DayWeatherForecast;
import bg.solutions.hitachi.space.enums.Cloud;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrecipitationGeneratorTest {
    private static PrecipitationGenerator precipitationGenerator;
    private static PrecipitationConfigurator precipitationConfigurator;
    private static List<DayWeatherForecast> forecasts;

    @BeforeAll
    static void setUp() {
        DayWeatherForecast dayWeatherForecast1 = new DayWeatherForecast(1, 28., 15., 20., 10, false, Cloud.CUMULUS);
        DayWeatherForecast dayWeatherForecast2 = new DayWeatherForecast(2, 28., 13, 30, 10, false, Cloud.CUMULUS);
        DayWeatherForecast dayWeatherForecast3 = new DayWeatherForecast(3, 29., 12, 30, 15, false, Cloud.STRATUS);
        DayWeatherForecast dayWeatherForecast4 = new DayWeatherForecast(4, 30., 14, 35, 40, false, Cloud.STRATUS);
        DayWeatherForecast dayWeatherForecast5 = new DayWeatherForecast(5, 31., 11, 60, 20, false, Cloud.STRATUS);

        precipitationConfigurator = PrecipitationConfigurator.builder()
            .setPrecipitation(20.)
            .build();

        forecasts = new ArrayList<>();

        forecasts.add(dayWeatherForecast1);
        forecasts.add(dayWeatherForecast2);
        forecasts.add(dayWeatherForecast3);
        forecasts.add(dayWeatherForecast4);
        forecasts.add(dayWeatherForecast5);

        precipitationGenerator = new PrecipitationGenerator(forecasts, precipitationConfigurator);
    }

    @Test
    void testFindAverage() {
        assertEquals(precipitationGenerator.findAverage(), 19, 0.001,
            "The PrecipitationGenerator must calculate correct average sum!");
    }

    @Test
    void testFindMax() {
        assertEquals(precipitationGenerator.findMax(), 40., 0,
            "The PrecipitationGenerator must correctly find max value!");
    }

    @Test
    void testFindMin() {
        assertEquals(precipitationGenerator.findMin(), 10., 0,
            "The PrecipitationGenerator must correctly find min value!");
    }

    @Test
    void testFindMedianOdd() {
        assertEquals(precipitationGenerator.findMedian(), 15., 0,
            "The PrecipitationGenerator must correctly find median value!");
    }

    @Test
    void testFindMedianEven() {
        List<DayWeatherForecast> newForecasts = new ArrayList<>(forecasts);
        newForecasts.add(new DayWeatherForecast(6, 20, 12, 30, 0, false, Cloud.CUMULUS));
        PrecipitationGenerator precipitationGenerator1 =
            new PrecipitationGenerator(newForecasts, precipitationConfigurator);

        assertEquals(precipitationGenerator1.findMedian(), 12.5, 0.01,
            "The PrecipitationGenerator must correctly find median value!");
    }

    @Test
    void testGetPerfectDay() {
        assertEquals(precipitationGenerator.getPerfectDay(), 1, 0,
            "The PrecipitationGenerator must correctly find prefect day!");
    }

    @Test
    void testGetPerfectDayNoSuch() {
        PrecipitationGenerator precipitationGenerator1 =
            new PrecipitationGenerator(forecasts, PrecipitationConfigurator.builder()
                .build());

        assertEquals(precipitationGenerator1.getPerfectDay(), -1, 0,
            "The PrecipitationGenerator must correctly find prefect day!");
    }
}