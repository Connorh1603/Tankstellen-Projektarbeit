

import Controller.ChatController;
import bots.WeatherBot;
import view.ConsoleView;

public class App {
    public static void main(String[] args) throws Exception {
        ChatController controller = new ChatController();
        
        // Registrierung der verfügbaren Bots
        controller.registerBot(1, new WeatherBot());

        
        // Starten der Konsolenschnittstelle
        ConsoleView consoleView = new ConsoleView(controller);
        consoleView.start();
    }
}
