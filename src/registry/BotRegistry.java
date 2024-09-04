package registry;

import java.util.Map;

import Interfaces.IBot;
import model.BotManager;

public class BotRegistry {

    private BotManager botManager;

    public BotRegistry(BotManager botManager) {
        this.botManager = botManager;
    }

    public void registerBots() {
        botManager.registerBot(1, new bots.WeatherBot());
        botManager.registerBot(2, new bots.WikiBot());
        botManager.registerBot(3, new bots.TranslationBot());
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
}
