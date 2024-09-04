# Bot-Dokumentation für TranslationBot

## Kontextabgrenzung

### Fachlicher Kontext
Der `TranslationBot` ist ein spezialisierter Bot innerhalb des Chatbot-Management-Systems, der für die Übersetzung von Texten in verschiedene Sprachen zuständig ist. Benutzer interagieren mit dem Bot durch Eingabe von Befehlen in der Form `@translatebot <Zielsprachen-Code> <Text>`. Der Bot verwendet die DeepL API, um die angeforderten Übersetzungen durchzuführen.

### Technischer Kontext
Der `TranslationBot` arbeitet innerhalb des Chatbot-Frameworks und kommuniziert mit der DeepL API über den `TranslationService`. Er empfängt Benutzereingaben über den `ChatController`, verarbeitet diese und liefert die Übersetzung als Antwort zurück.

## Lösungsstrategie

Die Lösungsstrategie des `TranslationBot` basiert auf der Analyse von Benutzereingaben, der korrekten Identifikation der Zielsprache und der Verwendung der DeepL API zur Durchführung der Übersetzung.

- **Technologieentscheidungen:**
    - **DeepL API:** Hochwertiger maschineller Übersetzungsdienst, der für präzise Übersetzungen in verschiedene Sprachen sorgt.
    - **Singleton-Pattern:** Der `TranslationService` wird als Singleton implementiert, um sicherzustellen, dass nur eine Instanz des Services im gesamten System verwendet wird.

- **Top-Level-Zerlegung:**
    - **TranslationBot:** Übernimmt die Verarbeitung der Benutzereingaben und die Kommunikation mit dem `TranslationService`.
    - **TranslationService:** Verwaltet die Kommunikation mit der DeepL API und führt die tatsächliche Übersetzung durch.

## Bausteinsicht

**Baustein-Übersicht:**
- **TranslationBot (Klasse):** Implementiert das `IBot` Interface. Enthält Methoden zur Verarbeitung von Benutzerbefehlen und zur Kommunikation mit dem `TranslationService`.
- **TranslationService (Klasse):** Implementiert das Singleton-Pattern und enthält Methoden zur Kommunikation mit der DeepL API.

**Baustein-Details:**
- **TranslationBot:**
    - `getName()`: Liefert den Namen des Bots.
    - `processCommand(String command)`: Verarbeitet den Übersetzungsbefehl und gibt das Ergebnis zurück.

- **TranslationService:**
    - `translate(String text, String targetLang)`: Führt die Übersetzung durch, indem eine Anfrage an die DeepL API gesendet wird.

## Laufzeitsicht

**Typischer Ablauf bei einer Übersetzungsanfrage:**
1. Der Benutzer gibt den Befehl `@translatebot en Hallo` ein.
2. Der `TranslationBot` empfängt den Befehl und analysiert ihn, um den Zielsprachen-Code `en` und den Text `Hallo` zu extrahieren.
3. Der `TranslationBot` ruft die Methode `translate` des `TranslationService` auf und übergibt den Text und die Zielsprache.
4. Der `TranslationService` sendet eine Anfrage an die DeepL API und erhält die Übersetzung zurück.
5. Die übersetzte Nachricht wird vom `TranslationBot` formatiert und dem Benutzer zurückgegeben.

**Wichtige Szenarien:**
- **Normale Übersetzung:** Erfolgreiche Übersetzung eines einfachen Textes.
- **Fehlerhafte Eingabe:** Der Befehl enthält keinen gültigen Zielsprachen-Code oder Text, der Bot gibt eine Fehlermeldung zurück.
- **API-Ausfall:** Die DeepL API ist nicht erreichbar, der Bot gibt eine entsprechende Fehlermeldung zurück.

## Schnittstellen

### Externe Schnittstellen
- **DeepL API:**
    - *Eingabe:* Text und Zielsprachen-Code (z.B. `Hallo`, `en`).
    - *Ausgabe:* Übersetzter Text (z.B. `Hello`).
    - *Spezifikation:* HTTP POST-Request mit Authentifizierung über einen API-Key, Antwort in JSON-Format.

### Interne Schnittstellen
- **IBot Interface:** Das `TranslationBot` implementiert das `IBot` Interface, welches die Methoden `getName()` und `processCommand(String command)` definiert.
- **TranslationService:** Der `TranslationBot` nutzt die Methode `translate(String text, String targetLang)` des `TranslationService`, um die DeepL API aufzurufen.