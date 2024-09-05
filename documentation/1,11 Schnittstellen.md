## 1.11. Schnittstellen

### Externe Schnittstellen

- **OpenWeatherMap API:**
  - *Eingabe:* Stadtname.
  - *Ausgabe:* JSON mit Wetterdaten und Vorhersagen.
  - *Verwendung:* `WeatherBot` verwendet `CurrentWeatherService` und `WeatherForecastService`.

- **DeepL API:**
  - *Eingabe:* Text und Zielsprachen-Code.
  - *Ausgabe:* Übersetzter Text.
  - *Verwendung:* `TranslationBot` über `TranslationService`.

- **Wikipedia API:**
  - *Eingabe:* Suchbegriff.
  - *Ausgabe:* JSON mit Wikipedia-Informationen.
  - *Verwendung:* `WikiBot` über `WikiService`.

### Interne Schnittstellen

- **ChatController zu Bots:**
  - *Methoden:* `processCommand(String command)`, um Benutzereingaben zu verarbeiten.

- **ChatController zu DatabaseManager:**
  - *Methoden:* `saveMessage(Message message)` zum Speichern und `loadMessages(String username)` zum Abrufen von Nachrichten.

- **BotManager zu ChatController:**
  - *Methoden:* `activateBot(int id)` und `deactivateBot(int id)` für die Verwaltung der aktiven Bots.