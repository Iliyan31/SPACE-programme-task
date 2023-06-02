package bg.solutions.hitachi.space.generators;

import bg.solutions.hitachi.space.criteria.CriteriaConfigurator;
import bg.solutions.hitachi.space.entities.DayWeatherForecast;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class ReportGenerator {
    private static final String REPORT_PATH = "./src/bg/solutions/hitachi/space/report/WeatherReport.csv";
    private final boolean isGermanSet;
    private final TemperatureGenerator temperatureGenerator;
    private final WindSpeedGenerator windSpeedGenerator;
    private final HumidityGenerator humidityGenerator;
    private final PrecipitationGenerator precipitationGenerator;
    private final LightningsGenerator lightningsGenerator;
    private final CloudsGenerator cloudsGenerator;

    public ReportGenerator(boolean isGermanSet,
                           TemperatureGenerator temperatureGenerator,
                           WindSpeedGenerator windSpeedGenerator,
                           HumidityGenerator humidityGenerator,
                           PrecipitationGenerator precipitationGenerator,
                           LightningsGenerator lightningsGenerator,
                           CloudsGenerator cloudsGenerator) {

        this.isGermanSet = isGermanSet;
        this.temperatureGenerator = temperatureGenerator;
        this.windSpeedGenerator = windSpeedGenerator;
        this.humidityGenerator = humidityGenerator;
        this.precipitationGenerator = precipitationGenerator;
        this.lightningsGenerator = lightningsGenerator;
        this.cloudsGenerator = cloudsGenerator;
    }

    public static ReportGenerator of(boolean isGermanSet, List<DayWeatherForecast> dayWeatherForecastList,
                                     CriteriaConfigurator criteriaConfigurator) {
        return new ReportGenerator(isGermanSet,
            new TemperatureGenerator(dayWeatherForecastList, criteriaConfigurator.temperatureConfigurator()),
            new WindSpeedGenerator(dayWeatherForecastList, criteriaConfigurator.windSpeedConfigurator()),
            new HumidityGenerator(dayWeatherForecastList, criteriaConfigurator.humidityConfigurator()),
            new PrecipitationGenerator(dayWeatherForecastList, criteriaConfigurator.precipitationConfigurator()),
            new LightningsGenerator(dayWeatherForecastList, criteriaConfigurator.lightningsConfigurator()),
            new CloudsGenerator(dayWeatherForecastList, criteriaConfigurator.cloudsConfigurator()));
    }


    public String generateReport() {
        try (Writer fileWriter = new FileWriter(REPORT_PATH, false)) {
            fileWriter.write(getHeader());
            fileWriter.flush();

            fileWriter.write(getTemperatureReport());
            fileWriter.flush();

            fileWriter.write(getWindSpeedReport());
            fileWriter.flush();

            fileWriter.write(getHumidityReport());
            fileWriter.flush();

            fileWriter.write(getPrecipitationReport());
            fileWriter.flush();

            fileWriter.write(getLightningReport());
            fileWriter.flush();

            fileWriter.write(getCloudsReport());
            fileWriter.flush();

            if (isGermanSet) {
                return "Korrekt exportierte CSV-Datei in das Berichtspaket!";
            }

            return "Correctly exported csv file to report package!";
        } catch (IOException e) {
            if (isGermanSet) {
                return "Beim Erstellen des Berichts ist ein Problem aufgetreten";
            }
            return "There was problem in generating the report";
        }
    }

    private String getHeader() {
        return "Stats/Parameter" +
            "," +
            "Average value" +
            "," +
            "Max value" +
            "," +
            "Min value" +
            "," +
            "Median value" +
            "," +
            "The most appropriate launch day value by parameter" +
            System.lineSeparator();
    }

    private String getTemperatureReport() {
        return "Temperature (C)" +
            "," +
            temperatureGenerator.findAverage() +
            "," +
            temperatureGenerator.findMax() +
            "," +
            temperatureGenerator.findMin() +
            "," +
            temperatureGenerator.findMedian() +
            "," +
            temperatureGenerator.getPerfectDay() +
            System.lineSeparator();
    }

    private String getWindSpeedReport() {
        return "Wind (m/s)" +
            "," +
            windSpeedGenerator.findAverage() +
            "," +
            windSpeedGenerator.findMax() +
            "," +
            windSpeedGenerator.findMin() +
            "," +
            windSpeedGenerator.findMedian() +
            "," +
            windSpeedGenerator.getPerfectDay() +
            System.lineSeparator();
    }

    private String getHumidityReport() {
        return "Humidity (%)" +
            "," +
            humidityGenerator.findAverage() +
            "," +
            humidityGenerator.findMax() +
            "," +
            humidityGenerator.findMin() +
            "," +
            humidityGenerator.findMedian() +
            "," +
            humidityGenerator.getPerfectDay() +
            System.lineSeparator();
    }

    private String getPrecipitationReport() {
        return "Precipitation (%)" +
            "," +
            precipitationGenerator.findAverage() +
            "," +
            precipitationGenerator.findMax() +
            "," +
            precipitationGenerator.findMin() +
            "," +
            precipitationGenerator.findMedian() +
            "," +
            precipitationGenerator.getPerfectDay() +
            System.lineSeparator();
    }

    private String getLightningReport() {
        return "Lightning" +
            "," + null + "," + null + "," + null + "," + null + "," +
            lightningsGenerator.getPerfectDay() +
            System.lineSeparator();
    }

    private String getCloudsReport() {
        return "Clouds" +
            "," + null + "," + null + "," + null + "," + null + "," +
            cloudsGenerator.getPerfectDay();
    }
}