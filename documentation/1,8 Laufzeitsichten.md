## 1.8. Laufzeitsichten

Das Verhalten des Systems zur Laufzeit kann durch folgende Szenarien beschrieben werden:

### Szenario 1: Übersetzungsanfrage an den TranslationBot
1. **Benutzerinteraktion:** Der Benutzer gibt den Befehl `@translatebot en Hallo` ein.
2. **Eingangsverarbeitung:** Der `ChatController` empfängt die Eingabe und speichert sie als `Message`.
3. **Bot-Aktivierung:** Der `TranslationBot` wird aktiviert, und die `processCommand`-Methode wird aufgerufen.
4. **Befehlsverarbeitung:** Der Bot extrahiert die Zielsprache und den Text und ruft den `TranslationService` auf.
5. **API-Kommunikation:** Der `TranslationService` übersetzt den Text mithilfe der DeepL API.
6. **Antwort:** Die Übersetzung wird zurückgegeben, gespeichert und an den Benutzer ausgegeben.

```
sequenceDiagram
participant User
participant ChatController
participant BotManager
participant TranslationBot
participant TranslationService
participant DeepL_API

    User->>ChatController: Eingabe "@translatebot en Hallo"
    ChatController->>BotManager: Aktivierte Bots abrufen
    BotManager->>TranslationBot: Aufruf processCommand("@translatebot en Hallo")
    TranslationBot->>TranslationService: Aufruf translate("Hallo", "EN")
    TranslationService->>DeepL_API: Anfrage zur Übersetzung (POST)
    DeepL_API-->>TranslationService: Übersetzter Text "Hello"
    TranslationService-->>TranslationBot: Übersetzter Text "Hello"
    TranslationBot-->>ChatController: Ausgabe "Translation: Hello"
    ChatController-->>User: Ausgabe "Translation: Hello"
```

### Szenario 2: Wetteranfrage an den WeatherBot
1. **Benutzerinteraktion:** Der Benutzer fragt nach dem Wetter in einer Stadt.
2. **Eingangsverarbeitung:** Der `ChatController` empfängt und speichert die Eingabe.
3. **Bot-Aktivierung:** Der `WeatherBot` wird aktiviert.
4. **Befehlsverarbeitung:** Der Bot erkennt den Befehl und verwendet den `ListChecker`, um die Stadt zu extrahieren.
5. **API-Kommunikation:** Der `WeatherBot` nutzt die OpenWeatherMap API, um aktuelle Wetterdaten abzurufen.
6. **Antwort:** Die Wetterdaten werden zurückgegeben und dem Benutzer ausgegeben.

### Szenario 3: Wikipedia-Abfrage an den WikiBot
1. **Benutzerinteraktion:** Der Benutzer fragt nach Informationen zu einem Begriff.
2. **Eingangsverarbeitung:** Der `ChatController` empfängt und speichert die Anfrage.
3. **Bot-Aktivierung:** Der `WikiBot` wird aktiviert.
4. **Befehlsverarbeitung:** Der Bot extrahiert den Suchbegriff und ruft die Wikipedia API auf.
5. **API-Kommunikation:** Der Bot holt die Daten von der API.
6. **Antwort:** Die Informationen werden dem Benutzer zurückgegeben.