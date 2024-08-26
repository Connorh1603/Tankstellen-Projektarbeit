package Interfaces;

import model.Message;
import model.User;

import java.util.List;

public interface IDatabase {
    User authenticateUser(String username, String password);
    void saveMessage(Message message);
    List<Message> loadMessages(String username, int limit);
    void close();
}