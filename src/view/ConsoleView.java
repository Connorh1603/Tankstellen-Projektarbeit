package view;

import java.util.Scanner;

import Controller.ChatController;

public class ConsoleView {
    private ChatController controller;

    public ConsoleView(ChatController controller) {
        this.controller = controller;
    }

    public void start() {
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
