package bg.solutions.hitachi.space.mission;

import bg.solutions.hitachi.space.entities.DayWeatherForecast;
import bg.solutions.hitachi.space.enums.Cloud;
import bg.solutions.hitachi.space.enums.Constraints;
import bg.solutions.hitachi.space.enums.WeatherParams;
import bg.solutions.hitachi.space.generators.ReportGenerator;
import bg.solutions.hitachi.space.mail.MailClient;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

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

    // I use this queue only for optimisation for the method findPerfectDayForSpaceShuttleLaunch() so I can reduce the
    // performance penalty
    private final Queue<DayWeatherForecast> dayWeatherForecasts;
    private final List<DayWeatherForecast> allDaysForecasts;
    private final ReportGenerator reportGenerator;
    private boolean isReportGenerated;

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

        this.dayWeatherForecasts = new PriorityQueue<>
            (Comparator.comparing(DayWeatherForecast::windSpeed)
                .thenComparing(DayWeatherForecast::humidity));

        this.allDaysForecasts = new ArrayList<>();

        int numberOfColumns = getNumberOfColumns(filePath);
        getWeatherData(numberOfColumns, new FileReader(filePath));

        this.reportGenerator = ReportGenerator.of(isGermanSet, allDaysForecasts);
        this.isReportGenerated = false;
    }

    @Override
    public int findPerfectDayForSpaceShuttleLaunch() {
        if (dayWeatherForecasts.size() == 0) {
            return NO_APPROPRIATE_DAY_TO_LAUNCH;
        }

        return dayWeatherForecasts.peek().dayNumber();
    }

    @Override
    public String generateWeatherReport() {
        String response = reportGenerator.generateReport();
        isReportGenerated = true;

        return response;
    }

    @Override
    public String sendEmail() {
        if (!isReportGenerated) {
            generateWeatherReport();
        }

        MailClient mailClient = new MailClient(senderEmailAddress, password, receiverEmailAddress,
            findPerfectDayForSpaceShuttleLaunch(), isGermanSet);

        return mailClient.sendEmail();
    }

    private int getNumberOfColumns(String filePath) throws FileNotFoundException {
        Reader reader = new FileReader(filePath);

        validateForEmptyFile(reader, isGermanSet);

        try (var bufferedReader = new BufferedReader(reader)) {
            return bufferedReader.readLine().split(DELIMITER).length;

        } catch (IOException e) {
            if (isGermanSet) {
                throw new RuntimeException("Beim Auslesen der Wetterdaten ist ein Problem aufgetreten", e);
            }

            throw new RuntimeException("There was problem while reading from the weather data", e);
        }
    }

    private void getWeatherData(int numberOfColumns, Reader weatherDataReader) {
        try (var bufferedReader = new BufferedReader(weatherDataReader)) {
            List<String[]> forecastBuffer = readFileByLines(bufferedReader);
            validateForecastBuffer(forecastBuffer, NUMBER_OF_ROWS, isGermanSet);

            convertAllRawDataToEntity(forecastBuffer, numberOfColumns);
        } catch (IOException e) {
            if (isGermanSet) {
                throw new RuntimeException("Beim Auslesen der Wetterdaten ist ein Problem aufgetreten", e);
            }

            throw new RuntimeException("There was problem while reading from the weather data", e);
        }
    }

    private List<String[]> readFileByLines(BufferedReader bufferedReader) throws IOException {
        String line;
        List<String[]> buffer = new ArrayList<>();

        while ((line = bufferedReader.readLine()) != null) {
            buffer.add(line.split(DELIMITER));
        }

        return buffer;
    }

    private void convertAllRawDataToEntity(List<String[]> forecastBuffer, final int numColumns) {
        for (int i = 1; i < numColumns; i++) {
            DayWeatherForecast dayWeatherForecast = convertDataToEntity(forecastBuffer, i);

            allDaysForecasts.add(dayWeatherForecast);
            if (canLaunch(dayWeatherForecast)) {
                dayWeatherForecasts.add(dayWeatherForecast);
            }
        }
    }

    private DayWeatherForecast convertDataToEntity(List<String[]> forecastBuffer, int index) {
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