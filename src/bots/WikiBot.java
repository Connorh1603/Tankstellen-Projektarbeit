package bots;

import Interfaces.IBot;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WikiBot implements IBot{
    @Override
    public String getName() {
        return "WikiBot";
    }

    @Override
    public boolean processCommand(String command) {
        if (command.equalsIgnoreCase("Bielefeld")) {
            System.out.println("Bielefeld ist schön");
            return true;
        } else {
            return false;
        }
    }


private String fetchWikiSummary(String searchTerm) {
    try {
        String apiUrl = "https://de.wikipedia.org/w/rest.php/v1/search/page?q=" + searchTerm + "&limit=3";
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            output.append(line);
        }

        conn.disconnect();

        // Parse JSON response using org.json
        JSONObject rootNode = new JSONObject(output.toString());
        JSONArray pages = rootNode.getJSONArray("pages");

        if (pages != null) {
            StringBuilder summary = new StringBuilder();
            for (int i = 0; i < pages.length(); i++) {
                JSONObject page = pages.getJSONObject(i);
                String title = page.getString("title");
                String description = page.getString("description");
                summary.append(" - ").append(title).append(": ").append(description).append("\n");
            }
            return summary.toString();
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
}