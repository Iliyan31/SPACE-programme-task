package bg.solutions.hitachi.space.criteria;

import bg.solutions.hitachi.space.entities.DayWeatherForecast;
import bg.solutions.hitachi.space.enums.Cloud;
import bg.solutions.hitachi.space.enums.Constraints;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HumidityConfiguratorTest {
    @Test
    void testDefaultCreation() {
        HumidityConfigurator humidityConfigurator = HumidityConfigurator.builder()
            .build();

        Double maxHumidity = humidityConfigurator.getMaxHumidity();

        assertEquals(maxHumidity, Constraints.MAX_HUMIDITY_PERCENT.getValue(),
            "The default max humidity % must be 60!");
    }

    @Test
    void testCreationWithDifferentParameters() {
        HumidityConfigurator humidityConfigurator = HumidityConfigurator.builder()
            .setHumidity(20.)
            .build();

        Double maxHumidity = humidityConfigurator.getMaxHumidity();
        assertEquals(maxHumidity, 20., "The humidity configurator must accept correct data!");
    }

    @Test
    void testIsHumiditySuitableWithDefault() {
        DayWeatherForecast dayWeatherForecast = new DayWeatherForecast(1, 5, 10, 10, 0., false, Cloud.CIRRUS);
        HumidityConfigurator humidityConfigurator = HumidityConfigurator.builder().build();

        assertTrue(humidityConfigurator.isHumiditySuitable(dayWeatherForecast),
            "The humidity configurator must return correct value from the method!");
    }

    @Test
    void testIsHumidityNotSuitableWithDefault() {
        DayWeatherForecast dayWeatherForecast = new DayWeatherForecast(1, -1, 20, 90, 1., false, Cloud.CIRRUS);
        HumidityConfigurator humidityConfigurator = HumidityConfigurator.builder().build();

        assertFalse(humidityConfigurator.isHumiditySuitable(dayWeatherForecast),
            "The humidity configurator must return correct value from the method!");
    }

    @Test
    void testIsHumiditySuitableWithDifferentParameters() {
        DayWeatherForecast dayWeatherForecast = new DayWeatherForecast(1, 5, 10, 10, 15.1, false, Cloud.CIRRUS);
        HumidityConfigurator humidityConfigurator = HumidityConfigurator.builder()
            .setHumidity(15.2)
            .build();

        assertTrue(humidityConfigurator.isHumiditySuitable(dayWeatherForecast),
            "The humidity configurator must return correct value from the method!");
    }

    @Test
    void testIsHumidityNotSuitableWithDifferentParameters() {
        DayWeatherForecast dayWeatherForecast = new DayWeatherForecast(1, -5, 10, 10, 10, false, Cloud.CIRRUS);
        HumidityConfigurator humidityConfigurator = HumidityConfigurator.builder()
            .setHumidity(8.7)
            .build();

        assertFalse(humidityConfigurator.isHumiditySuitable(dayWeatherForecast),
            "The humidity configurator must return correct value from the method!");
    }
}
