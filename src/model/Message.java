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

    // Diese Methode gibt die ID des Objekts zurück.
    public int getId() {
        return id;
    }

    // Diese Methode setzt eine neue ID für das Objekt.
    public void setId(int id) {
        this.id = id;
    }

    // Diese Methode gibt den Absender der Nachricht zurück.
    public String getSender() {
        return sender;
    }

    // Diese Methode gibt den Inhalt der Nachricht zurück.
    public String getContent() {
        return content;
    }

    // Diese Methode gibt den Zeitstempel zurück
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}