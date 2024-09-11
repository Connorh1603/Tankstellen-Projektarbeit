package Interfaces;

// Das IBot-Interface definiert die grundlegenden Funktionen, die ein Bot haben muss.
public interface IBot {

    // Gibt den Namen des Bots zurück. 
    String getName();

    // Verarbeitet einen gegebenen Befehl und gibt eine entsprechende Antwort zurück.
    String processCommand(String command);
}
