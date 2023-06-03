package bg.solutions.hitachi.space.entities;

import bg.solutions.hitachi.space.enums.Cloud;

public record DayWeatherForecast(int dayNumber, double temperature, double windSpeed, double humidity,
                                 double precipitation, boolean lightning, Cloud clouds) {
    public DayWeatherForecast {
        if (dayNumber <= 0) {
            throw new IllegalArgumentException("The day number cannot be below 0!");
        }

        if (windSpeed < 0) {
            throw new IllegalArgumentException("The wind speed cannot be below 0!");
        }

        if (humidity < 0 || humidity > 100) {
            throw new IllegalArgumentException("The humidity % cannot be below 0 or above 100!");
        }

        if (precipitation < 0 || precipitation > 100) {
            throw new IllegalArgumentException("The precipitation % cannot be below 0 or above 100!");
        }

        if (clouds == null) {
            throw new IllegalArgumentException("The clouds value cannot be null!");
        }
    }
}