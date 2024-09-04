package Interfaces;

import okhttp3.OkHttpClient;



public interface IDatabase {
    String getDatabaseUrl() ;
    String getApiKey() ;
    public OkHttpClient getClient() ;
}