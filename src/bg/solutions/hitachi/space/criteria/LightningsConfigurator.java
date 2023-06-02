package bg.solutions.hitachi.space.criteria;

import bg.solutions.hitachi.space.entities.DayWeatherForecast;
import bg.solutions.hitachi.space.enums.Constraints;

public class LightningsConfigurator {
    private final boolean lightnings;

    public boolean getLightnings() {
        return lightnings;
    }

    public boolean areThereNoLightnings(DayWeatherForecast dayWeatherForecast) {
        return dayWeatherForecast.lightning() == lightnings;
    }

    public static LightningsConfiguratorBuilder builder() {
        return new LightningsConfiguratorBuilder();
    }

    private LightningsConfigurator(LightningsConfiguratorBuilder lightningsConfiguratorBuilder) {
        this.lightnings = lightningsConfiguratorBuilder.lightnings;
    }

    public static class LightningsConfiguratorBuilder {
        private boolean lightnings;

        private LightningsConfiguratorBuilder() {
            this(false);
        }

        private LightningsConfiguratorBuilder(boolean lightnings) {
            this.lightnings = lightnings;
        }

        public LightningsConfiguratorBuilder setLightnings(boolean lightnings) {
            this.lightnings = lightnings;
            return this;
        }

        public LightningsConfigurator build() {
            return new LightningsConfigurator(this);
        }
    }
}