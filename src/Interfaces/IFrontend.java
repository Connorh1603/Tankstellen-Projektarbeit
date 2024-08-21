package Interfaces;

import Controller.ChatController;
import model.Message;
import java.util.List;

public interface IFrontend {
    void start(ChatController controller, String user);
    void displayMessage(String message);
    String getUserInput();
    void displayMessageHistory(List<Message> messageHistory);
}
