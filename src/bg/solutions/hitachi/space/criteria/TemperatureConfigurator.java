package bg.solutions.hitachi.space.criteria;

import bg.solutions.hitachi.space.entities.DayWeatherForecast;
import bg.solutions.hitachi.space.enums.Constraints;

public class TemperatureConfigurator {
    private final double minTemperature;
    private final double maxTemperature;

    public double getMinTemperature() {
        return minTemperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public boolean isTemperatureSuitable(DayWeatherForecast dayWeatherForecast) {
        return dayWeatherForecast.temperature() > minTemperature &&
            dayWeatherForecast.temperature() < maxTemperature;
    }


    public static TemperatureConfiguratorBuilder builder() {
        return new TemperatureConfiguratorBuilder();
    }

    private TemperatureConfigurator(TemperatureConfiguratorBuilder temperatureConfiguratorBuilder) {
        this.minTemperature = temperatureConfiguratorBuilder.minTemperature;
        this.maxTemperature = temperatureConfiguratorBuilder.maxTemperature;
    }

    public static class TemperatureConfiguratorBuilder {
        private double minTemperature;
        private double maxTemperature;

        private TemperatureConfiguratorBuilder() {
            this(Constraints.MIN_TEMPERATURE_VALUE.getValue(), Constraints.MAX_TEMPERATURE_VALUE.getValue());
        }

        private TemperatureConfiguratorBuilder(double minTemperature, double maxTemperature) {
            this.minTemperature = minTemperature;
            this.maxTemperature = maxTemperature;
        }

        public TemperatureConfiguratorBuilder setMinTemperature(double minTemperature) {
            this.minTemperature = minTemperature;
            return this;
        }

        public TemperatureConfiguratorBuilder setMaxTemperature(double maxTemperature) {
            this.maxTemperature = maxTemperature;
            return this;
        }

        public TemperatureConfigurator build() {
            return new TemperatureConfigurator(this);
        }
    }
}