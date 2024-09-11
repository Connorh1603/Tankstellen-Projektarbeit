import Controller.ChatController;
import Interfaces.IDatabase;
import model.User;
import persistence.DatabaseAdapter;
import persistence.Database;
import view.ConsoleView;
import view.FrontendAdapter;
import Interfaces.IFrontend;

import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        // Initialisierung der Database
        IDatabase db = new DatabaseAdapter(new Database());

        // Benutzer anmelden
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in); // Scanner zur Benutzereingabe
        User currentUser = null;

        // Schleife für die Benutzeranmeldung, bis gültige Anmeldeinformationen eingegeben werden
        while (currentUser == null) {
            System.out.print("Benutzername: ");
            String username = scanner.nextLine();
            System.out.print("Passwort: ");
            String password = scanner.nextLine();

            // Authentifizierung des Benutzers über die Datenbank
            currentUser = db.authenticateUser(username, password);
            if (currentUser == null) {
                System.out.println("Ungültiger Benutzername oder Passwort.");
            }
        }

        // Begrüßungsnachricht für den authentifizierten Benutzer
        System.out.println("Willkommen " + currentUser.getUsername() + "!");

        // Initialisierung des ChatControllers mit dem DatabaseManager
        ChatController controller = new ChatController(db);

        // Initialisierung und Registrierung der Bots im Controller
        controller.initializeBots();

        // Chatverlauf des Benutzers laden und anzeigen (maximal 100 Nachrichten)
        controller.displayMessageHistory(db.loadMessages(currentUser.getUsername(), 100));

        // Initialisierung der Benutzeroberfläche mit dem FrontendAdapter (aktuell für die Konsole)
        IFrontend frontend = new FrontendAdapter(new ConsoleView());
        frontend.start(controller, currentUser.getUsername()); // Start der Chatinteraktion
    }
}
