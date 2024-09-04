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

    public ChatController(IDatabase db) {
        this.botManager = new BotManager();
        this.messageHistory = new ArrayList<>();
        this.db = db;
        this.botRegistry = new BotRegistryService(botManager); // Initialisierung des BotRegistry
    }

    public void initializeBots() {
        botRegistry.registerBots(); // Registrierung der Bots über BotRegistry
    }

    public void listAvailableBots() {
        botRegistry.listAvailableBots(); // Aufruf der BotRegistry Methode
    }

    public void activateBot(int botId) {
        botRegistry.activateBot(botId); // Aufruf der BotRegistry Methode
    }

    public void deactivateBot(int botId) {
        botRegistry.deactivateBot(botId); // Aufruf der BotRegistry Methode
    }
    
    public void processInput(String input, String user) {
        // Speichere die Benutzereingabe als Nachricht
        Message userMessage = new Message(user, input, java.time.LocalDateTime.now());
        int userMessageId = db.saveMessage(userMessage, null);
        userMessage.setId(userMessageId);

        Map<Integer, IBot> activeBots = botManager.getActiveBots();
        if (activeBots.isEmpty()) {
            System.out.println("No bot is currently activated. Please activate a bot first.");
            return;
        }

        for (IBot bot : activeBots.values()) {
            String output = bot.processCommand(input);
            if (output != null && !output.trim().isEmpty()) {
                // Speichere die Bot-Antwort als Nachricht
                Message botMessage = new Message(bot.getName(), output, java.time.LocalDateTime.now());
                int botMessageId = db.saveMessage(botMessage, userMessage.getId());
                botMessage.setId(botMessageId);
                messageHistory.add(botMessage);

                // Sofortige Ausgabe der Benutzeranfrage und der Bot-Antwort
                System.out.println(userMessage.getTimestamp() + " [" + userMessage.getSender() + "]: " + userMessage.getContent());
                System.out.println(botMessage.getTimestamp() + " [" + botMessage.getSender() + "]: " + botMessage.getContent());
            }
        }
    }

    public List<Message> getMessageHistory() {
        return messageHistory;
    }

    // Hier ist die displayMessageHistory Methode
    public void displayMessageHistory(List<Message> messages) {
        if (messages.isEmpty()) {
            System.out.println("Keine Nachrichten im Chatverlauf.");
            return;
        }

        // Sortiere die Nachrichten nach Zeitstempel, damit Benutzer-Nachricht und Bot-Antwort zusammen erscheinen
        messages.sort(Comparator.comparing(Message::getTimestamp));

        System.out.println("Chatverlauf:");
        for (Message message : messages) {
            System.out.println(message.getTimestamp() + " [" + message.getSender() + "]: " + message.getContent());
        }
    }
}