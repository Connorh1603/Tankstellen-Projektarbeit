package services;

import java.util.Map;

import Interfaces.IBot;
import model.BotManager;

// Die Klasse BotRegistryService dient zur Registrierung  von Bots im System.
public class BotRegistryService {

    private BotManager botManager; 

    // Konstruktor, der den BotManager initialisiert.
    public BotRegistryService(BotManager botManager) {
        this.botManager = botManager;
    }

    // Methode zur Registrierung von Bots. Hier werden drei spezifische Bots (WeatherBot, WikiBot, TranslationBot) registriert.
    public void registerBots() {
        botManager.registerBot(1, new bots.WeatherBot());
        botManager.registerBot(2, new bots.WikiBot());
        botManager.registerBot(3, new bots.TranslationBot());
    }

    // Methode zur Registrierung eines einzelnen Bots
    public void registerBot(int id, IBot bot) {
        botManager.registerBot(id, bot); // Übergibt die Bot-Registrierung an den BotManager.
    }

    // Methode zur Aktivierung eines Bots 
    public void activateBot(int id) {
        botManager.activateBot(id); // Aktiviert den Bot über den BotManager.
        System.out.println("Bot " + botManager.getBot(id).getName() + " activated."); 
    }

    // Methode zur Deaktivierung eines Bots b
    public void deactivateBot(int id) {
        IBot bot = botManager.getBot(id); 
        botManager.deactivateBot(id); 
        System.out.println("Bot " + bot.getName() + " deactivated."); 
    }
    
    // Listet alle verfügbaren Bots und zeigt an, welche aktiv oder nur verfügbar sind.
    public void listAvailableBots() {
        Map<Integer, IBot> availableBots = botManager.getAvailableBots(); 
        Map<Integer, IBot> activeBots = botManager.getActiveBots(); 

        System.out.println("Available bots:");
        // Iteriert durch alle verfügbaren Bots und zeigt deren Status an (aktiv oder verfügbar).
        for (Map.Entry<Integer, IBot> entry : availableBots.entrySet()) {
            String botStatus = activeBots.containsKey(entry.getKey()) ? "enabled" : "available";
            System.out.println("    " + entry.getKey() + ") " + entry.getValue().getName() + " (" + botStatus + ")");
        }
    }
}
