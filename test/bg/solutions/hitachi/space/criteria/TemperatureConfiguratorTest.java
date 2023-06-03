package bg.solutions.hitachi.space.criteria;

import bg.solutions.hitachi.space.entities.DayWeatherForecast;
import bg.solutions.hitachi.space.enums.Cloud;
import bg.solutions.hitachi.space.enums.Constraints;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TemperatureConfiguratorTest {
    @Test
    void testDefaultCreation() {
        TemperatureConfigurator temperatureConfigurator = TemperatureConfigurator.builder()
            .build();

        Double minTemp = temperatureConfigurator.getMinTemperature();
        Double maxTemp = temperatureConfigurator.getMaxTemperature();

        assertTrue(minTemp.equals(Constraints.MIN_TEMPERATURE_VALUE.getValue()) &&
                maxTemp.equals(Constraints.MAX_TEMPERATURE_VALUE.getValue()),
            "The default min and max temperature must be 2 and 31!");
    }

    @Test
    void testCreationWithDifferentParameters() {
        TemperatureConfigurator temperatureConfigurator = TemperatureConfigurator.builder()
            .setMinTemperature(-1.)
            .setMaxTemperature(35.)
            .build();

        Double minTemp = temperatureConfigurator.getMinTemperature();
        Double maxTemp = temperatureConfigurator.getMaxTemperature();

        assertTrue(minTemp.equals(-1.) && maxTemp.equals(35.),
            "The temperature configurator must accept correct data!");
    }

    @Test
    void testIsTemperatureSuitableWithDefault() {
        DayWeatherForecast dayWeatherForecast = new DayWeatherForecast(1, 5, 10, 10, 10, false, Cloud.CIRRUS);
        TemperatureConfigurator temperatureConfigurator = TemperatureConfigurator.builder().build();

        assertTrue(temperatureConfigurator.isTemperatureSuitable(dayWeatherForecast),
            "The temperature configurator must return correct value from the method!");
    }

    @Test
    void testIsTemperatureNotSuitableWithDefault() {
        DayWeatherForecast dayWeatherForecast = new DayWeatherForecast(1, -1, 10, 10, 10, false, Cloud.CIRRUS);
        TemperatureConfigurator temperatureConfigurator = TemperatureConfigurator.builder().build();

        assertFalse(temperatureConfigurator.isTemperatureSuitable(dayWeatherForecast),
            "The temperature configurator must return correct value from the method!");
    }

    @Test
    void testIsTemperatureSuitableWithDifferentParameters() {
        DayWeatherForecast dayWeatherForecast = new DayWeatherForecast(1, 5, 10, 10, 10, false, Cloud.CIRRUS);
        TemperatureConfigurator temperatureConfigurator = TemperatureConfigurator.builder()
            .setMinTemperature(-2.)
            .setMaxTemperature(20.)
            .build();

        assertTrue(temperatureConfigurator.isTemperatureSuitable(dayWeatherForecast),
            "The temperature configurator must return correct value from the method!");
    }

    @Test
    void testIsTemperatureNotSuitableWithDifferentParameters() {
        DayWeatherForecast dayWeatherForecast = new DayWeatherForecast(1, -5, 10, 10, 10, false, Cloud.CIRRUS);
        TemperatureConfigurator temperatureConfigurator = TemperatureConfigurator.builder()
            .setMinTemperature(-2.)
            .setMaxTemperature(20.)
            .build();

        assertFalse(temperatureConfigurator.isTemperatureSuitable(dayWeatherForecast),
            "The temperature configurator must return correct value from the method!");
    }
}