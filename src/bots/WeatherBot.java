package bots;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


import Interfaces.IBot;

public class WeatherBot implements IBot{
    
    private static final String API_KEY = "0cd9fbf88f2bc8c631d2b0d5d7c528b5";
    private static final String CITY = "Bielefeld";
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&appid=" + API_KEY;

    @Override
    public String getName() {
        return "WeatherBot";
    }


    @Override
    public boolean processCommand(String command) {
        if (command.equalsIgnoreCase("weather")) {
            String weatherInfo = getWeatherInfo();
            System.out.println(weatherInfo);
            return true;
        } else {
            return false;
        }
    }

    private String getWeatherInfo() {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Hier kannst du die Antwort verarbeiten und spezifische Wetterinformationen extrahieren
                return "Weather Info: " + response.toString();
            } else {
                return "Failed to get weather information. Response Code: " + responseCode;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while fetching the weather information.";
        }
    }
}
