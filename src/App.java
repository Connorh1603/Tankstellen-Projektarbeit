

import Controller.ChatController;
import bots.TranslationBot;
import bots.WeatherBot;
import bots.WikiBot;
import view.ConsoleView;
import view.FrontendAdapter;

public class App {
    public static void main(String[] args) throws Exception {
        // Initialisierung des Controllers
        ChatController controller = new ChatController();

        // Registrierung der verfügbaren Bots
        controller.registerBot(1, new WeatherBot());

        // Verwenden des Adapters (aktuell für die Konsole)
        FrontendAdapter adapter = new FrontendAdapter(new ConsoleView());
        adapter.start(controller);
    }
}
