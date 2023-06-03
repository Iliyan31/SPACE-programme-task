package bg.solutions.hitachi.space.criteria;

import bg.solutions.hitachi.space.entities.DayWeatherForecast;
import bg.solutions.hitachi.space.enums.Cloud;
import bg.solutions.hitachi.space.enums.Constraints;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WindSpeedConfiguratorTest {
    @Test
    void testDefaultCreation() {
        WindSpeedConfigurator windSpeedConfigurator = WindSpeedConfigurator.builder()
            .build();

        Double maxWindSpeed = windSpeedConfigurator.getMaxWindSpeed();

        assertEquals(maxWindSpeed, Constraints.MAX_WIND_SPEED.getValue(), "The default max wind speed must be 10!");
    }

    @Test
    void testCreationWithDifferentParameters() {
        WindSpeedConfigurator windSpeedConfigurator = WindSpeedConfigurator.builder()
            .setWindSpeed(20.)
            .build();

        Double maxWindSpeed = windSpeedConfigurator.getMaxWindSpeed();
        assertEquals(maxWindSpeed, 20., "The wind speed configurator must accept correct data!");
    }

    @Test
    void testIsWindSpeedSuitableWithDefault() {
        DayWeatherForecast dayWeatherForecast = new DayWeatherForecast(1, 5, 10, 10, 10, false, Cloud.CIRRUS);
        WindSpeedConfigurator windSpeedConfigurator = WindSpeedConfigurator.builder().build();

        assertTrue(windSpeedConfigurator.isWindSpeedSuitable(dayWeatherForecast),
            "The wind speed configurator must return correct value from the method!");
    }

    @Test
    void testIsTemperatureNotSuitableWithDefault() {
        DayWeatherForecast dayWeatherForecast = new DayWeatherForecast(1, -1, 20, 10, 10, false, Cloud.CIRRUS);
        WindSpeedConfigurator windSpeedConfigurator = WindSpeedConfigurator.builder().build();

        assertFalse(windSpeedConfigurator.isWindSpeedSuitable(dayWeatherForecast),
            "The wind speed configurator must return correct value from the method!");
    }

    @Test
    void testIsTemperatureSuitableWithDifferentParameters() {
        DayWeatherForecast dayWeatherForecast = new DayWeatherForecast(1, 5, 10, 10, 10, false, Cloud.CIRRUS);
        WindSpeedConfigurator windSpeedConfigurator = WindSpeedConfigurator.builder()
            .setWindSpeed(15.1)
            .build();

        assertTrue(windSpeedConfigurator.isWindSpeedSuitable(dayWeatherForecast),
            "The wind speed configurator must return correct value from the method!");
    }

    @Test
    void testIsTemperatureNotSuitableWithDifferentParameters() {
        DayWeatherForecast dayWeatherForecast = new DayWeatherForecast(1, -5, 10, 10, 10, false, Cloud.CIRRUS);
        WindSpeedConfigurator windSpeedConfigurator = WindSpeedConfigurator.builder()
            .setWindSpeed(8.7)
            .build();

        assertFalse(windSpeedConfigurator.isWindSpeedSuitable(dayWeatherForecast),
            "The wind speed configurator must return correct value from the method!");
    }
}