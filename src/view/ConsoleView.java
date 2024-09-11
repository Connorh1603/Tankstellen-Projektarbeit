package view;
import java.util.List;
import java.util.Scanner;

import Controller.ChatController;
import model.Message;

// Die Klasse ConsoleView bietet eine einfache Benutzeroberfläche in der Konsole
public class ConsoleView {

    // Zeigt eine Nachricht in der Konsole an.
    public void display(String message) {
        System.out.println(message);
    }

    // Liest Benutzereingaben aus der Konsole 
    public String readInput() {
        Scanner scanner = new Scanner(System.in); // Scanner zum Lesen der Eingabe initialisieren
        return scanner.nextLine(); 
    }

    // Startet die Konsole und erlaubt dem Benutzer, mit dem Chat-System zu interagieren.
    public void run(ChatController controller, String user) {
        while (true) {
            // Zeigt eine Eingabeaufforderung an
            System.out.println("Enter command:");
            String command = readInput(); 

            // Überprüft, ob der Befehl zur Aktivierung eines Bots dient
            if (command.startsWith("activate bot")) {
                int botId = Integer.parseInt(command.split(" ")[2]); 
                controller.activateBot(botId); // Aktiviert den Bot 

            // Überprüft, ob der Befehl zur Deaktivierung eines Bots dient
            } else if (command.startsWith("deactivate bot")) {
                int botId = Integer.parseInt(command.split(" ")[2]);
                controller.deactivateBot(botId); // Deaktiviert den Bot 

            // Überprüft, ob der Befehl zur Auflistung der verfügbaren Bots dient
            } else if (command.equals("list bots")) {
                controller.listAvailableBots(); // Listet die verfügbaren Bots auf

            // Überprüft, ob der Befehl zum Anzeigen des Chatverlaufs dient
            } else if (command.equals("show history")) {
                // Ruft den Chatverlauf ab und zeigt ihn an
                displayMessageHistory(controller.getMessageHistory());

            // Andernfalls wird der Befehl an den Chatcontroller bzw. die Bots weitergegeben
            } else {
                controller.processInput(command, user); 
            }
        }
    }

    // Zeigt den Nachrichtenverlauf in der Konsole an.
    public void displayMessageHistory(List<Message> messageHistory) {
        System.out.println("Chat History:");
        for (Message message : messageHistory) {
            System.out.println(message.getTimestamp() + " [" + message.getSender() + "]: " + message.getContent());
        }
    }
}
