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
    private boolean isActiveConversation = false; // Für Variante B
    private String targetLanguage = "";
    private String pendingTextToTranslate = ""; // Für den neuen translate Befehl

    private final String apiKey = "21947c34-929d-ad82-932b-5747f7ba2f31:fx";  // Der API-Schlüssel von DeepL

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean processCommand(String command) {
        // Variante A: Direktansprache
        if (command.startsWith("@translatebot")) {
            handleDirectTranslation(command.substring(14).trim());
            return true;
        }

        // Neue Bedingung: Reaktion auf "translate {text}"
        if (command.startsWith("translate")) {
            String textToTranslate = command.substring(10).trim();
            initiateTranslationFlow(textToTranslate);
            return true;
        }

        // Variante B: Aktivierung des Bots und schrittweise Interaktion
        if (isActiveConversation || command.equals("@translatebot")) {
            handleInteractiveTranslation(command);
            return true;
        }

        return false;
    }

    private void initiateTranslationFlow(String textToTranslate) {
        if (!isActiveConversation) {
            isActiveConversation = true;
            pendingTextToTranslate = textToTranslate; // Speichern des zu übersetzenden Textes
            System.out.println("In welche Sprache soll ich übersetzen? Bitte zweistelligen Code eingeben:");
            System.out.println("    - [EN]glisch");
            System.out.println("    - [DE]utsch");
            // Weitere Sprachen können hinzugefügt werden
        }
    }

    private void handleDirectTranslation(String input) {
        try {
            String[] parts = input.split(" ", 2);
            if (parts.length < 2) {
                System.out.println("Usage: @translatebot <target_language_code> <text>");
                return;
            }

            String targetLang = parts[0].toUpperCase();
            String textToTranslate = parts[1];

            String translatedText = translate(textToTranslate, targetLang);
            System.out.println("Translation: " + translatedText);

        } catch (Exception e) {
            System.out.println("Error processing command: " + e.getMessage());
        }
    }

    private void handleInteractiveTranslation(String command) {
        if (!isActiveConversation) {
            isActiveConversation = true;
            System.out.println("In welche Sprache soll ich übersetzen? Bitte zweistelligen Code eingeben:");
            System.out.println("    - [EN]glisch");
            System.out.println("    - [DE]utsch");
            return;
        }

        if (targetLanguage.isEmpty()) {
            targetLanguage = command.toUpperCase();
            if (!pendingTextToTranslate.isEmpty()) {
                try {
                    String translatedText = translate(pendingTextToTranslate, targetLanguage);
                    System.out.println("Translation: " + translatedText);
                } catch (Exception e) {
                    System.out.println("Error processing command: " + e.getMessage());
                } finally {
                    isActiveConversation = false;
                    pendingTextToTranslate = "";
                    targetLanguage = "";
                }
            } else {
                System.out.println("Bitte den zu übersetzenden Text eingeben:");
            }
            return;
        }

        if (command.equalsIgnoreCase("quit")) {
            System.out.println("Bye!");
            isActiveConversation = false;
            pendingTextToTranslate = "";
            targetLanguage = "";
            return;
        }

        try {
            String translatedText = translate(command, targetLanguage);
            System.out.println("Translation: " + translatedText);
            isActiveConversation = false;
            pendingTextToTranslate = "";
            targetLanguage = "";

        } catch (Exception e) {
            System.out.println("Error processing command: " + e.getMessage());
        }
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