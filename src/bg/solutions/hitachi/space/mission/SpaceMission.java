package bg.solutions.hitachi.space.mission;

import bg.solutions.hitachi.space.entities.DayWeatherForecast;
import bg.solutions.hitachi.space.enums.Cloud;
import bg.solutions.hitachi.space.enums.Constraints;
import bg.solutions.hitachi.space.exceptions.EmptyWeatherDataFile;
import bg.solutions.hitachi.space.exceptions.NoSuitableDayToLaunchException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SpaceMission extends SpaceMissionValidator implements SpaceMissionAPI {
    private static final String IS_LIGHTNING = "Yes";
    private static final String DELIMITER = ",";
    private static final int NUMBER_OF_ROWS = 7;
    private final String filePath;
    private final String senderEmailAddress;
    private final String password;
    private final String receiverEmailAddress;

    private List<String[]> forecastBuffer;
//    private String[] days;
//    private String[] temperatures;
//    private String[] windSpeeds;
//    private String[] humidities;
//    private String[] precipitations;
//    private String[] lightnings;
//    private String[] clouds;
    private final Set<DayWeatherForecast> dayWeatherForecasts;

    public SpaceMission(String filePath, String senderEmailAddress, String password, String receiverEmailAddress)
        throws FileNotFoundException {
        validateFilePath(filePath);
        validateSenderEmailAddress(senderEmailAddress);
        validatePassword(password);
        validateReceiverEmailAddress(receiverEmailAddress);

        this.filePath = filePath;
        this.senderEmailAddress = senderEmailAddress;
        this.password = password;
        this.receiverEmailAddress = receiverEmailAddress;
        this.forecastBuffer = new ArrayList<>();
        this.dayWeatherForecasts = new HashSet<>();

        int numberOfColumns = getNumberOfColumns(filePath);
        getWeatherData(numberOfColumns, new FileReader(filePath));
    }

    @Override
    public int findPerfectDayForSpaceShuttleLaunch() {
        return dayWeatherForecasts.stream()
            .filter(this::canLaunch)
            .min(Comparator.comparing(DayWeatherForecast::windSpeed)
                .thenComparing(DayWeatherForecast::humidity))
            .orElseThrow(() -> new NoSuitableDayToLaunchException(
                "There was no suitable day found to launch the space shuttle"))
            .dayNumber();
    }

    private boolean checkForNonEmptyWeatherData(Reader weatherDataReader) {
        try {
            return weatherDataReader.ready();
        } catch (IOException e) {
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
                throw new RuntimeException("There was problem while reading from the weather data", e);
            }
        }

        throw new EmptyWeatherDataFile("There is no weather data in the given file!");
    }

    private void getWeatherData(int numberOfColumns, Reader weatherDataReader) {
        try (var bufferedReader = new BufferedReader(weatherDataReader)) {
            fillForecastBuffer(bufferedReader);

            if (forecastBuffer.size() != NUMBER_OF_ROWS) {
                throw new IllegalArgumentException("There are not enough rows!");
            }

            convertAllRawDataToEntity(numberOfColumns);
        } catch (IOException e) {
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
            dayWeatherForecasts.add(convertDataToEntity(i));
        }
    }

    private DayWeatherForecast convertDataToEntity(int index) {
        int day = Integer.parseInt(forecastBuffer.get(0)[index]);
        double temperature = Double.parseDouble(forecastBuffer.get(1)[index]);
        double windSpeed = Double.parseDouble(forecastBuffer.get(2)[index]);
        double humidity = Double.parseDouble(forecastBuffer.get(3)[index]);
        double precipitation = Double.parseDouble(forecastBuffer.get(4)[index]);
        boolean lightning = areThereLightnings(forecastBuffer.get(5)[index]);
        Cloud cloud = Cloud.valueOf(forecastBuffer.get(6)[index].toUpperCase());

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