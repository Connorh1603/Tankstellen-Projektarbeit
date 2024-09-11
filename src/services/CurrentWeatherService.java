package services;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

// Die Klasse CurrentWeatherService ruft aktuelle Wetterinformationen für eine Stadt von der OpenWeatherMap API ab.
public class CurrentWeatherService {
    // API-Schlüssel und URL zur OpenWeatherMap API
    private static final String API_KEY = "0cd9fbf88f2bc8c631d2b0d5d7c528b5";
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather?q=";

    // Methode, die Wetterinformationen für eine gegebene Stadt abruft und zurückgibt.
    public String getWeatherInfo(String city) {
        try {
            // Erstellen der URL zur API-Anfrage 
            @SuppressWarnings("deprecation")
            URL url = new URL(API_URL + city + "&appid=" + API_KEY);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // Verbindung zur API öffnen
            connection.setRequestMethod("GET"); // HTTP-Methode GET verwenden

            int responseCode = connection.getResponseCode(); // Überprüfen des HTTP-Antwortcodes
            if (responseCode == HttpURLConnection.HTTP_OK) { // Wenn die Antwort OK ist
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                // Lesen der API-Antwort und Aufbau des JSON-Strings
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parsen der JSON-Antwort, um relevante Wetterinformationen zu extrahieren
                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONObject main = jsonResponse.getJSONObject("main");
                JSONObject wind = jsonResponse.getJSONObject("wind");
                String weatherDescription = jsonResponse.getJSONArray("weather").getJSONObject(0).getString("description");

                // Formatierte Rückgabe der Wetterdaten, einschließlich Temperatur, Feuchtigkeit, Windgeschwindigkeit und Wetterbeschreibung
                return String.format(
                        "Current Weather in %s:\nTemperature: %.2f°C\nFeels Like: %.2f°C\nHumidity: %d%%\nDescription: %s\nWind Speed: %.2f m/s",
                        city,
                        main.getDouble("temp") - 273.15, // Umrechnung von Kelvin in Celsius
                        main.getDouble("feels_like") - 273.15, // Umrechnung von Kelvin in Celsius
                        main.getInt("humidity"), // Feuchtigkeit in Prozent
                        weatherDescription, // Wetterbeschreibung
                        wind.getDouble("speed") // Windgeschwindigkeit in m/s
                );
            } else {
                // Fehlermeldung, wenn die Antwort nicht OK (200) ist
                return "Failed to get weather information. Response Code: " + responseCode;
            }
        } catch (Exception e) {
            e.printStackTrace(); // Fehlerbehandlung bei Ausnahmefällen
            return "An error occurred while fetching the weather information.";
        }
    }
}
