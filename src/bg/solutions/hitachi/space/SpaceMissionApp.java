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
import java.util.Scanner;

public class SpaceMissionApp {
    public static void main(String[] args) {
        System.out.println("Hello to the space program!");
        System.out.println("To proceed using the app you need to enter \"start\" text in the field " +
            "and to stop it you need to enter \"stop\"!");
        System.out.println("If you want to change the language of the app you need to type \"EN\" for English and " +
            "\"GE\" for German!");
        System.out.println("Note that the command \"stop\" for German is \"stoppen\"!");

        Scanner appInput = new Scanner(System.in);
        String appCommand;

        boolean isGermanSet = false;

        while (true) {
            if (!isGermanSet) {
                System.out.print("Please input command: ");
            } else {
                System.out.print("Bitte geben Sie den Befehl ein: ");
            }

            appCommand = appInput.nextLine();

            if (appCommand.equals("start")) {
                if (!isGermanSet) {
                    System.out.println("To proceed you need to enter 4 must parameters as follows:");
                    System.out.print("The first one must be path to file: ");
                } else {
                    System.out.println("Um fortzufahren, müssen Sie die folgenden vier Parameter eingeben:");
                    System.out.print("Der erste muss der Pfad zur Datei sein: ");
                }
                String filePath = appInput.nextLine();


                if (!isGermanSet) {
                    System.out.print("The second one must be Sender email address: ");
                } else {
                    System.out.print("Die zweite muss die E-Mail-Adresse des Absenders sein: ");
                }
                String sender = appInput.nextLine();


                if (!isGermanSet) {
                    System.out.print("The third one must be Password: ");
                } else {
                    System.out.print("Das dritte muss Passwort sein: ");
                }
                String password = appInput.nextLine();


                if (!isGermanSet) {
                    System.out.print("The fourth one must be Receiver email address: ");
                } else {
                    System.out.print("Der vierte muss die E-Mail-Adresse des Empfängers sein: ");
                }
                String receiver = appInput.nextLine();


                try {
                    TemperatureConfigurator temperatureConfigurator = TemperatureConfigurator.builder().build();
                    WindSpeedConfigurator windSpeedConfigurator = WindSpeedConfigurator.builder().build();
                    HumidityConfigurator humidityConfigurator = HumidityConfigurator.builder().build();
                    PrecipitationConfigurator precipitationConfigurator = PrecipitationConfigurator.builder().build();
                    LightningsConfigurator lightningsConfigurator = LightningsConfigurator.builder().build();
                    CloudsConfigurator cloudsConfigurator = CloudsConfigurator.builder()
                        .setCloud(Cloud.CUMULUS)
                        .setCloud(Cloud.NIMBUS)
                        .build();

                    CriteriaConfigurator criteriaConfigurator =
                        new CriteriaConfigurator(temperatureConfigurator, windSpeedConfigurator, humidityConfigurator,
                            precipitationConfigurator, lightningsConfigurator, cloudsConfigurator);

                    SpaceMissionAPI spaceMission = new SpaceMission(isGermanSet, filePath, sender, password, receiver,
                        criteriaConfigurator);

                    System.out.println(spaceMission.findPerfectDayForSpaceShuttleLaunch());

                    System.out.println(spaceMission.generateWeatherReport());

//                    System.out.println(spaceMission.sendEmail());

//                    var startTime = System.nanoTime();
//                    var endTime = System.nanoTime();
//                    System.out.println((endTime - startTime));// / 1_000_000_000

                } catch (FileNotFoundException e) {
                    if (!isGermanSet) {
                        System.out.println("There is no such file!");
                        System.out.println("Please enter correct filepath!");
                    } else {
                        System.out.println("Es gibt keine solche Datei!There is no such file!");
                        System.out.println("Bitte geben Sie den korrekten Dateipfad ein!");
                    }

                } catch (Exception e) {
                    if (!isGermanSet) {
                        System.out.println(e.getMessage());
                        System.out.println("Error occured in the app, please restart with \"start\" command");
                    } else {
                        System.out.println("Es gibt keine solche Datei!There is no such file!");
                        System.out.println("Bitte geben Sie den korrekten Dateipfad ein!");
                    }
                }

            } else if ((appCommand.equals("stop") && !isGermanSet) || (isGermanSet && appCommand.equals("stoppen"))) {
                if (!isGermanSet) {
                    System.out.println("Stopping the program!");
                    System.out.println("Thank you for using the program!");
                } else {
                    System.out.println("Stoppen Sie das Programm!");
                    System.out.println("Vielen Dank, dass Sie das Programm nutzen!");
                }

                return;
            } else if (appCommand.equals("stoppen")) {
                System.out.println("Note that the command \"stop\" only for German is \"stoppen\"!");
            } else if (appCommand.equals("stop")) {
                System.out.println("Beachten Sie, dass der Befehl \"stop\" für Deutsch \"stoppen\" lautet!");
            } else if (appCommand.equals("EN")) {
                isGermanSet = false;
            } else if (appCommand.equals("GE")) {
                isGermanSet = true;
            } else {
                if (!isGermanSet) {
                    System.out.println("The supported commands are \"start\", \"stop\", \"EN\" and \"GE\"!");
                } else {
                    System.out.println("Die unterstützten Befehle sind \"start\", \"stoppen\", \"EN\" und \"GE\"!");
                }
            }
        }
    }
}