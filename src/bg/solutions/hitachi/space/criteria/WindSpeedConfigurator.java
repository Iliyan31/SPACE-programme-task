package bg.solutions.hitachi.space.criteria;

import bg.solutions.hitachi.space.entities.DayWeatherForecast;
import bg.solutions.hitachi.space.enums.Constraints;

public class WindSpeedConfigurator {
    private final double maxWindSpeed;

    public double getMaxWindSpeed() {
        return maxWindSpeed;
    }

    public boolean isWindSpeedSuitable(DayWeatherForecast dayWeatherForecast) {
        return dayWeatherForecast.windSpeed() <= maxWindSpeed;
    }


    public static WindSpeedConfiguratorBuilder builder() {
        return new WindSpeedConfiguratorBuilder();
    }

    private WindSpeedConfigurator(WindSpeedConfiguratorBuilder windSpeedConfiguratorBuilder) {
        this.maxWindSpeed = windSpeedConfiguratorBuilder.maxWindSpeed;
    }

    public static class WindSpeedConfiguratorBuilder {
        private double maxWindSpeed;

        private WindSpeedConfiguratorBuilder() {
            this(Constraints.MAX_WIND_SPEED.getValue());
        }

        private WindSpeedConfiguratorBuilder(double maxWindSpeed) {
            this.maxWindSpeed = maxWindSpeed;
        }

        public WindSpeedConfiguratorBuilder setWindSpeed(double maxWindSpeed) {
            this.maxWindSpeed = maxWindSpeed;
            return this;
        }

        public WindSpeedConfigurator build() {
            return new WindSpeedConfigurator(this);
        }
    }
}