package Controller;

import model.Message;
import model.BotManager;
import Interfaces.IBot;
import Interfaces.IDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChatController {
    private BotManager botManager;
    private List<Message> messageHistory;
    private IDatabase database;

    // Konstruktor, der die Datenbankinstanz akzeptiert
    public ChatController(IDatabase database) {
        this.botManager = new BotManager();
        this.messageHistory = new ArrayList<>();
        this.database = database;
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
        Message userMessage = new Message(user, input);
        messageHistory.add(userMessage);
        database.saveMessage(userMessage); // Nachricht in der Datenbank speichern

        Map<Integer, IBot> activeBots = botManager.getActiveBots();
        if (activeBots.isEmpty()) {
            System.out.println("No bot is currently activated. Please activate a bot first.");
            return;
        }

        boolean commandProcessed = false;

        for (IBot bot : activeBots.values()) {
            String output = bot.processCommand(input);
            if (output != null) {
                commandProcessed = true;
                // Speichere die Bot-Antwort als Nachricht
                Message botMessage = new Message(bot.getName(), output);
                messageHistory.add(botMessage);
                database.saveMessage(botMessage); // Bot-Antwort in der Datenbank speichern
                System.out.println(output);
            }
        }

        if (!commandProcessed) {
            System.out.println("No bot recognized the command.");
        }
    }

    public List<Message> getMessageHistory() {
        return messageHistory;
    }

    // Methode zum Schließen der Datenbankverbindung
    public void close() {
        database.close();
    }
}