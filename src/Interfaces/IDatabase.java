package Interfaces;

import java.util.List;

import model.Message;
import model.User;



public interface IDatabase {
    User authenticateUser(String username, String password) ;
    int saveMessage(Message message, Integer relatedMessageId);
    List<Message> loadMessages(String username, int limit);
}