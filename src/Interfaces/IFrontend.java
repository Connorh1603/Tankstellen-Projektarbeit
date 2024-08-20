package Interfaces;

import Controller.ChatController;

public interface IFrontend {
    void start(ChatController controller);
    void displayMessage(String message);
    String getUserInput();
}
