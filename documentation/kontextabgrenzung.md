## 1.5. Kontextabgrenzung

### Fachlicher Kontext
Das System ist ein mehrteiliger Chatbot-Manager, der verschiedene spezialisierte Bots verwaltet, die auf spezifische Benutzeranfragen reagieren können. Benutzer können über eine Konsolenoberfläche mit dem System interagieren, indem sie Textnachrichten eingeben, die an bestimmte Bots weitergeleitet werden. Das System wird hauptsächlich zur Bereitstellung von Diensten wie Übersetzungen, Wetterinformationen und Wikipedia-Suchen verwendet.

- **Benutzer:** Menschen, die mit dem System über eine Kommandozeilenschnittstelle (CLI) interagieren.
- **System:** Ein Chatbot-Framework, das die Verwaltung und Kommunikation mit verschiedenen spezialisierten Bots ermöglicht.
- **Bots:** Jeder Bot ist für eine spezifische Aufgabe zuständig, z.B. der `TranslationBot` für Übersetzungen, der `WeatherBot` für Wetterinformationen und der `WikiBot` für Wikipedia-Suchen.

### Technischer Kontext
Das System interagiert mit mehreren externen APIs, um die benötigten Informationen oder Dienste bereitzustellen.

- **OpenWeatherMap API:** Wird vom `WeatherBot` verwendet, um aktuelle Wetterdaten und Wettervorhersagen für bestimmte Städte abzurufen.
- **DeepL API:** Wird vom `TranslationBot` verwendet, um Texte in verschiedene Sprachen zu übersetzen.
- **Wikipedia API:** Wird vom `WikiBot` verwendet, um Zusammenfassungen und Informationen von Wikipedia-Seiten abzurufen.

Das System selbst besteht aus mehreren internen Komponenten, die miteinander kommunizieren, um Benutzereingaben zu verarbeiten und die entsprechenden Ergebnisse zu liefern.

- **ChatController:** Verantwortlich für die Verwaltung der Benutzerinteraktionen und die Weiterleitung von Befehlen an die entsprechenden Bots.
- **BotManager:** Verwaltet die Registrierung und Aktivierung von Bots.
- **DatabaseManager:** Verantwortlich für die Speicherung und das Abrufen von Chat-Nachrichten in einer Datenbank.

### Kommunikationsschnittstellen
- **Benutzer zu System:** Der Benutzer sendet Befehle über eine Konsolenschnittstelle, die vom `ChatController` verarbeitet wird.
- **System zu Bots:** Der `ChatController` leitet die Befehle an die aktivierten Bots weiter.
- **Bots zu Externen APIs:** Die Bots kommunizieren mit den externen APIs, um die benötigten Daten oder Dienste abzurufen.