package bots;

import Interfaces.IBot;

public class WeatherBot implements IBot{
    @Override
    public String getName() {
        return "WeatherBot";
    }

    @Override
    public void processCommand(String command) {
        if (command.equalsIgnoreCase("weather")) {
            System.out.println("The weather today is sunny.");
        } else {
            System.out.println("Unknown command for WeatherBot.");
        }
    }
}
