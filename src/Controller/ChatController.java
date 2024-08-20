package Controller;

import java.util.Map;

import Interfaces.IBot;
import model.BotManager;

public class ChatController {
    private BotManager botManager;

    public ChatController() {
        this.botManager = new BotManager();
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

    public void processInput(String input) {
        Map<Integer, IBot> activeBots = botManager.getActiveBots();
        if (activeBots.isEmpty()) {
            System.out.println("No bot is currently activated. Please activate a bot first.");
            return;
        }

        boolean commandProcessed = false;

        for (IBot bot : activeBots.values()) {
            if (bot.processCommand(input)) {
                commandProcessed = true;
            }
        }

        if (!commandProcessed) {
            System.out.println("No bot recognized the command.");
        }
    }
}
