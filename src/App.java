import java.util.Scanner;

import Controller.ChatController;
import bots.WeatherBot;

public class App {
    public static void main(String[] args) throws Exception {
            ChatController controller = new ChatController();
        controller.registerBot(1, new WeatherBot());

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter command:");
            String command = scanner.nextLine();
            if (command.startsWith("activate bot")) {
                int botId = Integer.parseInt(command.split(" ")[2]);
                controller.activateBot(botId);
            } else if (command.startsWith("deactivate bot")) {
                int botId = Integer.parseInt(command.split(" ")[2]);
                controller.deactivateBot(botId);
            } else if (command.equals("list bots")) {
                controller.listAvailableBots();
            } else {
                System.out.println("Unknown command.");
            }
        }
    }
}
