package bots;

import Interfaces.IBot;
import services.TranslationService;

// Die Klasse TranslationBot implementiert das IBot-Interface 
public class TranslationBot implements IBot {

    // Instanzvariable 
    private final TranslationService translationService = TranslationService.getInstance();

    // Implementierung der Methode getName() 
    @Override
    public String getName() {
        return "TranslationBot";
    }

    // Implementierung der Methode processCommand() 
    @Override
    public String processCommand(String command) {
        if (!command.toLowerCase().startsWith("@translatebot")) {
            return null; // Befehl ignorieren, wenn er nicht für den Bot bestimmt ist
        }

        String[] parts = command.substring(13).trim().split(" ", 2); // Entfernt "@translatebot" und trennt die Eingabe
        if (parts.length < 2) {
            return "Usage: @translatebot <target_language_code> <text>"; // Rückgabe der Anleitung
        }

        try {
            String translatedText = translationService.translate(parts[1], parts[0].toUpperCase()); // Übersetzt den Text
            return "Translation: " + translatedText; // Gibt die Übersetzung zurück
        } catch (Exception e) {
            return "Error: " + e.getMessage(); // Fehlerbehandlung
        }
    }
}