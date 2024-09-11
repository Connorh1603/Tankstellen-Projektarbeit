package services;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WikiService {

    // Diese Methode ruft eine Zusammenfassung von Wikipedia für den gegebenen Suchbegriff ab.
    public String fetchWikiSummary(String searchTerm) {
        try {
            // Kodieren des Suchbegriffs, um ihn in der URL zu verwenden (z.B. für Sonderzeichen)
            String encodedSearchTerm = URLEncoder.encode(searchTerm, StandardCharsets.UTF_8.toString());
            String apiUrl = buildWikiApiUrl(encodedSearchTerm); //
            HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection(); 
            conn.setRequestMethod("GET"); // HTTP-Methode GET verwenden
            conn.setRequestProperty("Accept", "application/json"); 

            // Wenn der HTTP-Statuscode nicht 200 (OK) ist, wird null zurückgegeben
            if (conn.getResponseCode() != 200) {
                return null; // Kein Inhalt bei Fehler
            }

            // Lesen der API-Antwort
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                StringBuilder output = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    output.append(line);
                }
                // Parsen der API-Antwort und Rückgabe der Zusammenfassung
                return parseWikiResponse(output.toString());
            } finally {
                conn.disconnect(); 
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Kein Inhalt bei Fehler
        }
    }

    // Erstellen der URL für die Wikipedia-API basierend auf dem Suchbegriff
    private String buildWikiApiUrl(String searchTerm) {
        return "https://de.wikipedia.org/w/rest.php/v1/search/page?q=" + searchTerm + "&limit=3"; // API-URL mit Limit auf 3 Ergebnisse
    }

    // Parsen der JSON-Antwort von Wikipedia, um die Zusammenfassung zu extrahieren
    private String parseWikiResponse(String jsonResponse) {
        JSONObject rootNode = new JSONObject(jsonResponse); 
        JSONArray pages = rootNode.optJSONArray("pages"); 

        // Wenn keine Seiten gefunden wurden, wird null zurückgegeben
        if (pages == null || pages.length() == 0) {
            return null; 
        }

        // Zusammenstellen der Zusammenfassung basierend auf den gefundenen Seiten
        StringBuilder summary = new StringBuilder();
        for (int i = 0; i < pages.length(); i++) {
            JSONObject page = pages.getJSONObject(i); 
            String title = page.optString("title", "Kein Titel verfügbar"); 
            String description = page.optString("description", "Keine Beschreibung verfügbar"); 
            summary.append(" - ").append(title).append(": ").append(description).append("\n"); 
        }
        return summary.toString(); // Rückgabe der finalen Zusammenfassung
    }
}
