package view;

import java.util.List;

import Controller.ChatController;
import Interfaces.IFrontend;
import model.Message;

// Die Klasse FrontendAdapter implementiert das IFrontend-Interface und adaptiert die ConsoleView
public class FrontendAdapter implements IFrontend {
    private ConsoleView consoleView; 

    // Konstruktor, der die ConsoleView übergeben bekommt 
    public FrontendAdapter(ConsoleView consoleView) {
        this.consoleView = consoleView;
    }

    // Startet die ConsoleView
    @Override
    public void start(ChatController controller, String user) {
        consoleView.run(controller, user); // Leitet den Aufruf an die run-Methode der ConsoleView weiter
    }

    // Zeigt eine Nachricht in der ConsoleView an.
    @Override
    public void displayMessage(String message) {
        consoleView.display(message); // Leitet den Aufruf an die display-Methode der ConsoleView weiter
    }

    // Liest eine Benutzereingabe über die ConsoleView und gibt diese zurück.
    @Override
    public String getUserInput() {
        return consoleView.readInput(); // Leitet den Aufruf an die readInput-Methode der ConsoleView weiter
    }

    // Zeigt den Chatverlauf über die ConsoleView an.
    @Override
    public void displayMessageHistory(List<Message> messageHistory) {
        consoleView.displayMessageHistory(messageHistory); // Leitet den Aufruf an die displayMessageHistory-Methode der ConsoleView weiter
    }
}
