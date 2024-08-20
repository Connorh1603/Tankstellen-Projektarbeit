package view;

import Controller.ChatController;
import Interfaces.IFrontend;

public class FrontendAdapter implements IFrontend{
    private ConsoleView consoleView;

    public FrontendAdapter(ConsoleView consoleView) {
        this.consoleView = consoleView;
    }

    @Override
    public void start(ChatController controller) {
        consoleView.run(controller);
    }

    @Override
    public void displayMessage(String message) {
        consoleView.display(message);
    }

    @Override
    public String getUserInput() {
        return consoleView.readInput();
    }
}
