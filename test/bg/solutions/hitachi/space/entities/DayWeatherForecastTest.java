package bg.solutions.hitachi.space.entities;

import bg.solutions.hitachi.space.enums.Cloud;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DayWeatherForecastTest {

    @Test
    void testCreateDayWeatherForecastWithDayNumberNegative() {
        assertThrows(IllegalArgumentException.class, () -> new DayWeatherForecast(-1, 0, 0, 0, 0, false, Cloud.CIRRUS)
            , "The Day number must be positive integer!");
    }

    @Test
    void testCreateDayWeatherForecastWithWindSpeedNegative() {
        assertThrows(IllegalArgumentException.class, () -> new DayWeatherForecast(1, 0, -1, 0, 0, false, Cloud.CIRRUS)
            , "The wind speed must be positive double value!");
    }

    @Test
    void testCreateDayWeatherForecastWithHumidityNegative() {
        assertThrows(IllegalArgumentException.class, () -> new DayWeatherForecast(1, 0, 1, -1, 0, false, Cloud.CIRRUS)
            , "The humidity % must be positive double value!");
    }

    @Test
    void testCreateDayWeatherForecastWithPrecipitationNegative() {
        assertThrows(IllegalArgumentException.class, () -> new DayWeatherForecast(1, 0, 1, 1, -1, false, Cloud.CIRRUS)
            , "The precipitation % must be positive double value!");
    }

    @Test
    void testCreateDayWeatherForecastWithHumidityAbove100() {
        assertThrows(IllegalArgumentException.class, () -> new DayWeatherForecast(1, 0, 1, 101, 0, false, Cloud.CIRRUS)
            , "The humidity % cannot be above 100%!");
    }

    @Test
    void testCreateDayWeatherForecastWithPrecipitationAbove100() {
        assertThrows(IllegalArgumentException.class, () -> new DayWeatherForecast(1, 0, 1, 1, 101, false, Cloud.CIRRUS)
            , "The precipitation % cannot be above 100%!");
    }

    @Test
    void testCreateDayWeatherForecastWithCloudsNull() {
        assertThrows(IllegalArgumentException.class, () -> new DayWeatherForecast(1, 0, 1, 1, 1, false, null)
            , "The clouds value cannot be null!");
    }
}