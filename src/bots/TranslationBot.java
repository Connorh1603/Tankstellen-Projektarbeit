package bots;

import Interfaces.IBot;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class TranslationBot implements IBot {
    private final String name = "TranslationBot";
    private final String apiKey = "21947c34-929d-ad82-932b-5747f7ba2f31:fx";  // Der API-Schlüssel von DeepL

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean processCommand(String command) {
        if (command.startsWith("@translatebot")) {
            try {
                String[] parts = command.split(" ", 3);
                if (parts.length < 3) {
                    System.out.println("Usage: @translatebot <target_language_code> <text>");
                    return false;
                }

                String targetLang = parts[1].toUpperCase();
                String textToTranslate = parts[2];

                String translatedText = translate(textToTranslate, targetLang);
                System.out.println("Translation: " + translatedText);
                return true;

            } catch (Exception e) {
                System.out.println("Error processing command: " + e.getMessage());
                return false;
            }
        }
        return false;
    }

    private String translate(String text, String targetLang) throws Exception {
        String urlStr = "https://api-free.deepl.com/v2/translate";
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "DeepL-Auth-Key " + apiKey);
        conn.setDoOutput(true);

        String postData = "text=" + URLEncoder.encode(text, "UTF-8") +
                "&target_lang=" + URLEncoder.encode(targetLang, "UTF-8");

        OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
        writer.write(postData);
        writer.flush();

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        // Parsing der JSON-Antwort, um den übersetzten Text zu extrahieren
        String result = response.toString();
        int start = result.indexOf("\"text\":\"") + 8;
        int end = result.indexOf("\"}]", start);
        return result.substring(start, end).replace("\\", "");
    }
}