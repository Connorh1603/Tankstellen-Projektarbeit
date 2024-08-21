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
    private String pendingTextToTranslate = ""; // Text, der übersetzt werden soll

    private final TranslationService translationService = TranslationService.getInstance(); // Instanz des TranslationService

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean processCommand(String command) {
        // Variante A: Direktansprache des Bots
        if (command.startsWith("@translatebot ")) {
            if (command.length() > 14) { // Sicherstellen, dass die Eingabe genügend Länge hat
                handleDirectTranslation(command.substring(14).trim());
            } else {
                System.out.println("Usage: @translatebot <target_language_code> <text>");
            }
            return true;
        }

        // Variante B: Interaktive Konversation mit dem Bot
        if (isActiveConversation || command.equals("@translatebot")) {
            handleInteractiveTranslation(command);
            return true;
        }

        return false;
    }

    /**
     * Behandelt die direkte Übersetzungsanfrage im Format:
     * @translatebot <target_language_code> <text>
     */
    private void handleDirectTranslation(String input) {
        try {
            String[] parts = input.split(" ", 2);
            if (parts.length < 2) {
                System.out.println("Usage: @translatebot <target_language_code> <text>");
                return;
            }

            // Ziel-Sprache und Text extrahieren
            String targetLang = parts[0].toUpperCase();
            String textToTranslate = parts[1];

            // Text übersetzen und ausgeben
            String translatedText = translate(textToTranslate, targetLang);
            System.out.println("Translation: " + translatedText);

        } catch (Exception e) {
            System.out.println("Error processing command: " + e.getMessage());
        }
    }

    /**
     * Behandelt die interaktive Konversation für Übersetzungen:
     * - Der Bot fragt nach der Zielsprache
     * - Der Bot fragt nach dem zu übersetzenden Text
     * - Der Bot gibt die Übersetzung aus
     */
    private void handleInteractiveTranslation(String command) {
        if (!isActiveConversation) {
            // Bot aktiviert, Benutzer wird nach der Zielsprache gefragt
            isActiveConversation = true;
            System.out.println("In welche Sprache soll ich übersetzen? Bitte zweistelligen Code eingeben:");
            System.out.println("    - [EN]glisch");
            System.out.println("    - [DE]utsch");
            // Weitere Sprachen können hinzugefügt werden
            return;
        }

        if (targetLanguage.isEmpty()) {
            // Ziel-Sprache setzen
            targetLanguage = command.toUpperCase();
            System.out.println("Bitte den zu übersetzenden Text eingeben:");
            return;
        }

        if (command.equalsIgnoreCase("quit")) {
            // Konversation beenden
            System.out.println("Bye!");
            isActiveConversation = false;
            targetLanguage = "";
            return;
        }

        try {
            // Text übersetzen und ausgeben
            String translatedText = translate(command, targetLanguage);
            System.out.println("Translation: " + translatedText);
            isActiveConversation = false; // Konversation nach der Übersetzung beenden
            targetLanguage = "";

        } catch (Exception e) {
            System.out.println("Error processing command: " + e.getMessage());
        }
    }

    /**
     * Führt die Übersetzung durch, indem es den TranslationService verwendet.
     *
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