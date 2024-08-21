package Services;

import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class TranslationService {
    private static final String API_KEY = "21947c34-929d-ad82-932b-5747f7ba2f31:fx"; // Der API-Schlüssel für DeepL
    private static final String TRANSLATE_URL = "https://api-free.deepl.com/v2/translate";

    private static TranslationService instance;

    private TranslationService() {}

    public static synchronized TranslationService getInstance() {
        if (instance == null) {
            instance = new TranslationService();
        }
        return instance;
    }

    /**
     * Führt die Übersetzung durch, indem es eine Anfrage an die DeepL API sendet.
     *
     * @param text Der zu übersetzende Text.
     * @param targetLang Der Ziel-Sprachcode (z.B. "EN" oder "DE").
     * @return Der übersetzte Text.
     * @throws Exception Bei Fehlern während der Übersetzung.
     */
    public String translate(String text, String targetLang) throws Exception {
        URL url = new URL(TRANSLATE_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "DeepL-Auth-Key " + API_KEY);
        conn.setDoOutput(true);

        // Daten für die POST-Anfrage vorbereiten
        String postData = "text=" + URLEncoder.encode(text, "UTF-8") +
                "&target_lang=" + URLEncoder.encode(targetLang, "UTF-8");

        // POST-Daten senden
        try (OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream())) {
            writer.write(postData);
            writer.flush();
        }

        // Antwort von der API lesen
        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        }

        // Antwort parsen, um den übersetzten Text zu extrahieren
        String result = response.toString();
        int start = result.indexOf("\"text\":\"") + 8;
        int end = result.indexOf("\"}", start);
        if (start > 8 && end > start) {
            return result.substring(start, end).replace("\\", ""); // Entfernen von Escape-Zeichen
        } else {
            throw new Exception("Invalid API response format.");
        }
    }
}