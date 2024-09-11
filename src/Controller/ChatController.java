package Controller;

import model.Message;
import services.BotRegistryService;
import model.BotManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import Interfaces.IBot;
import Interfaces.IDatabase;

public class ChatController {

    private BotManager botManager; 
    private List<Message> messageHistory; 
    private IDatabase db; 
    private BotRegistryService botRegistry; 

    // Konstruktor zur Initialisierung des ChatControllers mit einer Datenbank und den erforderlichen Services
    public ChatController(IDatabase db) {
        this.botManager = new BotManager();
        this.messageHistory = new ArrayList<>();
        this.db = db;
        this.botRegistry = new BotRegistryService(botManager); // Initialisierung des BotRegistry
    }

    // Initialisiert und registriert die verfügbaren Bots
    public void initializeBots() {
        botRegistry.registerBots(); 
    }

    // Listet die verfügbaren Bots auf
    public void listAvailableBots() {
        botRegistry.listAvailableBots(); 
    }

    // Aktiviert einen Bot anhand seiner ID
    public void activateBot(int botId) {
        botRegistry.activateBot(botId); 
    }

    // Deaktiviert einen Bot anhand seiner ID
    public void deactivateBot(int botId) {
        botRegistry.deactivateBot(botId); 
    }
    
    // Verarbeitet die Eingabe des Benutzers
    public void processInput(String input, String user) {
        // Speichert die Benutzereingabe als Nachricht
        Message userMessage = new Message(user, input, java.time.LocalDateTime.now());
        int userMessageId = db.saveMessage(userMessage, null); // Speichert die Nachricht in der Datenbank
        userMessage.setId(userMessageId); 

        // Holt die aktuell aktivierten Bots
        Map<Integer, IBot> activeBots = botManager.getActiveBots();
        if (activeBots.isEmpty()) {
            System.out.println("No bot is currently activated. Please activate a bot first.");
            return; // Falls keine Bots aktiv sind, wird eine Meldung ausgegeben
        }

        // Durchläuft die aktivierten Bots und verarbeitet die Benutzereingabe
        for (IBot bot : activeBots.values()) {
            String output = bot.processCommand(input); // Ruft die Antwort des Bots auf den eingegebenen Befehl ab
            if (output != null && !output.trim().isEmpty()) {
                // Speichert die Bot-Antwort als Nachricht
                Message botMessage = new Message(bot.getName(), output, java.time.LocalDateTime.now());
                int botMessageId = db.saveMessage(botMessage, userMessage.getId()); // Speichert die Bot-Nachricht in der DB
                botMessage.setId(botMessageId);
                messageHistory.add(botMessage); // Fügt die Bot-Nachricht dem Nachrichtenverlauf hinzu

                // Gibt die Benutzernachricht und die Bot-Antwort aus
                System.out.println(userMessage.getTimestamp() + " [" + userMessage.getSender() + "]: " + userMessage.getContent());
                System.out.println(botMessage.getTimestamp() + " [" + botMessage.getSender() + "]: " + botMessage.getContent());
            }
        }
    }

    // Gibt den Nachrichtenverlauf zurück
    public List<Message> getMessageHistory() {
        return messageHistory;
    }

    // Zeigt den Nachrichtenverlauf an
    public void displayMessageHistory(List<Message> messages) {
        if (messages.isEmpty()) {
            System.out.println("Keine Nachrichten im Chatverlauf.");
            return;
        }

        // Sortiert die Nachrichten nach Zeitstempel
        messages.sort(Comparator.comparing(Message::getTimestamp));

        System.out.println("Chatverlauf:");
        for (Message message : messages) {
            // Gibt jede Nachricht mit Zeitstempel und Sender aus
            System.out.println(message.getTimestamp() + " [" + message.getSender() + "]: " + message.getContent());
        }
    }
}
