package view;
import java.util.List;
import java.util.Scanner;

import Controller.ChatController;
import model.Message;

public class ConsoleView {

    public void display(String message) {
        System.out.println(message);
    }

    public String readInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public void run(ChatController controller, String user) {
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
            } else if (command.equals("show history")) {
                // Neue Methode zum Anzeigen des Chatverlaufs
                displayMessageHistory(controller.getMessageHistory());
            } else {
                controller.processInput(command, user);
            }
        }
    }

    public void displayMessageHistory(List<Message> messageHistory) {
        System.out.println("Chat History:");
        for (Message message : messageHistory) {
            System.out.println(message.getTimestamp() + " [" + message.getSender() + "]: " + message.getContent());
        }
    }
}
