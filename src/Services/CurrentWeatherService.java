package Services;

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
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Hier kannst du die Antwort verarbeiten und spezifische Wetterinformationen extrahieren
                return "Current Weather Info: " + response.toString();
            } else {
                return "Failed to get weather information. Response Code: " + responseCode;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while fetching the weather information.";
        }
    }
}
