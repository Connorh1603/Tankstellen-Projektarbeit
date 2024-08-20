package model;

import java.util.HashMap;
import java.util.Map;

import Interfaces.IBot;

public class BotManager {
    private Map<Integer, IBot> availableBots = new HashMap<>();
    private Map<Integer, IBot> activeBots = new HashMap<>();

    public void registerBot(int id, IBot bot) {
        availableBots.put(id, bot);
    }

    public void activateBot(int id) {
        if (availableBots.containsKey(id)) {
            activeBots.put(id, availableBots.get(id));
        }
    }

    public void deactivateBot(int id) {
        activeBots.remove(id);
    }

    public IBot getBot(int id) {
        return activeBots.get(id);
    }

    public Map<Integer, IBot> getActiveBots() {
        return activeBots;
    }

    public Map<Integer, IBot> getAvailableBots() {
        return availableBots;
    }
}
