package bg.solutions.hitachi.space.criteria;

import bg.solutions.hitachi.space.entities.DayWeatherForecast;
import bg.solutions.hitachi.space.enums.Cloud;
import bg.solutions.hitachi.space.enums.Constraints;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PrecipitationConfiguratorTest {
    @Test
    void testDefaultCreation() {
        PrecipitationConfigurator precipitationConfigurator = PrecipitationConfigurator.builder()
            .build();

        Double maxPrecipitation = precipitationConfigurator.getMaxPrecipitation();

        assertEquals(maxPrecipitation, Constraints.MAX_PRECIPITATION_PERCENTS.getValue(),
            "The default max precipitation % must be 0!");
    }

    @Test
    void testCreationWithDifferentParameters() {
        PrecipitationConfigurator precipitationConfigurator = PrecipitationConfigurator.builder()
            .setPrecipitation(20.)
            .build();

        Double maxPrecipitation = precipitationConfigurator.getMaxPrecipitation();
        assertEquals(maxPrecipitation, 20., "The precipitation configurator must accept correct data!");
    }

    @Test
    void testIsThereNoPrecipitationWithDefault() {
        DayWeatherForecast dayWeatherForecast = new DayWeatherForecast(1, 5, 10, 10, 0., false, Cloud.CIRRUS);
        PrecipitationConfigurator precipitationConfigurator = PrecipitationConfigurator.builder().build();

        assertTrue(precipitationConfigurator.isThereNoPrecipitation(dayWeatherForecast),
            "The precipitation configurator must return correct value from the method!");
    }

    @Test
    void testIsTherePrecipitationWithDefault() {
        DayWeatherForecast dayWeatherForecast = new DayWeatherForecast(1, -1, 20, 10, 1., false, Cloud.CIRRUS);
        PrecipitationConfigurator precipitationConfigurator = PrecipitationConfigurator.builder().build();

        assertFalse(precipitationConfigurator.isThereNoPrecipitation(dayWeatherForecast),
            "The precipitation configurator must return correct value from the method!");
    }

    @Test
    void testIsThereNoPrecipitationWithDifferentParameters() {
        DayWeatherForecast dayWeatherForecast = new DayWeatherForecast(1, 5, 10, 10, 15.1, false, Cloud.CIRRUS);
        PrecipitationConfigurator precipitationConfigurator = PrecipitationConfigurator.builder()
            .setPrecipitation(15.2)
            .build();

        assertTrue(precipitationConfigurator.isThereNoPrecipitation(dayWeatherForecast),
            "The precipitation configurator must return correct value from the method!");
    }

    @Test
    void testIsTherePrecipitationWithDifferentParameters() {
        DayWeatherForecast dayWeatherForecast = new DayWeatherForecast(1, -5, 10, 10, 10, false, Cloud.CIRRUS);
        PrecipitationConfigurator precipitationConfigurator = PrecipitationConfigurator.builder()
            .setPrecipitation(8.7)
            .build();

        assertFalse(precipitationConfigurator.isThereNoPrecipitation(dayWeatherForecast),
            "The precipitation configurator must return correct value from the method!");
    }
}