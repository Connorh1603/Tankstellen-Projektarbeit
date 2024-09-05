## 1.5. Kontextabgrenzung

### Fachlicher Kontext
Das System ist ein Chatbot-Manager, der verschiedene spezialisierte Bots verwaltet, welche auf Benutzeranfragen reagieren. Benutzer können über eine Konsolenoberfläche mit dem System interagieren, indem sie Textnachrichten eingeben, die an die Bots weitergeleitet werden. Hauptsächlich bietet das System Dienste wie Übersetzungen, Wetterinformationen und Wikipedia-Suchen an.

- **Benutzer:** Personen, die mit dem System über die Kommandozeilen-Schnittstelle interagieren.
- **System:** Ein Chatbot-Framework, das die Verwaltung und Kommunikation mit spezialisierten Bots ermöglicht.
- **Bots:** Spezialisiert auf bestimmte Aufgaben: `TranslationBot` (Übersetzungen), `WeatherBot` (Wetterinformationen), `WikiBot` (Wikipedia-Suchen).

### Technischer Kontext
Das System interagiert mit mehreren externen APIs, um Informationen oder Dienste bereitzustellen.

- **OpenWeatherMap API:** Wird vom `WeatherBot` für aktuelle Wetterdaten und Vorhersagen genutzt.
- **DeepL API:** Wird vom `TranslationBot` zur Durchführung von Übersetzungen verwendet.
- **Wikipedia API:** Wird vom `WikiBot` verwendet, um Informationen aus Wikipedia abzurufen.

Das System umfasst interne Komponenten, die Benutzereingaben verarbeiten und Ergebnisse generieren.

- **ChatController:** Verwaltet Benutzerinteraktionen und leitet Befehle an die Bots weiter.
- **BotManager:** Verwaltet die Registrierung und Aktivierung von Bots.
- **DatabaseManager:** Speichert und ruft Chatnachrichten in einer Datenbank ab.

### Kommunikationsschnittstellen
- **Benutzer zu System:** Eingabe über die Konsole, verarbeitet durch den `ChatController`.
- **System zu Bots:** Befehle werden an aktivierte Bots weitergeleitet.
- **Bots zu Externen APIs:** Bots kommunizieren mit externen APIs, um die benötigten Daten oder Dienste