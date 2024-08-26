package persistence;

import Interfaces.IDatabase;
import model.Message;
import model.User;
import okhttp3.*; // Importiert OkHttp Klassen
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Database implements IDatabase {
    private static final String SUPABASE_URL = "https://uhogndirdosqnnbywozi.supabase.co";
    private static final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InVob2duZGlyZG9zcW5uYnl3b3ppIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTcyNDYwMDIwMywiZXhwIjoyMDQwMTc2MjAzfQ.rE9ykKiHwapQynE89CuhXdHT7xEpDOWY7YiRArHlUII";
    private final OkHttpClient client;

    public Database() {
        this.client = new OkHttpClient();
    }

    @Override
    public User authenticateUser(String username, String password) {
        // Authentifizierungsmethode - Beispielhaft hartkodiert
        if (username.equals("user1") && password.equals("password1")) {
            return new User(username, password);
        }
        if (username.equals("user2") && password.equals("password2")) {
            return new User(username, password);
        }
        return null; // Authentifizierung fehlgeschlagen
    }

    @Override
    public void saveMessage(Message message) {
        if (message.getSender() == null || message.getContent() == null || message.getTimestamp() == null) {
            System.out.println("Invalid message, not saving to database.");
            return;
        }

        String json = "{ \"sender\": \"" + escapeJson(message.getSender()) + "\", " +
                "\"content\": \"" + escapeJson(message.getContent()) + "\", " +
                "\"timestamp\": \"" + message.getTimestamp() + "\"}";

        RequestBody body = RequestBody.create(
                json,
                MediaType.get("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(SUPABASE_URL + "/rest/v1/messages")
                .post(body)
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .addHeader("Prefer", "return=representation")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.out.println("Request failed: " + response.code());
                System.out.println("Response: " + response.body().string());
            } else {
                System.out.println("Request succeeded: " + response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String escapeJson(String text) {
        return text.replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }

    @Override
    public List<Message> loadMessages(String username, int limit) {
        List<Message> messages = new ArrayList<>();
        Request request = new Request.Builder()
                .url(SUPABASE_URL + "/rest/v1/messages?sender=eq." + username + "&limit=" + limit + "&order=timestamp.desc")
                .get()
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String jsonResponse = response.body().string();
            JSONArray jsonArray = new JSONArray(jsonResponse);

            // Standard ISO-8601-Format verwenden
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String sender = jsonObject.getString("sender");
                String content = jsonObject.getString("content");
                String timestampStr = jsonObject.getString("timestamp");

                LocalDateTime timestamp = null;
                try {
                    timestamp = LocalDateTime.parse(timestampStr, formatter);
                } catch (DateTimeParseException e) {
                    e.printStackTrace(); // Fehlerbehandlung bei Parsing-Problemen
                }

                Message message = new Message(sender, content, timestamp);
                messages.add(message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return messages;
    }

    @Override
    public void close() {
        // Keine spezifische Close-Operation für OkHttp erforderlich, da es sich um einen globalen Client handelt
    }
}