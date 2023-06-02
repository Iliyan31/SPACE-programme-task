package bg.solutions.hitachi.space.criteria;

import bg.solutions.hitachi.space.entities.DayWeatherForecast;
import bg.solutions.hitachi.space.enums.Constraints;

public class PrecipitationConfigurator {
    private final double maxPrecipitation;

    public double getMaxPrecipitation() {
        return maxPrecipitation;
    }

    public boolean isThereNoPrecipitation(DayWeatherForecast dayWeatherForecast) {
        return dayWeatherForecast.precipitation() <= maxPrecipitation;
    }

    public static PrecipitationConfiguratorBuilder builder() {
        return new PrecipitationConfiguratorBuilder();
    }

    private PrecipitationConfigurator(PrecipitationConfiguratorBuilder precipitationConfiguratorBuilder) {
        this.maxPrecipitation = precipitationConfiguratorBuilder.maxPrecipitation;
    }

    public static class PrecipitationConfiguratorBuilder {
        private double maxPrecipitation;

        private PrecipitationConfiguratorBuilder() {
            this(Constraints.MAX_PRECIPITATION_PERCENTS.getValue());
        }

        private PrecipitationConfiguratorBuilder(double maxPrecipitation) {
            this.maxPrecipitation = maxPrecipitation;
        }

        public PrecipitationConfiguratorBuilder setPrecipitation(double maxPrecipitation) {
            this.maxPrecipitation = maxPrecipitation;
            return this;
        }

        public PrecipitationConfigurator build() {
            return new PrecipitationConfigurator(this);
        }
    }
}