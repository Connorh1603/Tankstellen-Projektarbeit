package bots;

import Interfaces.IBot;
import Services.TranslationService;

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
