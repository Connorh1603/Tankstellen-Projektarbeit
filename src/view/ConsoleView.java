package view;

import java.util.Scanner;

import Controller.ChatController;

public class ConsoleView {


    public void display(String message) {
        System.out.println(message);
    }

    public String readInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public void run(ChatController controller) {
        while (true) {
            System.out.println("Enter command:");
            String command = readInput();
            if (command.startsWith("activate bot")) {
                int botId = Integer.parseInt(command.split(" ")[2]);
                controller.activateBot(botId);
            } else if (command.startsWith("deactivate bot")) {
                int botId = Integer.parseInt(command.split(" ")[2]);
                controller.deactivateBot(botId);
            } else if (command.equals("list bots")) {
                controller.listAvailableBots();
            } else {
                display("Unknown command.");
            }
        }
    }
}
