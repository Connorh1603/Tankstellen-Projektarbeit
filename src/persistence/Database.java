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

    public int saveMessage(Message message, Integer relatedMessageId) {
        String json = "{ \"sender\": \"" + message.getSender() + "\", " +
                "\"content\": \"" + escapeJson(message.getContent()) + "\", " +
                "\"timestamp\": \"" + message.getTimestamp() + "\", " +
                "\"related_message_id\": " + (relatedMessageId != null ? relatedMessageId : "null") + "}";

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
                return -1;
            } else {
                // Hier wird die ID der gespeicherten Nachricht aus der Antwort extrahiert
                String responseBody = response.body().string();
                JSONArray jsonArray = new JSONArray(responseBody);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                int id = jsonObject.getInt("id");
                return id; // Rückgabe der ID der gespeicherten Nachricht
            }
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
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

        try {
            // 1. Nachrichten des Benutzers laden
            String userMessagesUrl = SUPABASE_URL + "/rest/v1/messages?sender=eq." + username + "&limit=" + limit + "&order=timestamp.asc";
            Request requestUserMessages = new Request.Builder()
                    .url(userMessagesUrl)
                    .get()
                    .addHeader("apikey", API_KEY)
                    .addHeader("Authorization", "Bearer " + API_KEY)
                    .build();

            try (Response responseUserMessages = client.newCall(requestUserMessages).execute()) {
                if (!responseUserMessages.isSuccessful()) {
                    throw new IOException("Unexpected code " + responseUserMessages);
                }

                String jsonResponseUserMessages = responseUserMessages.body().string();
                JSONArray jsonArrayUserMessages = new JSONArray(jsonResponseUserMessages);

                // Standard ISO-8601-Format verwenden
                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

                for (int i = 0; i < jsonArrayUserMessages.length(); i++) {
                    JSONObject jsonObject = jsonArrayUserMessages.getJSONObject(i);
                    int id = jsonObject.getInt("id");
                    String sender = jsonObject.getString("sender");
                    String content = jsonObject.getString("content");
                    String timestampStr = jsonObject.getString("timestamp");

                    LocalDateTime timestamp = LocalDateTime.parse(timestampStr, formatter);

                    Message message = new Message(id, sender, content, timestamp);
                    messages.add(message);
                }

                // Wenn es keine Nachrichten gibt, gibt es keine IDs für die nachfolgende Abfrage
                if (jsonArrayUserMessages.length() == 0) {
                    return messages; // Keine Nachrichten, keine Bot-Antworten
                }

                // 2. Bot-Antworten laden, die auf die Nachrichten des Benutzers antworten
                String messageIdsCsv = getMessageIdsAsCsv(jsonArrayUserMessages);
                String botRepliesUrl = SUPABASE_URL + "/rest/v1/messages?related_message_id=in.(" + messageIdsCsv + ")&limit=" + limit + "&order=timestamp.asc";
                Request requestBotReplies = new Request.Builder()
                        .url(botRepliesUrl)
                        .get()
                        .addHeader("apikey", API_KEY)
                        .addHeader("Authorization", "Bearer " + API_KEY)
                        .build();

                try (Response responseBotReplies = client.newCall(requestBotReplies).execute()) {
                    if (!responseBotReplies.isSuccessful()) {
                        throw new IOException("Unexpected code " + responseBotReplies);
                    }

                    String jsonResponseBotReplies = responseBotReplies.body().string();
                    JSONArray jsonArrayBotReplies = new JSONArray(jsonResponseBotReplies);

                    for (int i = 0; i < jsonArrayBotReplies.length(); i++) {
                        JSONObject jsonObject = jsonArrayBotReplies.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String sender = jsonObject.getString("sender");
                        String content = jsonObject.getString("content");
                        String timestampStr = jsonObject.getString("timestamp");

                        LocalDateTime timestamp = LocalDateTime.parse(timestampStr, formatter);

                        Message message = new Message(id, sender, content, timestamp);
                        messages.add(message);
                    }

                }
            }

            // Die Nachrichten nach dem Timestamp sortieren
            messages.sort((m1, m2) -> m1.getTimestamp().compareTo(m2.getTimestamp()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return messages;
    }

    private String getMessageIdsAsCsv(JSONArray jsonArray) {
        StringBuilder ids = new StringBuilder();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (i > 0) {
                ids.append(",");
            }
            ids.append(jsonObject.getInt("id"));
        }
        return ids.toString();
    }

    @Override
    public void close() {
        // Keine spezifische Close-Operation für OkHttp erforderlich, da es sich um einen globalen Client handelt
    }
}