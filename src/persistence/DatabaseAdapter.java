package persistence;

import java.util.List;

import Interfaces.IDatabase;
import model.Message;
import model.User;
import services.SupabaseService;

public class DatabaseAdapter implements IDatabase {
    private Database database;

    public DatabaseAdapter(Database dbm){
        this.database=dbm;
        database.registerDatabase(new SupabaseService());
    }


    @Override
    public User authenticateUser(String username, String password) {
        return database.authenticateUser(username, password);
    }

    @Override
    public int saveMessage(Message message, Integer relatedMessageId) {
        return database.saveMessage(message, relatedMessageId);
    }

    @Override
    public List<Message> loadMessages(String username, int limit) {
        return database.loadMessages(username, limit);
    }


}
