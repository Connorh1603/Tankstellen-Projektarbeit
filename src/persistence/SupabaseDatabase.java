package persistence;

import Interfaces.IDatabase;
import okhttp3.OkHttpClient;

public class SupabaseDatabase implements IDatabase{
    private static final String SUPABASE_URL = "https://uhogndirdosqnnbywozi.supabase.co";
    private static final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InVob2duZGlyZG9zcW5uYnl3b3ppIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTcyNDYwMDIwMywiZXhwIjoyMDQwMTc2MjAzfQ.rE9ykKiHwapQynE89CuhXdHT7xEpDOWY7YiRArHlUII"; // Verwende hier deine Supabase-API

    private final OkHttpClient client;

    public SupabaseDatabase() {
        this.client = new OkHttpClient();
    }

    public String getDatabaseUrl() {
        return SUPABASE_URL;
    }

    public String getApiKey() {
        return API_KEY;
    }

    public OkHttpClient getClient() {
        return client;
    }
}