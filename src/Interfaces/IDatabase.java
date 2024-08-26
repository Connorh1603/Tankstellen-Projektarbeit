package Interfaces;

import model.Message;
import model.User;

import java.util.List;

public interface IDatabase {
    User authenticateUser(String username, String password);
    int saveMessage(Message message, Integer relatedMessageId); // Rückgabewert int für die Nachricht ID
    List<Message> loadMessages(String username, int limit);
    void close();
}