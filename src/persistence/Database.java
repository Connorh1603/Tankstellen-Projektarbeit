package persistence;

import java.util.ArrayList;
import java.util.List;
import okhttp3.*;
import services.SupabaseService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONObject;

import model.Message;
import model.User;

// Die Klasse Database bietet Funktionen zur Authentifizierung von Benutzern und zum Speichern/Laden von Nachrichten.
public class Database {
    private SupabaseService database; // Verweis auf den Supabase-Dienst zur Interaktion mit der Datenbank

    // Methode, um die Datenbank (Supabase) zu registrieren.
    public void registerDatabase(SupabaseService db) {
        this.database = db;
    }

    // Methode zur Benutzerauthentifizierung.
    public User authenticateUser(String username, String password) {
        if (username.equals("user1") && password.equals("password1")) {
            return new User(username, password);
        }
        if (username.equals("user2") && password.equals("password2")) {
            return new User(username, password);
        }
        return null; // Rückgabe von null bei fehlgeschlagener Authentifizierung
    }

    // Speichert eine Nachricht in der Datenbank und gibt die ID der gespeicherten Nachricht zurück.
    public int saveMessage(Message message, Integer relatedMessageId) {
        // Erstellung des JSON-Objekts für die Nachricht
        String json = "{ \"sender\": \"" + message.getSender() + "\", " +
                "\"content\": \"" + escapeJson(message.getContent()) + "\", " +
                "\"timestamp\": \"" + message.getTimestamp() + "\", " +
                "\"related_message_id\": " + (relatedMessageId != null ? relatedMessageId : "null") + "}";

        // Erstellung des HTTP-Request-Bodys mit JSON-Daten
        RequestBody body = RequestBody.create(
                json,
                MediaType.get("application/json; charset=utf-8")
        );

        // Aufbau des HTTP-Requests zum Speichern der Nachricht in der Datenbank
        Request request = new Request.Builder()
                .url(database.getDatabaseUrl() + "/rest/v1/messages")
                .post(body)
                .addHeader("apikey", database.getApiKey())
                .addHeader("Authorization", "Bearer " + database.getApiKey())
                .addHeader("Content-Type", "application/json")
                .addHeader("Prefer", "return=representation")
                .build();

        // Ausführung des Requests und Verarbeitung der Antwort
        try (Response response = database.getClient().newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.out.println("Request failed: " + response.code());
                System.out.println("Response: " + response.body().string());
                return -1; // Rückgabe von -1 bei Fehler
            } else {
                String responseBody = response.body().string();
                JSONArray jsonArray = new JSONArray(responseBody);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                return jsonObject.getInt("id"); // Rückgabe der ID der gespeicherten Nachricht
            }
        } catch (IOException e) {
            e.printStackTrace();
            return -1; // Fehlerbehandlung
        }
    }

    // Lädt eine Liste von Nachrichten für einen bestimmten Benutzer und limitiert die Anzahl der Ergebnisse.
    public List<Message> loadMessages(String username, int limit) {
        List<Message> messages = new ArrayList<>(); // Liste zur Speicherung der Nachrichten

        try {
            // Erstellung der URL für den Abruf von Benutzer-Nachrichten
            String userMessagesUrl = database.getDatabaseUrl() + "/rest/v1/messages?sender=eq." + username + "&limit=" + limit + "&order=timestamp.asc";
            Request requestUserMessages = new Request.Builder()
                    .url(userMessagesUrl)
                    .get()
                    .addHeader("apikey", database.getApiKey())
                    .addHeader("Authorization", "Bearer " + database.getApiKey())
                    .build();

            // Ausführung des Requests und Verarbeitung der Antwort
            try (Response responseUserMessages = database.getClient().newCall(requestUserMessages).execute()) {
                if (!responseUserMessages.isSuccessful()) {
                    throw new IOException("Unexpected code " + responseUserMessages);
                }

                String jsonResponseUserMessages = responseUserMessages.body().string();
                JSONArray jsonArrayUserMessages = new JSONArray(jsonResponseUserMessages);

                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

                // Iteration über die erhaltenen Nachrichten und Hinzufügen zur Liste
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

                // Abrufen der Bot-Antworten auf diese Nachrichten
                if (jsonArrayUserMessages.length() == 0) {
                    return messages; // Rückgabe der Nachrichtenliste, wenn keine Nachrichten gefunden wurden
                }

                String messageIdsCsv = getMessageIdsAsCsv(jsonArrayUserMessages);
                String botRepliesUrl = database.getDatabaseUrl() + "/rest/v1/messages?related_message_id=in.(" + messageIdsCsv + ")&limit=" + limit + "&order=timestamp.asc";
                Request requestBotReplies = new Request.Builder()
                        .url(botRepliesUrl)
                        .get()
                        .addHeader("apikey", database.getApiKey())
                        .addHeader("Authorization", "Bearer " + database.getApiKey())
                        .build();

                // Verarbeitung der Bot-Antworten
                try (Response responseBotReplies = database.getClient().newCall(requestBotReplies).execute()) {
                    if (!responseBotReplies.isSuccessful()) {
                        throw new IOException("Unexpected code " + responseBotReplies);
                    }

                    String jsonResponseBotReplies = responseBotReplies.body().string();
                    JSONArray jsonArrayBotReplies = new JSONArray(jsonResponseBotReplies);

                    // Hinzufügen der Bot-Antworten zur Nachrichtenliste
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

            // Sortiert die Nachrichten nach ihrem Zeitstempel
            messages.sort((m1, m2) -> m1.getTimestamp().compareTo(m2.getTimestamp()));

        } catch (Exception e) {
            e.printStackTrace(); // Fehlerbehandlung
        }

        return messages; // Rückgabe der Nachrichtenliste
    }

    // Methode zum Escapen von JSON-Sonderzeichen in Text.
    private String escapeJson(String text) {
        return text.replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }

    // Hilfsmethode, um eine CSV-Liste von Nachricht-IDs zu erstellen.
    private String getMessageIdsAsCsv(JSONArray jsonArray) {
        StringBuilder ids = new StringBuilder();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (i > 0) {
                ids.append(",");
            }
            ids.append(jsonObject.getInt("id"));
        }
        return ids.toString(); // Rückgabe der CSV-Liste von IDs
    }
}
