package Interfaces;

import java.util.List;
import model.Message;
import model.User;

// Das IDatabase-Interface definiert Methoden für den Zugriff auf und die Verwaltung von Daten in einer Datenbank.
public interface IDatabase {

    // Authentifiziert einen Benutzer
    User authenticateUser(String username, String password);

    // Speichert eine Nachricht in der Datenbank.
    int saveMessage(Message message, Integer relatedMessageId);

    // Lädt eine Liste von Nachrichten aus der Datenbank. 
    List<Message> loadMessages(String username, int limit);
}
