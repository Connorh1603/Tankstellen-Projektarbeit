package bots;

import Interfaces.IBot;
import Services.WikiService;

public class WikiBot implements IBot {

    private final WikiService wikiService = new WikiService();

    @Override
    public String getName() {
        return "WikiBot";
    }

    @Override
    public String processCommand(String command) {
        if (command == null || !command.toLowerCase().contains("@wiki")) {
            return "Ungültiger Befehl. Bitte verwenden Sie das Format '@wiki [Suchbegriff]'.";
        }

        // Extrahiere den Suchbegriff nach "@wiki"
        String searchTerm = command.substring(command.toLowerCase().indexOf("@wiki") + "@wiki".length()).trim();

        // Holen der Zusammenfassung von Wikipedia
        String result = wikiService.fetchWikiSummary(searchTerm);
        if (result != null && !result.isEmpty()) {
            return "Folgende Information habe ich zu " + searchTerm + ":\n" + result;
        } else {
            return "Keine Informationen gefunden zu " + searchTerm + ".";
        }
    }
}
