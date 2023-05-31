package bg.solutions.hitachi.space.mission;

import bg.solutions.hitachi.space.entities.DayWeatherForecast;
import bg.solutions.hitachi.space.enums.Cloud;
import bg.solutions.hitachi.space.enums.Constraints;
import bg.solutions.hitachi.space.enums.WeatherParams;
import bg.solutions.hitachi.space.exceptions.EmptyWeatherDataFile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class SpaceMission extends SpaceMissionValidator implements SpaceMissionAPI {
    private static final String IS_LIGHTNING = "Yes";
    private static final String DELIMITER = ",";
    private static final int NUMBER_OF_ROWS = 7;
    private static final int NO_APPROPRIATE_DAY_TO_LAUNCH = -1;
    private final boolean isGermanSet;
    private final String filePath;
    private final String senderEmailAddress;
    private final String password;
    private final String receiverEmailAddress;
    private final Queue<DayWeatherForecast> dayWeatherForecasts;
    private final List<String[]> forecastBuffer;

    public SpaceMission(boolean isGermanSet, String filePath, String senderEmailAddress, String password,
                        String receiverEmailAddress)
        throws FileNotFoundException {
        validateFilePath(filePath, isGermanSet);
        validateSenderEmailAddress(senderEmailAddress, isGermanSet);
        validatePassword(password, isGermanSet);
        validateReceiverEmailAddress(receiverEmailAddress, isGermanSet);

        this.isGermanSet = isGermanSet;
        this.filePath = filePath;
        this.senderEmailAddress = senderEmailAddress;
        this.password = password;
        this.receiverEmailAddress = receiverEmailAddress;
        this.forecastBuffer = new ArrayList<>();
        this.dayWeatherForecasts = new PriorityQueue<>
            (Comparator.comparing(DayWeatherForecast::windSpeed)
            .thenComparing(DayWeatherForecast::humidity));

        int numberOfColumns = getNumberOfColumns(filePath);
        getWeatherData(numberOfColumns, new FileReader(filePath));
    }

    @Override
    public int findPerfectDayForSpaceShuttleLaunch() {
        if (dayWeatherForecasts.size() == 0) {
            return NO_APPROPRIATE_DAY_TO_LAUNCH;
        }

        return dayWeatherForecasts.peek().dayNumber();
    }

    private boolean checkForNonEmptyWeatherData(Reader weatherDataReader) {
        try {
            return weatherDataReader.ready();
        } catch (IOException e) {
            if (isGermanSet) {
                throw new RuntimeException(
                    "Bei der Suche nach einem leeren Wetterdatenleser ist in der " +
                        "Methode ready() ein Problem aufgetreten!", e);
            }

            throw new RuntimeException(
                "There was problem in ready() method when checking for empty weather data reader!", e);
        }
    }

    private int getNumberOfColumns(String filePath) throws FileNotFoundException {
        Reader reader = new FileReader(filePath);

        if (checkForNonEmptyWeatherData(reader)) {
            try (var bufferedReader = new BufferedReader(reader)) {
                return bufferedReader.readLine().split(DELIMITER).length;

            } catch (IOException e) {
                if (isGermanSet) {
                    throw new RuntimeException("Beim Auslesen der Wetterdaten ist ein Problem aufgetreten", e);
                }

                throw new RuntimeException("There was problem while reading from the weather data", e);
            }
        }

        if (isGermanSet) {
            throw new EmptyWeatherDataFile("Die angegebene Datei enthält keine Wetterdaten!");
        }

        throw new EmptyWeatherDataFile("There is no weather data in the given file!");
    }

    private void getWeatherData(int numberOfColumns, Reader weatherDataReader) {
        try (var bufferedReader = new BufferedReader(weatherDataReader)) {
            fillForecastBuffer(bufferedReader);
            validateForecastBuffer(forecastBuffer, NUMBER_OF_ROWS, isGermanSet);
            convertAllRawDataToEntity(numberOfColumns);

        } catch (IOException e) {
            if (isGermanSet) {
                throw new RuntimeException("Beim Auslesen der Wetterdaten ist ein Problem aufgetreten", e);
            }

            throw new RuntimeException("There was problem while reading from the weather data", e);
        }
    }

    private void fillForecastBuffer(BufferedReader bufferedReader) throws IOException {
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            forecastBuffer.add(line.split(DELIMITER));
        }
    }

    private void convertAllRawDataToEntity(final int numColumns) {
        for (int i = 1; i < numColumns; i++) {
            DayWeatherForecast dayWeatherForecast = convertDataToEntity(i);

            if (canLaunch(dayWeatherForecast)) {
                dayWeatherForecasts.add(dayWeatherForecast);
            }
        }
    }

    private DayWeatherForecast convertDataToEntity(int index) {
        int day = Integer.parseInt(forecastBuffer.get(WeatherParams.DAYS.getValue())[index]);
        double temperature = Double.parseDouble(forecastBuffer.get(WeatherParams.TEMPERATURES.getValue())[index]);
        double windSpeed = Double.parseDouble(forecastBuffer.get(WeatherParams.WIND_SPEEDS.getValue())[index]);
        double humidity = Double.parseDouble(forecastBuffer.get(WeatherParams.HUMIDITIES.getValue())[index]);
        double precipitation = Double.parseDouble(forecastBuffer.get(WeatherParams.PRECIPITATIONS.getValue())[index]);
        boolean lightning = areThereLightnings(forecastBuffer.get(WeatherParams.LIGHTNINGS.getValue())[index]);
        Cloud cloud = Cloud.valueOf(forecastBuffer.get(WeatherParams.CLOUDS.getValue())[index].toUpperCase());

        return new DayWeatherForecast(day, temperature, windSpeed, humidity, precipitation, lightning, cloud);
    }

    private boolean areThereLightnings(String lightning) {
        return lightning.equals(IS_LIGHTNING);
    }

    private boolean canLaunch(DayWeatherForecast dayWeatherForecast) {
        return isTemperatureSuitable(dayWeatherForecast) &&
            isWindSpeedSuitable(dayWeatherForecast) &&
            isHumiditySuitable(dayWeatherForecast) &&
            isThereNoPrecipitation(dayWeatherForecast) &&
            areThereNoLightnings(dayWeatherForecast) &&
            areCloudsSuitable(dayWeatherForecast);
    }

    private boolean isTemperatureSuitable(DayWeatherForecast dayWeatherForecast) {
        return dayWeatherForecast.temperature() > Constraints.MIN_TEMPERATURE_VALUE.getValue() &&
            dayWeatherForecast.temperature() < Constraints.MAX_TEMPERATURE_VALUE.getValue();
    }

    private boolean isWindSpeedSuitable(DayWeatherForecast dayWeatherForecast) {
        return dayWeatherForecast.windSpeed() <= Constraints.MAX_WIND_SPEED.getValue();
    }

    private boolean isHumiditySuitable(DayWeatherForecast dayWeatherForecast) {
        return dayWeatherForecast.humidity() <= Constraints.MAX_HUMIDITY_PERCENT.getValue();
    }

    private boolean isThereNoPrecipitation(DayWeatherForecast dayWeatherForecast) {
        return dayWeatherForecast.precipitation() <= Constraints.MAX_PRECIPITATION_PERCENTS.getValue();
    }

    private boolean areThereNoLightnings(DayWeatherForecast dayWeatherForecast) {
        return !dayWeatherForecast.lightning();
    }

    private boolean areCloudsSuitable(DayWeatherForecast dayWeatherForecast) {
        return dayWeatherForecast.clouds() == Cloud.CIRRUS || dayWeatherForecast.clouds() == Cloud.STRATUS;
    }
}