import Controller.ChatController;
import model.DatabaseManager;
import model.User;
import persistence.SupabaseDatabase;
import view.ConsoleView;
import view.FrontendAdapter;
import Interfaces.IFrontend;

import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        // Initialisierung des DatabaseManagers
        DatabaseManager dbManager = new DatabaseManager();

        // Registrierung der Datenbank
        dbManager.registerDatabase(new SupabaseDatabase());

        // Benutzer anmelden
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        User currentUser = null;

        while (currentUser == null) {
            System.out.print("Benutzername: ");
            String username = scanner.nextLine();
            System.out.print("Passwort: ");
            String password = scanner.nextLine();

            currentUser = dbManager.authenticateUser(username, password);
            if (currentUser == null) {
                System.out.println("Ungültiger Benutzername oder Passwort.");
            }
        }

        System.out.println("Willkommen " + currentUser.getUsername() + "!");

        // Initialisierung des Controllers mit dem DatabaseManager
        ChatController controller = new ChatController(dbManager);

        // Initialisierung und Registrierung der Bots
        controller.initializeBots();

        // Chatverlauf laden und anzeigen
        controller.displayMessageHistory(dbManager.loadMessages(currentUser.getUsername(), 100));

        // Verwenden des FrontendAdapters (aktuell für die Konsole)
        IFrontend frontend = new FrontendAdapter(new ConsoleView());
        frontend.start(controller, currentUser.getUsername());
    }
}