package bots;

import Interfaces.IBot;
import Services.TranslationService;
import model.ListChecker;
import model.Message;

/**
 * Bot für Übersetzungen, der den TranslationService zur Durchführung von Übersetzungen verwendet.
 */
public class TranslationBot implements IBot {
    private final String name = "TranslationBot"; // Name des Bots
    private boolean isActiveConversation = false; // Zeigt an, ob der Bot aktiv ist und auf Eingaben wartet
    private String targetLanguage = ""; // Ziel-Sprache für die Übersetzung

    private final TranslationService translationService = TranslationService.getInstance(); // Instanz des TranslationService

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String processCommand(String command) {
        // Variante A: Direktansprache des Bots
        if (command.startsWith("@translatebot")) {
            if (command.length() > 13) { // Sicherstellen, dass die Eingabe genügend Länge hat
                return handleDirectTranslation(command.substring(13).trim());
            } else {
                return "Usage: @translatebot <target_language_code> <text>";
            }
        }

        // Variante B: Interaktive Konversation mit dem Bot
        if (isActiveConversation || command.equals("@translatebot")) {
            return handleInteractiveTranslation(command);
        }

        return null;
    }

    /**
     * Behandelt die direkte Übersetzungsanfrage im Format:
     * @translatebot <target_language_code> <text>
     */
    private String handleDirectTranslation(String input) {
        try {
            String[] parts = input.split(" ", 2);
            if (parts.length < 2) {
                return "Usage: @translatebot <target_language_code> <text>";
            }

            // Ziel-Sprache und Text extrahieren
            String targetLang = parts[0].toUpperCase();
            String textToTranslate = parts[1];

            // Text übersetzen und Ergebnis zurückgeben
            String translatedText = translate(textToTranslate, targetLang);
            return "Translation: " + translatedText;

        } catch (Exception e) {
            return "Error processing command: " + e.getMessage();
        }
    }

    /**
     * Behandelt die interaktive Konversation für Übersetzungen:
     * - Der Bot fragt nach der Zielsprache
     * - Der Bot fragt nach dem zu übersetzenden Text
     * - Der Bot gibt die Übersetzung aus
     */
    private String handleInteractiveTranslation(String command) {
        if (!isActiveConversation) {
            // Bot aktiviert, Benutzer wird nach der Zielsprache gefragt
            isActiveConversation = true;
            return "In welche Sprache soll ich übersetzen? Bitte zweistelligen Code eingeben:\n    - [EN]glisch\n    - [DE]utsch";
            // Weitere Sprachen können hinzugefügt werden
        }

        if (targetLanguage.isEmpty()) {
            // Ziel-Sprache setzen
            targetLanguage = command.toUpperCase();
            return "Bitte den zu übersetzenden Text eingeben:";
        }

        if (command.equalsIgnoreCase("quit")) {
            // Konversation beenden
            isActiveConversation = false;
            targetLanguage = "";
            return "Bye!";
        }

        try {
            // Text übersetzen und Ergebnis zurückgeben
            String translatedText = translate(command, targetLanguage);
            isActiveConversation = false; // Konversation nach der Übersetzung beenden
            targetLanguage = "";
            return "Translation: " + translatedText;

        } catch (Exception e) {
            return "Error processing command: " + e.getMessage();
        }
    }

    /**
     * Führt die Übersetzung durch.
     * @param text Der zu übersetzende Text.
     * @param targetLang Der Ziel-Sprachcode (z.B. "EN" oder "DE").
     * @return Der übersetzte Text.
     * @throws Exception Bei Fehlern während der Übersetzung.
     */
    private String translate(String text, String targetLang) throws Exception {
        // Verwenden Sie den TranslationService zur Durchführung der Übersetzung
        return translationService.translate(text, targetLang);
    }
}