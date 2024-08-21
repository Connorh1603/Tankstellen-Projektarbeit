package bots;



import Interfaces.IBot;
import Services.CurrentWeatherService;
import Services.WeatherForecastService;
import model.ListChecker;


public class WeatherBot implements IBot{
    
    private CurrentWeatherService currentWeatherService;
    private WeatherForecastService weatherForecastService;

    public WeatherBot() {
        this.currentWeatherService = new CurrentWeatherService();
        this.weatherForecastService = new WeatherForecastService();
    }

    @Override
    public String getName() {
        return "WeatherBot";
    }

    @Override
    public boolean processCommand(String command) {
        ListChecker cityCheck=new ListChecker("src/documents/Staette.CSV");
        command=command.toLowerCase();
        if (command.contains(" wetter ")) {
            if (command.contains(" ist ")) {
                String weatherInfo = currentWeatherService.getWeatherInfo(cityCheck.findCityInText(command));
                System.out.println(weatherInfo);
            } else if (command.contains(" wird ")) {
                String forecastInfo = weatherForecastService.getForecastInfo(cityCheck.findCityInText(command));
                System.out.println(forecastInfo);
            }
            return true;
        }
        else   {
            return false;
        }
    }

}
