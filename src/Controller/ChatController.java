package Controller;

import model.Message;
import model.BotManager;
import Interfaces.IBot;
import model.DatabaseManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ChatController {
    private BotManager botManager;
    private List<Message> messageHistory;
    private DatabaseManager dbManager;

    // Konstruktor, der den DatabaseManager akzeptiert
    public ChatController(DatabaseManager dbManager) {
        this.botManager = new BotManager();
        this.messageHistory = new ArrayList<>();
        this.dbManager = dbManager;
    }

    public void registerBot(int id, IBot bot) {
        botManager.registerBot(id, bot);
    }

    public void activateBot(int id) {
        botManager.activateBot(id);
        System.out.println("Bot " + botManager.getBot(id).getName() + " activated.");
    }

    public void deactivateBot(int id) {
        IBot bot = botManager.getBot(id);
        botManager.deactivateBot(id);
        System.out.println("Bot " + bot.getName() + " deactivated.");
    }

    public void listAvailableBots() {
        Map<Integer, IBot> availableBots = botManager.getAvailableBots();
        Map<Integer, IBot> activeBots = botManager.getActiveBots();
        System.out.println("Available bots:");
        for (Map.Entry<Integer, IBot> entry : availableBots.entrySet()) {
            String botStatus = activeBots.containsKey(entry.getKey()) ? "enabled" : "available";
            System.out.println("    " + entry.getKey() + ") " + entry.getValue().getName() + " (" + botStatus + ")");
        }
    }

    public void processInput(String input, String user) {
        // Speichere die Benutzereingabe als Nachricht
        Message userMessage = new Message(user, input, java.time.LocalDateTime.now());
        int userMessageId = dbManager.saveMessage(userMessage, null);
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
                int botMessageId = dbManager.saveMessage(botMessage, userMessage.getId());
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