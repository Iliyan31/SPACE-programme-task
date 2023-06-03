package bg.solutions.hitachi.space.generators;

import bg.solutions.hitachi.space.criteria.CloudsConfigurator;
import bg.solutions.hitachi.space.entities.DayWeatherForecast;
import bg.solutions.hitachi.space.enums.Cloud;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CloudsGeneratorTest {
    private static CloudsGenerator cloudsGenerator;
    private static List<DayWeatherForecast> forecasts;

    @BeforeAll
    static void setUp() {
        DayWeatherForecast dayWeatherForecast1 = new DayWeatherForecast(1, 28., 15., 20., 10, false, Cloud.CUMULUS);
        DayWeatherForecast dayWeatherForecast2 = new DayWeatherForecast(2, 28., 13, 30, 10, false, Cloud.CUMULUS);
        DayWeatherForecast dayWeatherForecast3 = new DayWeatherForecast(3, 29., 12, 30, 15, false, Cloud.STRATUS);
        DayWeatherForecast dayWeatherForecast4 = new DayWeatherForecast(4, 30., 14, 35, 40, false, Cloud.STRATUS);
        DayWeatherForecast dayWeatherForecast5 = new DayWeatherForecast(5, 31., 11, 60, 20, false, Cloud.STRATUS);

        CloudsConfigurator cloudsConfigurator = CloudsConfigurator.builder()
            .build();

        forecasts = new ArrayList<>();

        forecasts.add(dayWeatherForecast1);
        forecasts.add(dayWeatherForecast2);
        forecasts.add(dayWeatherForecast3);
        forecasts.add(dayWeatherForecast4);
        forecasts.add(dayWeatherForecast5);

        cloudsGenerator = new CloudsGenerator(forecasts, cloudsConfigurator);
    }

    @Test
    void testGetPerfectDay() {
        assertEquals(cloudsGenerator.getPerfectDay(), 1, 0,
            "The TemperatureGenerator must correctly find prefect day!");
    }

    @Test
    void testGetPerfectDayNoSuch() {
        CloudsGenerator lightningsGenerator1 =
            new CloudsGenerator(forecasts, CloudsConfigurator.builder()
                .setCloud(Cloud.STRATUS)
                .setCloud(Cloud.CUMULUS)
                .build());

        assertEquals(lightningsGenerator1.getPerfectDay(), -1, 0,
            "The TemperatureGenerator must correctly find prefect day!");
    }
}