# Bot-Dokumentation für TranslationBot

## Kontextabgrenzung

### Fachlicher Kontext
Der `TranslationBot` ist ein spezialisierter Bot, der für die Übersetzung von Texten verantwortlich ist. Benutzer interagieren mit dem Bot, indem sie den Befehl `@translatebot <Zielsprachen-Code> <Text>` eingeben. Der Bot verwendet die DeepL API, um die Übersetzungen durchzuführen.

### Technischer Kontext
Der `TranslationBot` arbeitet innerhalb des Chatbot-Frameworks und verwendet den `TranslationService` zur Kommunikation mit der DeepL API.

## Lösungsstrategie

Die grundlegenden Designentscheidungen:
- **DeepL API:** Die API wird für maschinelle Übersetzungen verwendet.
- **Singleton Pattern:** Der `TranslationService` wird als Singleton implementiert.

## Bausteinsicht

### TranslationBot:
- **getName():** Gibt den Namen des Bots zurück.
- **processCommand(String command):** Verarbeitet den Übersetzungsbefehl.

### TranslationService:
- **translate(String text, String targetLang):** Führt die Übersetzung durch.

## Laufzeitsicht

### Typischer Ablauf:
1. Der Benutzer gibt den Befehl ein.
2. Der `TranslationBot` analysiert den Befehl und ruft den `TranslationService` auf.
3. Der Text wird über die DeepL API übersetzt.
4. Die Übersetzung wird dem Benutzer zurückgegeben.

### Wichtige Szenarien:
- **Normale Übersetzung:** Erfolgreiche Übersetzung eines Textes.
- **Fehlerhafte Eingabe:** Unvollständige oder falsche Befehle werden mit einer Fehlermeldung beantwortet.
- **API-Ausfall:** Gibt eine Fehlermeldung zurück, wenn die API nicht verfügbar ist.

## Schnittstellen

### Externe Schnittstelle:
- **DeepL API:** Übersetzt Text in verschiedene Sprachen.

### Interne Schnittstellen:
- **IBot Interface:** Implementiert die Methoden `getName()` und `processCommand(String command)`.
- **TranslationService:** Bietet die Übersetzungsfunktionalität.