package model;

import java.time.LocalDateTime;

public class Message {
    private int id;  // Neue ID-Variable
    private String sender;
    private String content;
    private LocalDateTime timestamp;

    // Konstruktor ohne ID, wird für neue Nachrichten verwendet
    public Message(String sender, String content, LocalDateTime timestamp) {
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
    }

    // Konstruktor mit ID, wird verwendet, wenn die Nachricht aus der Datenbank abgerufen wird
    public Message(int id, String sender, String content, LocalDateTime timestamp) {
        this.id = id;
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}