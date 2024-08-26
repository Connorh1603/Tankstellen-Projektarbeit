package services;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrentWeatherService {
    private static final String API_KEY = "0cd9fbf88f2bc8c631d2b0d5d7c528b5";
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather?q=";

    public String getWeatherInfo(String city) {
        try {
            @SuppressWarnings("deprecation")
            URL url = new URL(API_URL + city + "&appid=" + API_KEY);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // JSON-Antwort parsen und relevante Informationen extrahieren
                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONObject main = jsonResponse.getJSONObject("main");
                JSONObject wind = jsonResponse.getJSONObject("wind");
                String weatherDescription = jsonResponse.getJSONArray("weather").getJSONObject(0).getString("description");

                return String.format(
                        "Current Weather in %s:\nTemperature: %.2f°C\nFeels Like: %.2f°C\nHumidity: %d%%\nDescription: %s\nWind Speed: %.2f m/s",
                        city,
                        main.getDouble("temp") - 273.15,
                        main.getDouble("feels_like") - 273.15,
                        main.getInt("humidity"),
                        weatherDescription,
                        wind.getDouble("speed")
                );
            } else {
                return "Failed to get weather information. Response Code: " + responseCode;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while fetching the weather information.";
        }
    }
}
