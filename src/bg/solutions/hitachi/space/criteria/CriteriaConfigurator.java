package bg.solutions.hitachi.space.criteria;

import bg.solutions.hitachi.space.entities.DayWeatherForecast;

public record CriteriaConfigurator(TemperatureConfigurator temperatureConfigurator,
                                   WindSpeedConfigurator windSpeedConfigurator,
                                   HumidityConfigurator humidityConfigurator,
                                   PrecipitationConfigurator precipitationConfigurator,
                                   LightningsConfigurator lightningsConfigurator,
                                   CloudsConfigurator cloudsConfigurator) {

    public boolean isWindSpeedSuitable(DayWeatherForecast dayWeatherForecast) {
        return windSpeedConfigurator.isWindSpeedSuitable(dayWeatherForecast);
    }

    public boolean isTemperatureSuitable(DayWeatherForecast dayWeatherForecast) {
        return temperatureConfigurator.isTemperatureSuitable(dayWeatherForecast);
    }

    public boolean isThereNoPrecipitation(DayWeatherForecast dayWeatherForecast) {
        return precipitationConfigurator.isThereNoPrecipitation(dayWeatherForecast);
    }

    public boolean areThereNoLightnings(DayWeatherForecast dayWeatherForecast) {
        return lightningsConfigurator.areThereNoLightnings(dayWeatherForecast);
    }

    public boolean isHumiditySuitable(DayWeatherForecast dayWeatherForecast) {
        return humidityConfigurator.isHumiditySuitable(dayWeatherForecast);
    }

    public boolean areCloudsSuitable(DayWeatherForecast dayWeatherForecast) {
        return cloudsConfigurator.areCloudsSuitable(dayWeatherForecast);
    }
}