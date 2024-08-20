package bots;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Interfaces.IBot;

public class WeatherBot implements IBot{
    @Override
    public String getName() {
        return "WeatherBot";
    }

    @Override
    public boolean processCommand(String command) {
        // Pattern to match "weather" with an optional city before or after
        Pattern pattern = Pattern.compile("(.*\\b)?weather(\\b.*)?", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(command.trim());

        if (matcher.matches()) {
            String beforeWeather = matcher.group(1);
            String afterWeather = matcher.group(2);
            String city = "";

            // Check if there is a city name before or after the word "weather"
            if (beforeWeather != null && !beforeWeather.trim().isEmpty()) {
                city = beforeWeather.trim();
            } else if (afterWeather != null && !afterWeather.trim().isEmpty()) {
                city = afterWeather.trim();
            }

            // If a city is detected, display it
            if (!city.isEmpty()) {
                System.out.println("The weather in " + city + " today is sunny.");
            } else {
                System.out.println("The weather today is sunny.");
            }

            return true;
        } else {
            return false;
        }
    }
}
