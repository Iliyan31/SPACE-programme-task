package bg.solutions.hitachi.space;

import bg.solutions.hitachi.space.criteria.CloudsConfigurator;
import bg.solutions.hitachi.space.criteria.CriteriaConfigurator;
import bg.solutions.hitachi.space.criteria.HumidityConfigurator;
import bg.solutions.hitachi.space.criteria.LightningsConfigurator;
import bg.solutions.hitachi.space.criteria.PrecipitationConfigurator;
import bg.solutions.hitachi.space.criteria.TemperatureConfigurator;
import bg.solutions.hitachi.space.criteria.WindSpeedConfigurator;
import bg.solutions.hitachi.space.enums.Cloud;
import bg.solutions.hitachi.space.mission.SpaceMission;
import bg.solutions.hitachi.space.mission.SpaceMissionAPI;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SpaceMissionApp {
    private static final int NO_APPROPRIATE_DAY_TO_LAUNCH = -1;
    private static boolean isGermanSet;
    private static SpaceMissionAPI spaceMission = null;

    private static void printTextLn(String englishText, String germanText) {
        if (!isGermanSet) {
            System.out.println(englishText);
        } else {
            System.out.println(germanText);
        }
    }

    private static void printText(String englishText, String germanText) {
        if (!isGermanSet) {
            System.out.print(englishText);
        } else {
            System.out.print(germanText);
        }
    }

    private static CloudsConfigurator createCloudsConfig(Scanner appInput) {
        printTextLn("Please add clouds which are forbidden for the space shuttle to launch. The cloud " +
                "types are: Cumulus, Stratus, Nimbus and Cirrus!",
            "Bitte fügen Sie Wolken hinzu, die für den Start des Space Shuttles verboten sind. " +
                "Die Wolkentypen sind: Cumulus, Stratus, Nimbus und Cirrus!");

        printTextLn("To add the clouds, type the cloud name as written above separated with space! :",
            "Um die Wolken hinzuzufügen, geben Sie den Wolkennamen wie " +
                "oben beschrieben ein, getrennt durch Leerzeichen! :");

        String input = appInput.nextLine().replaceAll("\\s+", " ").strip();

        List<Cloud> clouds = Arrays.stream(input.split(" "))
            .map(s -> Cloud.valueOf(s.toUpperCase()))
            .toList();

        return CloudsConfigurator.builder()
            .setForbiddenClouds(clouds)
            .build();
    }

    private static LightningsConfigurator createLightningConfig(Scanner appInput) {
        printText("Please add whether or not lightnings are allowed (Yes/No): ",
            "Bitte fügen Sie hinzu, ob Blitze erlaubt sind oder nicht (Ja/Nein): ");

        String input = appInput.nextLine();

        boolean value = input.equalsIgnoreCase("Yes") || input.equalsIgnoreCase("Ja");

        return LightningsConfigurator.builder()
            .setLightnings(value)
            .build();
    }

    private static PrecipitationConfigurator createPrecipitationConfig(Scanner appInput) {
        printText("Please add max precipitation percentage: ", "Bitte maximalen Niederschlagsprozentsatz hinzufügen: ");

        double maxValue = Double.parseDouble(appInput.nextLine());

        return PrecipitationConfigurator.builder()
            .setPrecipitation(maxValue)
            .build();
    }

    private static HumidityConfigurator createHumidityConfig(Scanner appInput) {
        printText("Please add max humidity percentage: ",
            "Bitte fügen Sie den maximalen Luftfeuchtigkeitsprozentsatz hinzu: ");

        double maxValue = Double.parseDouble(appInput.nextLine());

        return HumidityConfigurator.builder()
            .setHumidity(maxValue)
            .build();
    }

    private static WindSpeedConfigurator createWindSpeedConfig(Scanner appInput) {
        printText("Please add max wind speed: ", "Bitte maximale Windgeschwindigkeit hinzufügen: ");

        double maxValue = Double.parseDouble(appInput.nextLine());

        return WindSpeedConfigurator.builder()
            .setWindSpeed(maxValue)
            .build();
    }

    private static TemperatureConfigurator createTemperatureConfig(Scanner appInput) {
        printText("Please add min temperature: ", "Bitte Mindesttemperatur hinzufügen: ");
        double minValue = Double.parseDouble(appInput.nextLine());

        printText("Please add max temperature: ", "Bitte maximale Temperatur hinzufügen: ");
        double maxValue = Double.parseDouble(appInput.nextLine());

        return TemperatureConfigurator.builder()
            .setMinTemperature(minValue)
            .setMaxTemperature(maxValue)
            .build();
    }

    private static CriteriaConfigurator generateCriteriaByInput(Scanner appInput) {
        TemperatureConfigurator temperatureConfigurator = createTemperatureConfig(appInput);
        WindSpeedConfigurator windSpeedConfigurator = createWindSpeedConfig(appInput);
        HumidityConfigurator humidityConfigurator = createHumidityConfig(appInput);
        PrecipitationConfigurator precipitationConfigurator = createPrecipitationConfig(appInput);
        LightningsConfigurator lightningsConfigurator = createLightningConfig(appInput);
        CloudsConfigurator cloudsConfigurator = createCloudsConfig(appInput);

        return new CriteriaConfigurator(temperatureConfigurator, windSpeedConfigurator,
            humidityConfigurator, precipitationConfigurator,
            lightningsConfigurator, cloudsConfigurator);
    }

    private static CriteriaConfigurator generateCriteriaDefault() {
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

        return new CriteriaConfigurator(temperatureConfigurator, windSpeedConfigurator,
            humidityConfigurator, precipitationConfigurator,
            lightningsConfigurator, cloudsConfigurator);
    }

    private static void prepareSpaceMission(Scanner appInput) {
        printTextLn("To proceed you need to enter 4 must parameters as follows:",
            "Um fortzufahren, müssen Sie die folgenden vier Parameter eingeben:");
        printText("The first one must be path to file: ", "Der erste muss der Pfad zur Datei sein: ");
        String filePath = appInput.nextLine();


        printText("The second one must be Sender email address: ",
            "Die zweite muss die E-Mail-Adresse des Absenders sein: ");
        String sender = appInput.nextLine();


        printText("The third one must be Password: ", "Das dritte muss Passwort sein: ");
        String password = appInput.nextLine();


        printText("The fourth one must be Receiver email address: ",
            "Der vierte muss die E-Mail-Adresse des Empfängers sein: ");
        String receiver = appInput.nextLine();


        try {
            CriteriaConfigurator criteriaConfigurator;
            printTextLn("Would you like to add your own parameters? Yes/No",
                "Möchten Sie Ihre eigenen Parameter hinzufügen? Ja/Nein");

            String response = appInput.nextLine();

            if (response.equalsIgnoreCase("Yes") || response.equalsIgnoreCase("Ja")) {
                criteriaConfigurator = generateCriteriaByInput(appInput);
            } else {
                criteriaConfigurator = generateCriteriaDefault();
            }

            spaceMission = new SpaceMission(isGermanSet, filePath, sender, password, receiver,
                criteriaConfigurator);

        } catch (FileNotFoundException e) {
            printTextLn("There is no such file!", "Es gibt keine solche Datei!");
            printTextLn("Please enter correct filepath!", "Bitte geben Sie den korrekten Dateipfad ein!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            printTextLn("Error occurred in the app, please restart with \"start\" command",
                "In der App ist ein Fehler aufgetreten, bitte starten Sie mit dem Befehl „Start“ neu.");
        }
    }

    private static void printWarnText() {
        printTextLn("You need to enter \"start\" command first!",
            "Sie müssen zuerst den Befehl \"Start\" eingeben!");
    }

    private static void chooseLanguage(Scanner appInput) {
        System.out.print("Please choose your language (EN/GE): ");
        String input = "a";

        while (!input.equalsIgnoreCase("GE") && !input.equalsIgnoreCase("EN")) {

            input = appInput.nextLine();

            if (input.equalsIgnoreCase("GE")) {
                isGermanSet = true;
                break;
            } else if (input.equalsIgnoreCase("EN")) {
                break;
            }

            System.out.println("Please enter correct data for language!");
            System.out.print("Please choose your language (EN/GE): ");
        }
    }

    private static void printStartText() {
        printTextLn("Hello to the space program!", "Hallo zum Weltraumprogramm!");
        printTextLn(
            "The commands for the app are: \"start\", \"stop\", \"EN\", \"GE\", \"find_perfect_day\", " +
                "\"generate_report\" and \"send_email\".",
            "Die Befehle für die App sind: \"start\", \"stoppen\", \"EN\", \"GE\", \"perfekten_tag\", " +
                "\"bericht_generieren\" und \"email_senden\".");
        printTextLn("To proceed using the app you need to enter \"start\" text in the field " +
                "and to stop it you need to enter \"stop\"!",
            "Um mit der Nutzung der App fortzufahren, müssen Sie den Text „Start“ in das " +
                "Feld eingeben. Um sie zu stoppen, müssen Sie „Stopp“ eingeben!");
        printTextLn("If you want to change the language of the app you need to type \"EN\" for English and " +
                "\"GE\" for German!",
            "Wenn Sie die Sprache der App ändern möchten, müssen Sie „EN“ für Englisch und „GE“ für " +
                "Deutsch eingeben!");
        printTextLn("You can use \"find_perfect_day\", \"generate_report\" and \"send_email\" " +
                "commands only if \"start\" command was previously pressed!",
            "Sie können die Befehle „find_perfect_day“, „generate_report“ und „send_email“ nur " +
                "verwenden, wenn zuvor der Befehl „start“ gedrückt wurde!");
    }

    public static void main(String[] args) {
        Scanner appInput = new Scanner(System.in);
        String appCommand;

        chooseLanguage(appInput);
        printStartText();

        while (true) {
            printText("Please input command: ", "Bitte geben Sie den Befehl ein: ");

            appCommand = appInput.nextLine();

            if (appCommand.equalsIgnoreCase("start")) {

                prepareSpaceMission(appInput);

            } else if ((appCommand.equalsIgnoreCase("find_perfect_day") && !isGermanSet) ||
                (appCommand.equalsIgnoreCase("perfekten_tag") && isGermanSet)) {
                if (spaceMission == null) {
                    printWarnText();
                } else {
                    int perfectDay = spaceMission.findPerfectDayForSpaceShuttleLaunch();

                    if (perfectDay == NO_APPROPRIATE_DAY_TO_LAUNCH) {
                        printTextLn("Unfortunately there is no perfect day to launch the space shuttle!",
                            "Leider gibt es keinen perfekten Tag für den Start des Space Shuttles!");
                    } else {
                        printTextLn("The perfect day to launch the space shuttle is day No: " + perfectDay,
                            "Der perfekte Tag, um das Spache-Shuttle zu starten, ist Tag No" + perfectDay);
                    }
                }


            } else if ((appCommand.equalsIgnoreCase("generate_report") && !isGermanSet) ||
                (appCommand.equalsIgnoreCase("bericht_generieren") && isGermanSet)) {

                if (spaceMission == null) {
                    printWarnText();
                } else {
                    System.out.println(spaceMission.generateWeatherReport());
                }

            } else if ((appCommand.equalsIgnoreCase("send_email") && !isGermanSet) ||
                (appCommand.equalsIgnoreCase("email_senden") && isGermanSet)) {

                if (spaceMission == null) {
                    printWarnText();
                } else {
                    System.out.println(spaceMission.sendEmail());
                }

            } else if ((appCommand.equalsIgnoreCase("stop") && !isGermanSet) ||
                (isGermanSet && appCommand.equalsIgnoreCase("stoppen"))) {

                printTextLn("Stopping the program!", "Stoppen Sie das Programm!");
                printTextLn("Thank you for using the program!", "Vielen Dank, dass Sie das Programm nutzen!");

                return;
            } else if (appCommand.equalsIgnoreCase("stoppen")) {
                System.out.println("Note that the command \"stop\" only for German is \"stoppen\"!");
            } else if (appCommand.equalsIgnoreCase("stop")) {
                System.out.println("Beachten Sie, dass der Befehl \"stop\" für Deutsch \"stoppen\" lautet!");
            } else if (appCommand.equalsIgnoreCase("EN")) {
                System.out.println(
                    "The commands for the app are: \"start\", \"stop\", \"EN\", \"GE\", \"find_perfect_day\", " +
                        "\"generate_report\" and \"send_email\".");

                isGermanSet = false;
            } else if (appCommand.equalsIgnoreCase("GE")) {
                System.out.println(
                    "Die Befehle für die App sind: \"start\", \"stoppen\", \"EN\", \"GE\", \"perfekten_tag\", " +
                        "\"bericht_generieren\" und \"email_senden\".");

                isGermanSet = true;
            } else {
                printTextLn(
                    "The supported commands are \"start\", \"stop\", \"EN\", \"GE\", \"find_perfect_day\", " +
                        "\"generate_report\" and \"send_email\"!",
                    "Die unterstützten Befehle sind \"start\", \"stoppen\", \"EN\", \"GE\", \"perfekten_tag\", " +
                        "\"bericht_generieren\" und \"email_senden\"!");
            }
        }
    }
}