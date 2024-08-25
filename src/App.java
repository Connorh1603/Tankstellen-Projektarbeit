

import Controller.ChatController;
import bots.TranslationBot;
import bots.WeatherBot;
import bots.WikiBot;
import persistence.Database;
import view.ConsoleView;
import view.FrontendAdapter;
import Interfaces.*;

public class App {
    public static void main(String[] args) throws Exception {
        // Initialisierung der Datenbank
        IDatabase database = new Database();

        // Initialisierung des Controllers
        ChatController controller = new ChatController();

        // Registrierung der verfügbaren Bots
        controller.registerBot(1, new WeatherBot());
        controller.registerBot(2, new WikiBot());
        controller.registerBot(3, new TranslationBot());
        // Benutzername festlegen
        String user = "User123";

        // Verwenden des FrontendAdapters (aktuell für die Konsole)
        IFrontend frontend = new FrontendAdapter(new ConsoleView());
        frontend.start(controller, user);
    }
}
