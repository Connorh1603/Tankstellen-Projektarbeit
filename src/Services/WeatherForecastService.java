package Services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecastService {
    private static final String API_KEY = "0cd9fbf88f2bc8c631d2b0d5d7c528b5"; // Ersetze durch deinen tatsächlichen API-Schlüssel
    private static final String FORECAST_API_URL = "https://api.openweathermap.org/data/2.5/forecast?q=";

    public String getForecastInfo(String city) {
        try {
            // Wettervorhersage für die Stadt abrufen
            @SuppressWarnings("deprecation")
            URL forecastUrl = new URL(FORECAST_API_URL + city + "&appid=" + API_KEY);
            HttpURLConnection forecastConnection = (HttpURLConnection) forecastUrl.openConnection();
            forecastConnection.setRequestMethod("GET");

            int forecastResponseCode = forecastConnection.getResponseCode();
            if (forecastResponseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(forecastConnection.getInputStream()));
                StringBuilder forecastResponse = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    forecastResponse.append(inputLine);
                }
                in.close();

                // Hier kannst du die Antwort weiterverarbeiten, um spezifische Vorhersageinformationen zu extrahieren
                return "Weather Forecast Info: " + forecastResponse.toString();
            } else {
                return "Failed to get forecast information. Response Code: " + forecastResponseCode;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while fetching the forecast information.";
        }
    }
}
