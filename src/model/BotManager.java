package model;

import java.util.HashMap;
import java.util.Map;
import Interfaces.IBot;

// Die Klasse BotManager verwaltet die Bots
public class BotManager {

    // Eine Map, die die verfügbaren Bots speichert, wobei der Schlüssel eine ID und der Wert ein Bot-Objekt (IBot) ist.
    private Map<Integer, IBot> availableBots = new HashMap<>();
    
    // Eine Map, die die aktivierten Bots speichert
    private Map<Integer, IBot> activeBots = new HashMap<>();

    // Registriert einen Bot
    public void registerBot(int id, IBot bot) {
        availableBots.put(id, bot); // Speichert den Bot in der Map der verfügbaren Bots
    }

    // Aktiviert einen Bot anhand seiner ID
    public void activateBot(int id) {
        if (availableBots.containsKey(id)) { // Prüft, ob der Bot verfügbar ist
            activeBots.put(id, availableBots.get(id)); // Aktiviert den Bot
        }
    }

    // Deaktiviert einen Bot anhand seiner ID
    public void deactivateBot(int id) {
        activeBots.remove(id); // Entfernt den Bot aus den aktiven Bots
    }

    // Gibt einen aktiven Bot anhand seiner ID zurück
    public IBot getBot(int id) {
        return activeBots.get(id);
    }

    // Gibt eine Map der aktiven Bots zurück
    public Map<Integer, IBot> getActiveBots() {
        return activeBots;
    }

    // Gibt eine Map der verfügbaren Bots zurück, die registriert sind
    public Map<Integer, IBot> getAvailableBots() {
        return availableBots;
    }
}
