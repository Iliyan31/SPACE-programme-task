//package bg.solutions.hitachi.space;
//
//import bg.solutions.hitachi.space.commands.CommandExecutor;
//import bg.solutions.hitachi.space.commands.CommandType;
//import bg.solutions.hitachi.space.mission.SpaceMission;
//import bg.solutions.hitachi.space.mission.SpaceMissionAPI;
//
//import java.io.FileNotFoundException;
//import java.util.Scanner;
//
//public class SpaceMissionTestApp {
//    public static void main(String[] args) {
//        System.out.println("Hello to the space program!");
//        System.out.println("To proceed using the app you need to enter \"start\" text in the field " +
//            "and to stop it you need to enter \"stop\"!");
//        System.out.println("If you want to change the language of the app you need to type \"EN\" for English and " +
//            "\"GE\" for German!");
//        System.out.println("Note that the command \"stop\" for German is \"stoppen\"!");
//
//        Scanner appInput = new Scanner(System.in);
//        String appCommand;
//
//        boolean isGermanSet = false;
////        CommandExecutor commandExecutor = null;
//
//        while (true) {
//            if (!isGermanSet) {
//                System.out.print("Please input command: ");
//            } else {
//                System.out.print("Bitte geben Sie den Befehl ein: ");
//            }
//
//            appCommand = appInput.nextLine();
//            CommandType commandType = CommandType.valueOf(appCommand.toUpperCase());
//
//            switch (commandType) {
//                case START -> new RegisterCommand(cmd.arguments(), bookmarkManager).execute();
//                case STOP -> new LoginCommand(cmd.arguments(), clientChannel, bookmarkManager).execute();
//                case STOPPEN -> new LogoutCommand(cmd.arguments(), clientChannel, bookmarkManager).execute();
//                case EN -> new CreateNewGroupCommand(cmd.arguments(), clientChannel, bookmarkManager).execute();
//                case GE -> new AddBookmarkCommand(cmd.arguments(), clientChannel, bookmarkManager).execute();
//                case SEND_EMAIL, E_MAIL_SENDEN -> new RemoveBookmarkCommand(cmd.arguments(), clientChannel, bookmarkManager).execute();
//                case EXPORT_CSV, CSV_EXPORTIEREN -> new SearchBookmarksCommand(cmd.arguments(), clientChannel, bookmarkManager).execute();
//                case FIND_PERFECT_DAY, PERFEKTEN_TAG -> new CleanupCommand(cmd.arguments(), clientChannel, bookmarkManager).execute();
//                default -> "Unknown command";
//            };
//
//            if (appCommand.equals("start")) {
//                if (!isGermanSet) {
//                    System.out.println("To proceed you need to enter 4 must parameters as follows:");
//                    System.out.print("The first one must be path to file: ");
//                } else {
//                    System.out.println("Um fortzufahren, müssen Sie die folgenden vier Parameter eingeben:");
//                    System.out.print("Der erste muss der Pfad zur Datei sein: ");
//                }
//                String filePath = appInput.nextLine();
//
//
//                if (!isGermanSet) {
//                    System.out.print("The second one must be Sender email address: ");
//                } else {
//                    System.out.print("Die zweite muss die E-Mail-Adresse des Absenders sein: ");
//                }
//                String sender = appInput.nextLine();
//
//
//                if (!isGermanSet) {
//                    System.out.print("The third one must be Password: ");
//                } else {
//                    System.out.print("Das dritte muss Passwort sein: ");
//                }
//                String password = appInput.nextLine();
//
//
//                if (!isGermanSet) {
//                    System.out.print("The fourth one must be Receiver email address: ");
//                } else {
//                    System.out.print("Der vierte muss die E-Mail-Adresse des Empfängers sein: ");
//                }
//                String receiver = appInput.nextLine();
//
//
//                try {
//                    SpaceMissionAPI spaceMission = new SpaceMission(isGermanSet, filePath, sender, password, receiver);
//                    commandExecutor = new CommandExecutor(spaceMission);
//
//
//                    System.out.println(spaceMission.findPerfectDayForSpaceShuttleLaunch());
//
////                    spaceMission.generateWeatherReport();
//
//                    //System.out.println(spaceMission.sendEmail());
//
//                    var startTime = System.nanoTime();
//                    var endTime = System.nanoTime();
//                    System.out.println((endTime - startTime));// / 1_000_000_000
//
//                } catch (FileNotFoundException e) {
//                    if (!isGermanSet) {
//                        System.out.println("There is no such file!");
//                        System.out.println("Please enter correct filepath!");
//                    } else {
//                        System.out.println("Es gibt keine solche Datei!There is no such file!");
//                        System.out.println("Bitte geben Sie den korrekten Dateipfad ein!");
//                    }
//
//                } catch (Exception e) {
//                    if (!isGermanSet) {
//                        System.out.println(e.getMessage());
//                        System.out.println("Error occured in the app, please restart with \"start\" command");
//                    } else {
//                        System.out.println("Es gibt keine solche Datei!There is no such file!");
//                        System.out.println("Bitte geben Sie den korrekten Dateipfad ein!");
//                    }
//                }
//
//            } else if ((appCommand.equals("stop") && !isGermanSet) || (isGermanSet && appCommand.equals("stoppen"))) {
//                if (!isGermanSet) {
//                    System.out.println("Stopping the program!");
//                    System.out.println("Thank you for using the program!");
//                } else {
//                    System.out.println("Stoppen Sie das Programm!");
//                    System.out.println("Vielen Dank, dass Sie das Programm nutzen!");
//                }
//
//                return;
//            } else if (appCommand.equals("stoppen")) {
//                System.out.println("Note that the command \"stop\" only for German is \"stoppen\"!");
//            } else if (appCommand.equals("stop")) {
//                System.out.println("Beachten Sie, dass der Befehl \"stop\" für Deutsch \"stoppen\" lautet!");
//            } else if (appCommand.equals("EN")) {
//                isGermanSet = false;
//            } else if (appCommand.equals("GE")) {
//                isGermanSet = true;
//            } else {
//                if (!isGermanSet) {
//                    System.out.println("The supported commands are \"start\", \"stop\", \"EN\" and \"GE\"!");
//                } else {
//                    System.out.println("Die unterstützten Befehle sind \"start\", \"stoppen\", \"EN\" und \"GE\"!");
//                }
//            }
//        }
//    }
//}
