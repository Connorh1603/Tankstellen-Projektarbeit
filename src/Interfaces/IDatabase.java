package Interfaces;

import model.Message;

import java.util.List;

public interface IDatabase {
    void saveMessage(Message message);
    List<Message> loadMessages(String username, int limit);
    void close();
}