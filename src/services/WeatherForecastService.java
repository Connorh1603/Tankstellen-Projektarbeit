package services;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

// Die Klasse WeatherForecastService ruft die Wettervorhersage für eine Stadt von der OpenWeatherMap API ab.
public class WeatherForecastService {
    // API-Schlüssel und URL zur OpenWeatherMap API
    private static final String API_KEY = "0cd9fbf88f2bc8c631d2b0d5d7c528b5"; // API-Schlüssel für OpenWeatherMap
    private static final String FORECAST_API_URL = "https://api.openweathermap.org/data/2.5/forecast?q=";

    // Methode, die Wettervorhersagen für eine gegebene Stadt abruft und formatiert zurückgibt.
    public String getForecastInfo(String city) {
        try {
            // Erstellen der URL für die Vorhersage-API mit Stadtname und API-Schlüssel
            URL forecastUrl = new URL(FORECAST_API_URL + city + "&appid=" + API_KEY);
            HttpURLConnection forecastConnection = (HttpURLConnection) forecastUrl.openConnection(); // Verbindung zur API öffnen
            forecastConnection.setRequestMethod("GET"); // HTTP-Methode GET verwenden

            int forecastResponseCode = forecastConnection.getResponseCode(); // Überprüfen des HTTP-Antwortcodes
            if (forecastResponseCode == HttpURLConnection.HTTP_OK) { // Wenn die Antwort OK (200) ist
                BufferedReader in = new BufferedReader(new InputStreamReader(forecastConnection.getInputStream()));
                StringBuilder forecastResponse = new StringBuilder();
                String inputLine;

                // Lesen der API-Antwort und Aufbau des JSON-Strings
                while ((inputLine = in.readLine()) != null) {
                    forecastResponse.append(inputLine);
                }
                in.close();

                // JSON-Antwort parsen und relevante Informationen extrahieren
                JSONObject jsonResponse = new JSONObject(forecastResponse.toString());
                JSONArray list = jsonResponse.getJSONArray("list"); 

                // Aufbau der formatieren Vorhersage 
                StringBuilder formattedForecast = new StringBuilder(String.format("Weather Forecast for %s:\n", city));

                // Formatter für Eingangs- und Ausgangszeit
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("d. MMMM yyyy, HH:mm 'Uhr'", Locale.GERMANY);

                // Iteration über die Vorhersagen (mehrere Zeitpunkte)
                for (int i = 0; i < list.length(); i++) {
                    JSONObject forecast = list.getJSONObject(i);
                    LocalDateTime dateTime = LocalDateTime.parse(forecast.getString("dt_txt"), inputFormatter);
                    String formattedDateTime = dateTime.format(outputFormatter); // Formatierte Ausgabe der Vorhersagezeit

                    // Extrahieren der Wetterinformationen (Temperatur, Feuchtigkeit, Wind, usw.)
                    JSONObject main = forecast.getJSONObject("main");
                    JSONObject wind = forecast.getJSONObject("wind");
                    String weatherDescription = forecast.getJSONArray("weather").getJSONObject(0).getString("description");

                    // Aufbau der formatierten Vorhersage-Details für jeden Zeitpunkt
                    formattedForecast.append(String.format(
                            "%s: Temperature: %.2f°C, Feels Like: %.2f°C, Humidity: %d%%, Description: %s, Wind Speed: %.2f m/s\n",
                            formattedDateTime,
                            main.getDouble("temp") - 273.15, // Umrechnung von Kelvin in Celsius
                            main.getDouble("feels_like") - 273.15, // Gefühlte Temperatur in Celsius
                            main.getInt("humidity"), // Feuchtigkeit in Prozent
                            weatherDescription, // Beschreibung des Wetters
                            wind.getDouble("speed") // Windgeschwindigkeit in m/s
                    ));
                }

                return formattedForecast.toString(); // Rückgabe der formatierten Wettervorhersage
            } else {
                // Fehlermeldung, wenn die API-Anfrage fehlschlägt
                return "Failed to get forecast information. Response Code: " + forecastResponseCode;
            }
        } catch (Exception e) {
            e.printStackTrace(); // Fehlerbehandlung bei Ausnahmefällen
            return "An error occurred while fetching the forecast information.";
        }
    }
}
