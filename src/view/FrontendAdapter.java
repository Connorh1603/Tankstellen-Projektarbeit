package view;

import java.util.List;

import Controller.ChatController;
import Interfaces.IFrontend;
import model.Message;

public class FrontendAdapter implements IFrontend{
    private ConsoleView consoleView;

    public FrontendAdapter(ConsoleView consoleView) {
        this.consoleView = consoleView;
    }

    @Override
    public void start(ChatController controller, String user) {
        consoleView.run(controller, user);
    }

    @Override
    public void displayMessage(String message) {
        consoleView.display(message);
    }

    @Override
    public String getUserInput() {
        return consoleView.readInput();
    }

    @Override
    public void displayMessageHistory(List<Message> messageHistory) {
        consoleView.displayMessageHistory(messageHistory);
    }
}
