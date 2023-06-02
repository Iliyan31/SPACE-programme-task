package bg.solutions.hitachi.space.criteria;

import bg.solutions.hitachi.space.entities.DayWeatherForecast;
import bg.solutions.hitachi.space.enums.Cloud;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CloudsConfigurator {
    private final Set<Cloud> forbiddenClouds;

    public Set<Cloud> getForbiddenClouds() {
        return forbiddenClouds;
    }

    public boolean areCloudsSuitable(DayWeatherForecast dayWeatherForecast) {
        return !forbiddenClouds.contains(dayWeatherForecast.clouds());
    }


    public static CloudsConfiguratorBuilder builder() {
        return new CloudsConfiguratorBuilder();
    }

    private CloudsConfigurator(CloudsConfiguratorBuilder temperatureConfiguratorBuilder) {
        this.forbiddenClouds = temperatureConfiguratorBuilder.forbiddenClouds;
    }

    public static class CloudsConfiguratorBuilder {

        private final Set<Cloud> forbiddenClouds;

        private CloudsConfiguratorBuilder() {
            this(new HashSet<>());
        }

        private CloudsConfiguratorBuilder(Set<Cloud> forbiddenClouds) {
            this.forbiddenClouds = forbiddenClouds;
        }

        public CloudsConfiguratorBuilder setCloud(Cloud cloud) {
            this.forbiddenClouds.add(cloud);
            return this;
        }

        public CloudsConfiguratorBuilder setForbiddenClouds(Collection<Cloud> clouds) {
            this.forbiddenClouds.addAll(clouds);
            return this;
        }

        public CloudsConfigurator build() {
            return new CloudsConfigurator(this);
        }
    }
}