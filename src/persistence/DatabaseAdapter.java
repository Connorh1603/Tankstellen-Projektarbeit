package persistence;

import java.util.List;

import Interfaces.IDatabase;
import model.Message;
import model.User;
import services.SupabaseService;

// Die Klasse DatabaseAdapter implementiert das IDatabase-Interface und passt die Methoden des Database-Objekts an.
public class DatabaseAdapter implements IDatabase {
    private Database database; // Referenz auf die konkrete Database-Klasse

    // Konstruktor, der das Database-Objekt initialisiert und eine Verbindung zu Supabase herstellt.
    public DatabaseAdapter(Database dbm){
        this.database = dbm;
        database.registerDatabase(new SupabaseService()); // Registrierung des Supabase-Dienstes
    }

    // Methode zur Benutzerauthentifizierung. Leitet den Aufruf an die authenticateUser-Methode der Database-Klasse weiter.
    @Override
    public User authenticateUser(String username, String password) {
        return database.authenticateUser(username, password);
    }

    // Methode zum Speichern einer Nachricht. Leitet den Aufruf an die saveMessage-Methode der Database-Klasse weiter.
    @Override
    public int saveMessage(Message message, Integer relatedMessageId) {
        return database.saveMessage(message, relatedMessageId);
    }

    // Methode zum Laden von Nachrichten. Leitet den Aufruf an die loadMessages-Methode der Database-Klasse weiter.
    @Override
    public List<Message> loadMessages(String username, int limit) {
        return database.loadMessages(username, limit);
    }
}
