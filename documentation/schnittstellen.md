## 1.11. Schnittstellen

### Externe Schnittstellen

- **OpenWeatherMap API:**
    - *Eingabe:* Stadtname (z.B. `Berlin`).
    - *Ausgabe:* JSON-Objekt mit aktuellen Wetterdaten und Wettervorhersagen (z.B. Temperatur, Windgeschwindigkeit).
    - *Verwendung:* Vom `WeatherBot` über den `CurrentWeatherService` und `WeatherForecastService`.

- **DeepL API:**
    - *Eingabe:* Text und Zielsprachen-Code (z.B. `Hallo` und `en`).
    - *Ausgabe:* Übersetzter Text als String.
    - *Verwendung:* Vom `TranslationBot` über den `TranslationService`.

- **Wikipedia API:**
    - *Eingabe:* Suchbegriff (z.B. `Berlin`).
    - *Ausgabe:* JSON-Objekt mit Zusammenfassungen und relevanten Wikipedia-Seiten.
    - *Verwendung:* Vom `WikiBot` über den `WikiService`.

### Interne Schnittstellen

- **ChatController zu Bots:**
    - *Methoden:* `processCommand(String command)` wird aufgerufen, um Benutzereingaben zu verarbeiten und die jeweilige Antwort zu generieren.

- **ChatController zu DatabaseManager:**
    - *Methoden:* `saveMessage(Message message, Integer relatedMessageId)` zum Speichern von Nachrichten und `loadMessages(String username, int limit)` zum Laden von Nachrichten aus der Datenbank.

- **BotManager zu ChatController:**
    - *Methoden:* `activateBot(int id)`, `deactivateBot(int id)` zur Verwaltung der aktiven Bots.