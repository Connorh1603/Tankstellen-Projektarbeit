package model;


import persistence.SupabaseDatabase;
import java.util.ArrayList;
import java.util.List;
import okhttp3.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONObject;

import Interfaces.IDatabase;

public class DatabaseManager {
    private IDatabase database;

    public void registerDatabase(IDatabase db) {
        this.database = db;
    }

    public User authenticateUser(String username, String password) {
        // Authentifizierungsmethode (hier hartkodiert für Beispiel)
        if (username.equals("user1") && password.equals("password1")) {
            return new User(username, password);
        }
        if (username.equals("user2") && password.equals("password2")) {
            return new User(username, password);
        }
        return null;
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
                .url(database.getDatabaseUrl() + "/rest/v1/messages")
                .post(body)
                .addHeader("apikey", database.getApiKey())
                .addHeader("Authorization", "Bearer " + database.getApiKey())
                .addHeader("Content-Type", "application/json")
                .addHeader("Prefer", "return=representation")
                .build();

        try (Response response = database.getClient().newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.out.println("Request failed: " + response.code());
                System.out.println("Response: " + response.body().string());
                return -1;
            } else {
                String responseBody = response.body().string();
                JSONArray jsonArray = new JSONArray(responseBody);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                return jsonObject.getInt("id");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<Message> loadMessages(String username, int limit) {
        List<Message> messages = new ArrayList<>();

        try {
            String userMessagesUrl = database.getDatabaseUrl() + "/rest/v1/messages?sender=eq." + username + "&limit=" + limit + "&order=timestamp.asc";
            Request requestUserMessages = new Request.Builder()
                    .url(userMessagesUrl)
                    .get()
                    .addHeader("apikey", database.getApiKey())
                    .addHeader("Authorization", "Bearer " + database.getApiKey())
                    .build();

            try (Response responseUserMessages = database.getClient().newCall(requestUserMessages).execute()) {
                if (!responseUserMessages.isSuccessful()) {
                    throw new IOException("Unexpected code " + responseUserMessages);
                }

                String jsonResponseUserMessages = responseUserMessages.body().string();
                JSONArray jsonArrayUserMessages = new JSONArray(jsonResponseUserMessages);

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

                if (jsonArrayUserMessages.length() == 0) {
                    return messages;
                }

                String messageIdsCsv = getMessageIdsAsCsv(jsonArrayUserMessages);
                String botRepliesUrl = database.getDatabaseUrl() + "/rest/v1/messages?related_message_id=in.(" + messageIdsCsv + ")&limit=" + limit + "&order=timestamp.asc";
                Request requestBotReplies = new Request.Builder()
                        .url(botRepliesUrl)
                        .get()
                        .addHeader("apikey", database.getApiKey())
                        .addHeader("Authorization", "Bearer " + database.getApiKey())
                        .build();

                try (Response responseBotReplies = database.getClient().newCall(requestBotReplies).execute()) {
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

            messages.sort((m1, m2) -> m1.getTimestamp().compareTo(m2.getTimestamp()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return messages;
    }

    private String escapeJson(String text) {
        return text.replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
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
}