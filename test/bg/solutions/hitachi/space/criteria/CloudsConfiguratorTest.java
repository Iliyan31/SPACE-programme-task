package bg.solutions.hitachi.space.criteria;

import bg.solutions.hitachi.space.entities.DayWeatherForecast;
import bg.solutions.hitachi.space.enums.Cloud;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CloudsConfiguratorTest {
    @Test
    void testDefaultCreation() {
        CloudsConfigurator cloudsConfigurator = CloudsConfigurator.builder()
            .build();

        assertTrue(cloudsConfigurator.getForbiddenClouds().isEmpty(), "The default forbidden clouds must be empty!");
    }

    @Test
    void testCreationWithDifferentOneParameter() {
        CloudsConfigurator cloudsConfigurator = CloudsConfigurator.builder()
            .setCloud(Cloud.STRATUS)
            .build();

        assertTrue(cloudsConfigurator.getForbiddenClouds().contains(Cloud.STRATUS),
            "The cloud configurator must accept correct data!");
    }

    @Test
    void testCreationWithDifferentMoreParameters() {
        CloudsConfigurator cloudsConfigurator = CloudsConfigurator.builder()
            .setCloud(Cloud.STRATUS)
            .setCloud(Cloud.CUMULUS)
            .build();

        List<Cloud> clouds = new ArrayList<>();
        clouds.add(Cloud.CUMULUS);
        clouds.add(Cloud.STRATUS);

        assertTrue(cloudsConfigurator.getForbiddenClouds().containsAll(clouds),
            "The cloud configurator must accept correct data!");
    }

    @Test
    void testCreationWithDifferentMoreParametersList() {
        List<Cloud> clouds = new ArrayList<>();
        clouds.add(Cloud.CUMULUS);
        clouds.add(Cloud.STRATUS);

        CloudsConfigurator cloudsConfigurator = CloudsConfigurator.builder()
            .setForbiddenClouds(clouds)
            .build();

        Set<Cloud> newClouds = new HashSet<>();
        newClouds.add(Cloud.CUMULUS);
        newClouds.add(Cloud.STRATUS);

        assertTrue(cloudsConfigurator.getForbiddenClouds().containsAll(newClouds),
            "The cloud configurator must accept correct data!");
    }

    @Test
    void testAreCloudsSuitableWithDefault() {
        DayWeatherForecast dayWeatherForecast = new DayWeatherForecast(1, 5, 10, 10, 0., false, Cloud.CIRRUS);
        CloudsConfigurator cloudsConfigurator = CloudsConfigurator.builder().build();

        assertTrue(cloudsConfigurator.areCloudsSuitable(dayWeatherForecast),
            "The cloud configurator must return correct value from the method!");
    }

    @Test
    void testAreCloudsSuitableWithDifferentParameters() {
        DayWeatherForecast dayWeatherForecast = new DayWeatherForecast(1, 5, 10, 10, 15.1, false, Cloud.CIRRUS);
        CloudsConfigurator cloudsConfigurator = CloudsConfigurator.builder()
            .setCloud(Cloud.STRATUS)
            .build();

        assertTrue(cloudsConfigurator.areCloudsSuitable(dayWeatherForecast),
            "The cloud configurator must return correct value from the method!");
    }

    @Test
    void testAreCloudsNotSuitableWithDifferentParameters() {
        DayWeatherForecast dayWeatherForecast = new DayWeatherForecast(1, -1, 20, 90, 1., false, Cloud.CIRRUS);
        CloudsConfigurator cloudsConfigurator = CloudsConfigurator.builder()
            .setCloud(Cloud.CIRRUS)
            .build();

        assertFalse(cloudsConfigurator.areCloudsSuitable(dayWeatherForecast),
            "The cloud configurator must return correct value from the method!");
    }
}