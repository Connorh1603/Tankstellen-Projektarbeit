package services;

import okhttp3.OkHttpClient;

// Die Klasse SupabaseService stellt Verbindungen zu einer Supabase-Datenbank her 
public class SupabaseService {
    // Die Supabase URL und der API-Schlüssel
    private static final String SUPABASE_URL = "https://uhogndirdosqnnbywozi.supabase.co"; // Die URL der Supabase-Datenbank
    private static final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InVob2duZGlyZG9zcW5uYnl3b3ppIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTcyNDYwMDIwMywiZXhwIjoyMDQwMTc2MjAzfQ.rE9ykKiHwapQynE89CuhXdHT7xEpDOWY7YiRArHlUII"; // Supabase API-Schlüssel (Dieser sollte sicher gespeichert werden)

    private final OkHttpClient client; // HTTP-Client für Anfragen an die Supabase-Datenbank

    // Konstruktor der Klasse, der einen neuen OkHttpClient initialisiert.
    public SupabaseService() {
        this.client = new OkHttpClient();
    }

    // Gibt die URL der Supabase-Datenbank zurück.
    public String getDatabaseUrl() {
        return SUPABASE_URL;
    }

    // Gibt den API-Schlüssel für die Supabase zurück
    public String getApiKey() {
        return API_KEY;
    }

    // Gibt den OkHttpClient zurück, der für HTTP-Anfragen verwendet wird.
    public OkHttpClient getClient() {
        return client;
    }
}
