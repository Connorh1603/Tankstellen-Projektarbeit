package bots;

import Interfaces.IBot;
import services.CurrentWeatherService;
import services.WeatherForecastService;
import model.ListChecker;

// Die Klasse WeatherBot implementiert das IBot-Interface 
public class WeatherBot implements IBot {
    
    // Instanzvariablen 
    private CurrentWeatherService currentWeatherService;
    private WeatherForecastService weatherForecastService;

    // Konstruktor der WeatherBot-Klasse. Initialisiert die beiden Services.
    public WeatherBot() {
        this.currentWeatherService = new CurrentWeatherService();
        this.weatherForecastService = new WeatherForecastService();
    }

    // Implementierung der Methode getName() 
    @Override
    public String getName() {
        return "WeatherBot";
    }

    // Implementierung der Methode processCommand() 
    @Override
    public String processCommand(String command) {
        // ListChecker wird verwendet, um Städte aus einer CSV-Datei zu überprüfen.
        ListChecker cityCheck = new ListChecker("src/documents/Staette.CSV");

        command = command.toLowerCase();

        // Überprüft, ob der Befehl das Wort "wetter" enthält.
        if (command.contains(" wetter ")) {

            // Überprüft, ob der Befehl nach dem aktuellen Wetter fragt (z.B. "Wie ist das Wetter?").
            if (command.contains(" ist ")) {
                // Ruft die aktuelle Wetterinformation für die gefundene Stadt ab.
                String weatherInfo = currentWeatherService.getWeatherInfo(cityCheck.findCityInText(command));
                return weatherInfo;

            // Überprüft, ob der Befehl nach der Wettervorhersage fragt (z.B. "Wie wird das Wetter?").
            } else if (command.contains(" wird ")) {
                // Ruft die Wettervorhersage für die gefundene Stadt ab.
                String forecastInfo = weatherForecastService.getForecastInfo(cityCheck.findCityInText(command));
                return forecastInfo;
            }

            // Falls kein Zeitbezug (aktuell oder zukünftiges Wetter) angegeben ist, wird eine entsprechende Nachricht zurückgegeben.
            return "No Information about the time given";
        }
        else {
            // Falls der Befehl kein Wetterbezug hat, wird null zurückgegeben.
            return null;
        }
    }
}
