package Interfaces;

import model.Message;

public interface IBot {
    String getName();
    String processCommand(String command);
}
