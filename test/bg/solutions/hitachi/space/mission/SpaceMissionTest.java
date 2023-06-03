package bg.solutions.hitachi.space.mission;


import bg.solutions.hitachi.space.criteria.CloudsConfigurator;
import bg.solutions.hitachi.space.criteria.CriteriaConfigurator;
import bg.solutions.hitachi.space.criteria.HumidityConfigurator;
import bg.solutions.hitachi.space.criteria.LightningsConfigurator;
import bg.solutions.hitachi.space.criteria.PrecipitationConfigurator;
import bg.solutions.hitachi.space.criteria.TemperatureConfigurator;
import bg.solutions.hitachi.space.criteria.WindSpeedConfigurator;
import bg.solutions.hitachi.space.enums.Cloud;
import bg.solutions.hitachi.space.exceptions.EmptyWeatherDataFile;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SpaceMissionTest {
    private static CriteriaConfigurator criteriaConfigurator;

    @BeforeAll
    static void setUp() {
        TemperatureConfigurator temperatureConfigurator = TemperatureConfigurator.builder()
            .build();

        WindSpeedConfigurator windSpeedConfigurator = WindSpeedConfigurator.builder()
            .build();

        HumidityConfigurator humidityConfigurator = HumidityConfigurator.builder()
            .build();

        PrecipitationConfigurator precipitationConfigurator = PrecipitationConfigurator.builder()
            .build();

        LightningsConfigurator lightningsConfigurator = LightningsConfigurator.builder()
            .build();

        CloudsConfigurator cloudsConfigurator = CloudsConfigurator.builder()
            .setCloud(Cloud.CUMULUS)
            .setCloud(Cloud.NIMBUS)
            .build();

        criteriaConfigurator = new CriteriaConfigurator(temperatureConfigurator, windSpeedConfigurator,
            humidityConfigurator, precipitationConfigurator,
            lightningsConfigurator, cloudsConfigurator);
    }

    @Test
    void createSpaceMissionWithNullFilePathEN() {
        assertThrows(IllegalArgumentException.class, () -> new SpaceMission(false, null, null, null,
            null, null), "File path cannot be null");
    }

    @Test
    void createSpaceMissionWithNullFilePathGE() {
        assertThrows(IllegalArgumentException.class, () -> new SpaceMission(true, null, null, null,
            null, null), "File path cannot be null");
    }

    @Test
    void createSpaceMissionWithEmptyFilePathEN() {
        assertThrows(IllegalArgumentException.class, () -> new SpaceMission(false, "", null, null,
            null, null), "File path cannot be empty");
    }

    @Test
    void createSpaceMissionWithEmptyFilePathGE() {
        assertThrows(IllegalArgumentException.class, () -> new SpaceMission(true, "", null, null,
            null, null), "File path cannot be empty");
    }

    @Test
    void createSpaceMissionWithBlankFilePathEN() {
        assertThrows(IllegalArgumentException.class, () -> new SpaceMission(false, " ", null, null,
            null, null), "File path cannot be blank");
    }

    @Test
    void createSpaceMissionWithBlankFilePathGE() {
        assertThrows(IllegalArgumentException.class, () -> new SpaceMission(true, " ", null, null,
            null, null), "File path cannot be blank");
    }

    @Test
    void createSpaceMissionWithNullSenderEmailAddressEN() {
        assertThrows(IllegalArgumentException.class, () -> new SpaceMission(false, "a", null, null,
            null, null), "Sender cannot be null");
    }

    @Test
    void createSpaceMissionWithNullSenderEmailAddressGE() {
        assertThrows(IllegalArgumentException.class, () -> new SpaceMission(true, "a", null, null,
            null, null), "Sender cannot be null");
    }

    @Test
    void createSpaceMissionWithEmptySenderEmailAddressEN() {
        assertThrows(IllegalArgumentException.class, () -> new SpaceMission(false, "a", "", null,
            null, null), "Sender cannot be empty");
    }

    @Test
    void createSpaceMissionWithEmptySenderEmailAddressGE() {
        assertThrows(IllegalArgumentException.class, () -> new SpaceMission(true, "a", "", null,
            null, null), "Sender cannot be empty");
    }

    @Test
    void createSpaceMissionWithBlankSenderEmailAddressEN() {
        assertThrows(IllegalArgumentException.class, () -> new SpaceMission(false, "a", " ", null,
            null, null), "Sender cannot be blank");
    }

    @Test
    void createSpaceMissionWithBlankSenderEmailAddressGE() {
        assertThrows(IllegalArgumentException.class, () -> new SpaceMission(true, "a", " ", null,
            null, null), "Sender cannot be blank");
    }

    @Test
    void createSpaceMissionWithNullPasswordEN() {
        assertThrows(IllegalArgumentException.class, () -> new SpaceMission(false, "a", "null", null,
            null, null), "Password cannot be null");
    }

    @Test
    void createSpaceMissionWithNullPasswordGE() {
        assertThrows(IllegalArgumentException.class, () -> new SpaceMission(true, "a", "null", null,
            null, null), "Password cannot be null");
    }

    @Test
    void createSpaceMissionWithEmptyPasswordEN() {
        assertThrows(IllegalArgumentException.class, () -> new SpaceMission(false, "a", "a", "",
            null, null), "Password cannot be empty");
    }

    @Test
    void createSpaceMissionWithEmptyPasswordGE() {
        assertThrows(IllegalArgumentException.class, () -> new SpaceMission(true, "a", "a", "",
            null, null), "Password cannot be empty");
    }

    @Test
    void createSpaceMissionWithBlankPasswordEN() {
        assertThrows(IllegalArgumentException.class, () -> new SpaceMission(false, "a", "a", " ",
            null, null), "Password cannot be blank");
    }

    @Test
    void createSpaceMissionWithBlankPasswordGE() {
        assertThrows(IllegalArgumentException.class, () -> new SpaceMission(true, "a", "a", " ",
            null, null), "Password cannot be blank");
    }

    @Test
    void createSpaceMissionWithNullReceiverEmailAddressEN() {
        assertThrows(IllegalArgumentException.class, () -> new SpaceMission(false, "a", "null", "null",
            null, null), "Receiver cannot be null");
    }

    @Test
    void createSpaceMissionWithNullReceiverEmailAddressGE() {
        assertThrows(IllegalArgumentException.class, () -> new SpaceMission(true, "a", "null", "null",
            null, null), "Receiver cannot be null");
    }

    @Test
    void createSpaceMissionWithEmptyReceiverEmailAddressEN() {
        assertThrows(IllegalArgumentException.class, () -> new SpaceMission(false, "a", "a", "null",
            "", null), "Receiver cannot be empty");
    }

    @Test
    void createSpaceMissionWithEmptyReceiverEmailAddressGE() {
        assertThrows(IllegalArgumentException.class, () -> new SpaceMission(true, "a", "a", "null",
            "", null), "Receiver cannot be empty");
    }

    @Test
    void createSpaceMissionWithBlankReceiverEmailAddressEN() {
        assertThrows(IllegalArgumentException.class, () -> new SpaceMission(false, "a", "a ", "null",
            " ", null), "Receiver cannot be blank");
    }

    @Test
    void createSpaceMissionWithBlankReceiverEmailAddressGE() {
        assertThrows(IllegalArgumentException.class, () -> new SpaceMission(true, "a", "a ", "null",
            " ", null), "Receiver cannot be blank");
    }

    @Test
    void createSpaceMissionWithNoSuchFile() {
        assertThrows(FileNotFoundException.class,
            () -> new SpaceMission(false, "test/solutions/hitachi/space/csv/Test1.csv", "sender", "password",
                "receiver",
                criteriaConfigurator), "Correct and existing file path must be passed!");

    }

    @Test
    void testValidationForNotEnoughRowsEN() {
        assertThrows(IllegalArgumentException.class,
            () -> new SpaceMission(false, "test/bg/solutions/hitachi/space/csv/NotEnoughRows.csv", "sender", "password",
                "receiver",
                criteriaConfigurator), "The rows of the file must be 7!");
    }

    @Test
    void testValidationForNotEnoughRowsGE() {
        assertThrows(IllegalArgumentException.class,
            () -> new SpaceMission(true, "test/bg/solutions/hitachi/space/csv/NotEnoughRows.csv", "sender", "password",
                "receiver",
                criteriaConfigurator), "The rows of the file must be 7!");
    }


    @Test
    void testValidationEmptyFileEN() {
        assertThrows(
            EmptyWeatherDataFile.class,
            () -> new SpaceMission(false, "test/bg/solutions/hitachi/space/csv/EmptyFile.csv", "sender", "password",
                "receiver",
                criteriaConfigurator), "SpaceMission should not accept empty file!");
    }

    @Test
    void testValidationForEmptyFileGE() {
        assertThrows(EmptyWeatherDataFile.class,
            () -> new SpaceMission(true, "test/bg/solutions/hitachi/space/csv/EmptyFile.csv", "sender", "password",
                "receiver",
                criteriaConfigurator), "SpaceMission should not accept empty file!");
    }

    @Test
    void testValidationForNotEnoughColsEN() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new SpaceMission(false, "test/bg/solutions/hitachi/space/csv/ColsError.csv", "sender", "password",
                "receiver",
                criteriaConfigurator), "SpaceMission should not accept files with different column sizes!");
    }

    @Test
    void testValidationForNotEnoughColsGE() {
        assertThrows(IllegalArgumentException.class,
            () -> new SpaceMission(true, "test/bg/solutions/hitachi/space/csv/ColsError.csv", "sender", "password",
                "receiver",
                criteriaConfigurator), "SpaceMission should not accept files with different column sizes!");
    }

    @Test
    void testValidationForEmptyOrBlankValueInCsvEN() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new SpaceMission(false, "test/bg/solutions/hitachi/space/csv/EmptyValueInCols.csv", "sender",
                "password", "receiver",
                criteriaConfigurator),
            "SpaceMission should not accept files with null, empty or blank values in columns!");
    }

    @Test
    void testValidationForEmptyOrBlankValueInCsvGE() {
        assertThrows(IllegalArgumentException.class,
            () -> new SpaceMission(true, "test/bg/solutions/hitachi/space/csv/EmptyValueInCols.csv", "sender",
                "password", "receiver",
                criteriaConfigurator),
            "SpaceMission should not accept files with null, empty or blank values in columns!");
    }

    @Test
    void testForPerfectLaunchDayTestCSV() throws FileNotFoundException {
        SpaceMission spaceMission = new SpaceMission(false, "test/bg/solutions/hitachi/space/csv/Test1.csv",
            "sender", "password", "receiver", criteriaConfigurator);

        assertEquals(10, spaceMission.findPerfectDayForSpaceShuttleLaunch(),
            "The space mission app must correctly evaluate perfect launch day for the space shuttle!");
    }

    @Test
    void testForPerfectLaunchDayTest2CSVTwoPerfectDays() throws FileNotFoundException {
        SpaceMission spaceMission = new SpaceMission(false, "test/bg/solutions/hitachi/space/csv/Test2.csv",
            "sender", "password", "receiver", criteriaConfigurator);

        assertEquals(2, spaceMission.findPerfectDayForSpaceShuttleLaunch(),
            "The space mission app must correctly evaluate perfect launch day for the space shuttle!");
    }

    // Test3.csv and Test4.csv are almost identical but wind speed and humidity changed
    // The if two days or more days are candidates for perfect day the first day of them all (the smallest by number)
    // will be taken
    @Test
    void testForPerfectLaunchDayTest3CSV() throws FileNotFoundException {
        SpaceMission spaceMission = new SpaceMission(false, "test/bg/solutions/hitachi/space/csv/Test3.csv",
            "sender", "password", "receiver", criteriaConfigurator);

        assertEquals(1, spaceMission.findPerfectDayForSpaceShuttleLaunch(),
            "The space mission app must correctly evaluate perfect launch day for the space shuttle!");
    }

    @Test
    void testForPerfectLaunchDayTest4CSV() throws FileNotFoundException {
        SpaceMission spaceMission = new SpaceMission(false, "test/bg/solutions/hitachi/space/csv/Test4.csv",
            "sender", "password", "receiver", criteriaConfigurator);

        assertEquals(2, spaceMission.findPerfectDayForSpaceShuttleLaunch(),
            "The space mission app must correctly evaluate perfect launch day for the space shuttle!");
    }

    @Test
    void testForPerfectLaunchDayTest5CSVNoSuchDay() throws FileNotFoundException {
        SpaceMission spaceMission = new SpaceMission(false, "test/bg/solutions/hitachi/space/csv/Test5.csv",
            "sender", "password", "receiver", criteriaConfigurator);

        assertEquals(-1, spaceMission.findPerfectDayForSpaceShuttleLaunch(),
            "The space mission app must correctly evaluate perfect launch day for the space shuttle!");
    }

    @Test
    void testForPerfectLaunchDayTest6CSVIdenticalDays() throws FileNotFoundException {
        SpaceMission spaceMission = new SpaceMission(false, "test/bg/solutions/hitachi/space/csv/Test6.csv",
            "sender", "password", "receiver", criteriaConfigurator);

        assertEquals(1, spaceMission.findPerfectDayForSpaceShuttleLaunch(),
            "The space mission app must correctly evaluate perfect launch day for the space shuttle!");
    }

    @Test
    void testGenerateReportEN() throws FileNotFoundException {
        SpaceMission spaceMission = new SpaceMission(false, "test/bg/solutions/hitachi/space/csv/Test1.csv",
            "sender", "password", "receiver", criteriaConfigurator);

        assertEquals("Correctly exported csv file to report package!", spaceMission.generateWeatherReport(),
            "The space mission app must correctly generate report!");
    }

    @Test
    void testGenerateReportGE() throws FileNotFoundException {
        SpaceMission spaceMission = new SpaceMission(true, "test/bg/solutions/hitachi/space/csv/Test1.csv",
            "sender", "password", "receiver", criteriaConfigurator);

        assertEquals("Korrekt exportierte CSV-Datei in das Berichtspaket!", spaceMission.generateWeatherReport(),
            "The space mission app must correctly generate report!");
    }

    @Test
    void testSendEmailEN() throws FileNotFoundException {
        SpaceMission spaceMission = new SpaceMission(false, "test/bg/solutions/hitachi/space/csv/Test1.csv",
            "InputCorrectSenderEmail", "InputCorrectPassword", "InputCorrectReceiverEmail", criteriaConfigurator);

        assertEquals("Successfully sent message!", spaceMission.sendEmail(),
            "The space mission app must correctly generate report!");
    }

    @Test
    void testSendEmailGE() throws FileNotFoundException {
        SpaceMission spaceMission = new SpaceMission(true, "test/bg/solutions/hitachi/space/csv/Test1.csv",
            "InputCorrectSenderEmail", "InputCorrectPassword", "InputCorrectReceiverEmail", criteriaConfigurator);

        assertEquals("Nachricht erfolgreich gesendet!", spaceMission.sendEmail(),
            "The space mission app must correctly generate report!");
    }

    @Test
    void testSendEmailInvalidCredentialsEN() throws FileNotFoundException {
        SpaceMission spaceMission = new SpaceMission(false, "test/bg/solutions/hitachi/space/csv/Test1.csv",
            "sender", "password", "receiver", criteriaConfigurator);

        assertEquals("There was error while sending the message! Please try again by inputting correct sender email, " +
                "sender password and receiver email!", spaceMission.sendEmail(),
            "The space mission app must correctly send email!");
    }

    @Test
    void testSendEmailInvalidCredentialsGE() throws FileNotFoundException {
        SpaceMission spaceMission = new SpaceMission(true, "test/bg/solutions/hitachi/space/csv/Test1.csv",
            "sender", "password", "receiver", criteriaConfigurator);

        assertEquals("Beim Senden der Nachricht ist ein Fehler aufgetreten! " +
                "Bitte versuchen Sie es erneut, indem Sie die korrekte E-Mail-Adresse des Absenders, " +
                "das Passwort des Absenders und die E-Mail-Adresse des Empfängers eingeben!", spaceMission.sendEmail(),
            "The space mission app must correctly send email!");
    }

}