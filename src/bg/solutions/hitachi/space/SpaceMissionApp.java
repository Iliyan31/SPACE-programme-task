package bg.solutions.hitachi.space;

import java.util.Scanner;

public class SpaceMissionApp {
    public static void main(String[] args) {
        System.out.println("Hello to the space program!");

        System.out.println(
            "To proceed using the app you need to enter \"start\" text in the field " +
                "and to stop it you need to enter \"stop\"!");


        Scanner appInput = new Scanner(System.in);
        String appCommand;
        while (true) {
            System.out.print("Please input command: ");
            appCommand = appInput.nextLine();

            if (appCommand.equals("start")) {
                System.out.println("To proceed you need to enter 4 must parameters as follows:");
                System.out.print("The first one must be path to file: ");
                String filePath = appInput.nextLine();
                //validation here

                System.out.print("The second one must be Sender email address: ");
                String sender = appInput.nextLine();
                //validation here

                System.out.print("The second one must be Password: ");
                String password = appInput.nextLine();
                //validation here

                System.out.print("The second one must be Receiver email address: ");
                String receiver = appInput.nextLine();
                //validation here


            } else if (appCommand.equals("stop")) {
                return;
            } else {
                System.out.println("The supported commands are \"start\" and \"stop\"!");
            }
        }
    }
}
