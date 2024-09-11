package Interfaces;

import Controller.ChatController;
import model.Message;
import java.util.List;

// Das IFrontend-Interface definiert Methoden für die Interaktion mit der Benutzeroberfläche (Frontend).
public interface IFrontend {

    // Startet die Benutzeroberfläche
    void start(ChatController controller, String user);

    // Zeigt eine Nachricht an, die als String übergeben wird.
    void displayMessage(String message);

    // Holt die Benutzereingabe 
    String getUserInput();

    // Zeigt den Chatverlauf (Liste von Nachrichten) an.
    void displayMessageHistory(List<Message> messageHistory);
}
