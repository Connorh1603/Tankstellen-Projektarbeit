package bots;

import Interfaces.IBot;

public class WikiBot implements IBot{
    @Override
    public String getName() {
        return "WikiBot";
    }

    @Override
    public boolean processCommand(String command) {
        if (command.equalsIgnoreCase("Bielefeld")) {
            System.out.println("Bielefeld ist schön");
            return true;
        } else {
            return false;
        }
    }
}
