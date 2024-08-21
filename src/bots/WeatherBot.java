package bots;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


import Interfaces.IBot;
import Services.CurrentWeatherService;
import Services.WeatherForecastService;

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
        if (command.equalsIgnoreCase("weather")) {
            String weatherInfo = currentWeatherService.getWeatherInfo("Bielefeld");
            System.out.println(weatherInfo);
            return true;
        } else if (command.equalsIgnoreCase("forecast")) {
            String forecastInfo = weatherForecastService.getForecastInfo("Bielefeld");
            System.out.println(forecastInfo);
            return true;
        } else {
            return false;
        }
    }
}
