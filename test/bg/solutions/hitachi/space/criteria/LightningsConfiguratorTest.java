package bg.solutions.hitachi.space.criteria;

import bg.solutions.hitachi.space.entities.DayWeatherForecast;
import bg.solutions.hitachi.space.enums.Cloud;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LightningsConfiguratorTest {
    @Test
    void testDefaultCreation() {
        LightningsConfigurator lightningsConfigurator = LightningsConfigurator.builder()
            .build();

        assertFalse(lightningsConfigurator.getLightnings(), "The default lightnings must be false!");
    }

    @Test
    void testCreationWithDifferentParameters() {
        LightningsConfigurator lightningsConfigurator = LightningsConfigurator.builder()
            .setLightnings(true)
            .build();


        assertTrue(lightningsConfigurator.getLightnings(), "The lightnings configurator must accept correct data!");
    }

    @Test
    void testAreThereLightningsWithDefault() {
        DayWeatherForecast dayWeatherForecast = new DayWeatherForecast(1, 5, 10, 10, 0., false, Cloud.CIRRUS);
        LightningsConfigurator lightningsConfigurator = LightningsConfigurator.builder().build();

        assertTrue(lightningsConfigurator.areThereLightnings(dayWeatherForecast),
            "The lightnings configurator must return correct value from the method!");
    }

    @Test
    void testAreThereNoLightningsWithDefault() {
        DayWeatherForecast dayWeatherForecast = new DayWeatherForecast(1, -1, 20, 10, 1., true, Cloud.CIRRUS);
        LightningsConfigurator lightningsConfigurator = LightningsConfigurator.builder().build();

        assertFalse(lightningsConfigurator.areThereLightnings(dayWeatherForecast),
            "The lightnings configurator must return correct value from the method!");
    }

    @Test
    void testAreThereLightningsWithDifferentParameters() {
        DayWeatherForecast dayWeatherForecast = new DayWeatherForecast(1, 5, 10, 10, 15.1, true, Cloud.CIRRUS);
        LightningsConfigurator lightningsConfigurator = LightningsConfigurator.builder()
            .setLightnings(true)
            .build();

        assertTrue(lightningsConfigurator.areThereLightnings(dayWeatherForecast),
            "The lightnings configurator must return correct value from the method!");
    }

    @Test
    void testAreThereNoLightningsWithDifferentParameters() {
        DayWeatherForecast dayWeatherForecast = new DayWeatherForecast(1, -5, 10, 10, 10, false, Cloud.CIRRUS);
        LightningsConfigurator lightningsConfigurator = LightningsConfigurator.builder()
            .setLightnings(true)
            .build();

        assertFalse(lightningsConfigurator.areThereLightnings(dayWeatherForecast),
            "The lightnings configurator must return correct value from the method!");
    }
}