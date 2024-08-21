package Services;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class WeatherForecastService {
    private static final String API_KEY = "0cd9fbf88f2bc8c631d2b0d5d7c528b5"; // Ersetze durch deinen tatsächlichen API-Schlüssel
    private static final String FORECAST_API_URL = "https://api.openweathermap.org/data/2.5/forecast?q=";


    public String getForecastInfo(String city) {
        try {
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

                // JSON-Antwort parsen und relevante Informationen extrahieren
                JSONObject jsonResponse = new JSONObject(forecastResponse.toString());
                JSONArray list = jsonResponse.getJSONArray("list");

                StringBuilder formattedForecast = new StringBuilder(String.format("Weather Forecast for %s:\n", city));

                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("d. MMMM yyyy, HH:mm 'Uhr'", Locale.GERMANY);

                for (int i = 0; i < list.length(); i++) {
                    JSONObject forecast = list.getJSONObject(i);
                    LocalDateTime dateTime = LocalDateTime.parse(forecast.getString("dt_txt"), inputFormatter);
                    String formattedDateTime = dateTime.format(outputFormatter);
                    
                    JSONObject main = forecast.getJSONObject("main");
                    JSONObject wind = forecast.getJSONObject("wind");
                    String weatherDescription = forecast.getJSONArray("weather").getJSONObject(0).getString("description");

                    formattedForecast.append(String.format(
                            "%s: Temperature: %.2f°C, Feels Like: %.2f°C, Humidity: %d%%, Description: %s, Wind Speed: %.2f m/s\n",
                            formattedDateTime,
                            main.getDouble("temp") - 273.15,
                            main.getDouble("feels_like") - 273.15,
                            main.getInt("humidity"),
                            weatherDescription,
                            wind.getDouble("speed")
                    ));
                }

                return formattedForecast.toString();
            } else {
                return "Failed to get forecast information. Response Code: " + forecastResponseCode;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while fetching the forecast information.";
        }
    }
}
