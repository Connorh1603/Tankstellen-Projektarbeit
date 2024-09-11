package model;

// Die Klasse User repräsentiert einen Benutzer mit einem Benutzernamen und Passwort.
public class User {
    private String username;  
    private String password;  

    // Konstruktor, der den Benutzernamen und das Passwort initialisiert.
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Gibt den Benutzernamen zurück.
    public String getUsername() {
        return username;
    }

    // Validiert das eingegebene Passwort. 
    public boolean validatePassword(String inputPassword) {
        return this.password.equals(inputPassword); // Vergleicht das gespeicherte Passwort mit der Eingabe
    }
}
