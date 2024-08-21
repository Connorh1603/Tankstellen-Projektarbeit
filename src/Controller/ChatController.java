package Controller;

import model.Message;
import model.BotManager;
import Interfaces.IBot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChatController {
    private BotManager botManager;
    private List<Message> messageHistory;

    public ChatController() {
        this.botManager = new BotManager();
        this.messageHistory = new ArrayList<>();
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
        System.out.println("Available bots:");
        for (Map.Entry<Integer, IBot> entry : availableBots.entrySet()) {
            System.out.println(entry.getKey() + ") " + entry.getValue().getName());
        }
    }

    public void processInput(String input, String user) {
        // Speichere die Benutzereingabe als Nachricht
        Message userMessage = new Message(user, input);
        messageHistory.add(userMessage);

        Map<Integer, IBot> activeBots = botManager.getActiveBots();
        if (activeBots.isEmpty()) {
            System.out.println("No bot is currently activated. Please activate a bot first.");
            return;
        }

        boolean commandProcessed = false;

        for (IBot bot : activeBots.values()) {
            String output=bot.processCommand(input);
            if (output != null) {
                commandProcessed = true;
                // Speichere die Bot-Antwort als Nachricht
                Message botMessage = new Message(bot.getName(),output);
                messageHistory.add(botMessage);
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
}
