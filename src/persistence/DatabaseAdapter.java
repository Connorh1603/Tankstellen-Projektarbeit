package persistence;

import java.util.List;

import Interfaces.IDatabase;
import model.DatabaseManager;
import model.Message;
import model.User;

public class DatabaseAdapter implements IDatabase {
    private DatabaseManager database;

    public DatabaseAdapter(DatabaseManager dbm){
        this.database=dbm;
        database.registerDatabase(new SupabaseDatabase());
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
