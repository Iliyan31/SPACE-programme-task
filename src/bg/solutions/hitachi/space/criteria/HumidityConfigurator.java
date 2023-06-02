package bg.solutions.hitachi.space.criteria;

import bg.solutions.hitachi.space.entities.DayWeatherForecast;
import bg.solutions.hitachi.space.enums.Constraints;

public class HumidityConfigurator {
    private final double maxHumidity;

    public double getMaxHumidity() {
        return maxHumidity;
    }

    public boolean isHumiditySuitable(DayWeatherForecast dayWeatherForecast) {
        return dayWeatherForecast.humidity() <= maxHumidity;
    }

    public static HumidityConfiguratorBuilder builder() {
        return new HumidityConfiguratorBuilder();
    }

    private HumidityConfigurator(HumidityConfiguratorBuilder humidityConfiguratorBuilder) {
        this.maxHumidity = humidityConfiguratorBuilder.maxHumidity;
    }

    public static class HumidityConfiguratorBuilder {
        private double maxHumidity;

        private HumidityConfiguratorBuilder() {
            this(Constraints.MAX_HUMIDITY_PERCENT.getValue());
        }

        private HumidityConfiguratorBuilder(double maxHumidity) {
            this.maxHumidity = maxHumidity;
        }

        public HumidityConfiguratorBuilder setHumidity(double maxHumidity) {
            this.maxHumidity = maxHumidity;
            return this;
        }

        public HumidityConfigurator build() {
            return new HumidityConfigurator(this);
        }
    }
}