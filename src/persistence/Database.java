package persistence;

import Interfaces.IDatabase;
import model.Message;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Database implements IDatabase {
    private static final String SUPABASE_URL = "https://uhogndirdosqnnbywozi.supabase.co";
    private static final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ";
    private final OkHttpClient client;

    public Database() {
        this.client = new OkHttpClient();
    }

    @Override
    public void saveMessage(Message message) {
        String json = "{ \"sender\": \"" + message.getSender() + "\", " +
                "\"content\": \"" + message.getContent() + "\", " +
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
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Message> loadMessages(String username, int limit) {
        List<Message> messages = new ArrayList<>();
        Request request = new Request.Builder()
                .url(SUPABASE_URL + "/rest/v1/messages?sender=eq." + username + "&limit=" + limit)
                .get()
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            String jsonResponse = response.body().string();
            // Hier würdest du das JSON-Response parsen und in Message-Objekte umwandeln
            // Zum Beispiel mit Jackson oder Gson
        } catch (IOException e) {
            e.printStackTrace();
        }

        return messages;
    }

    @Override
    public void close() {
        // Keine spezifische Close-Operation für OkHttp erforderlich, da es sich um einen globalen Client handelt
    }
}