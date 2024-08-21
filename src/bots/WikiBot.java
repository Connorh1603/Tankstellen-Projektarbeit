package bots;

import Interfaces.IBot;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WikiBot implements IBot {

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

        // Kodieren des Suchbegriffs für die URL
        String encodedSearchTerm;
        try {
            encodedSearchTerm = URLEncoder.encode(searchTerm, StandardCharsets.UTF_8.toString());
        } catch (Exception e) {
            return "Fehler bei der Kodierung des Suchbegriffs.";
        }

        // Holen der Zusammenfassung von Wikipedia
        String result = fetchWikiSummary(encodedSearchTerm);
        if (result != null && !result.isEmpty()) {
            return "Folgende Information habe ich zu " + searchTerm + ":\n" + result;
        } else {
            return "Keine Informationen gefunden zu " + searchTerm + ".";
        }
    }

    private String fetchWikiSummary(String searchTerm) {
        try {
            String apiUrl = buildWikiApiUrl(searchTerm);
            HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                return null; // Kein Inhalt bei Fehler
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                StringBuilder output = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    output.append(line);
                }
                return parseWikiResponse(output.toString());
            } finally {
                conn.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Kein Inhalt bei Fehler
        }
    }

    private String buildWikiApiUrl(String searchTerm) {
        return "https://de.wikipedia.org/w/rest.php/v1/search/page?q=" + searchTerm + "&limit=3";
    }

    private String parseWikiResponse(String jsonResponse) {
        JSONObject rootNode = new JSONObject(jsonResponse);
        JSONArray pages = rootNode.optJSONArray("pages");

        if (pages == null || pages.length() == 0) {
            return null; // Keine Ergebnisse gefunden
        }

        StringBuilder summary = new StringBuilder();
        for (int i = 0; i < pages.length(); i++) {
            JSONObject page = pages.getJSONObject(i);
            String title = page.optString("title", "Kein Titel verfügbar");
            String description = page.optString("description", "Keine Beschreibung verfügbar");
            summary.append(" - ").append(title).append(": ").append(description).append("\n");
        }
        return summary.toString();
    }
}