## 1.8. Laufzeitsichten

Das Verhalten des Systems zur Laufzeit kann durch die folgenden Szenarien beschrieben werden, die die wichtigsten Prozesse abdecken:

### Szenario 1: Übersetzungsanfrage an den TranslationBot
1. **Benutzerinteraktion:** Der Benutzer gibt den Befehl `@translatebot en Hallo` ein, um das Wort "Hallo" ins Englische zu übersetzen.
2. **Eingangsverarbeitung:** Der `ChatController` empfängt die Eingabe und speichert sie als `Message` in der Datenbank über den `DatabaseManager`.
3. **Bot-Aktivierung:** Der `BotManager` hat den `TranslationBot` bereits aktiviert. Der `ChatController` ruft die `processCommand`-Methode des `TranslationBot` auf.
4. **Befehlsverarbeitung:** Der `TranslationBot` analysiert den Befehl, extrahiert den Zielsprachen-Code und den Text, und ruft die `translate`-Methode des `TranslationService` auf.
5. **API-Kommunikation:** Der `TranslationService` kommuniziert mit der DeepL API, um die Übersetzung durchzuführen.
6. **Antwort:** Die übersetzte Nachricht wird vom `TranslationBot` an den `ChatController` zurückgegeben, der sie in der Datenbank speichert und an den Benutzer ausgibt.

### Szenario 2: Wetteranfrage an den WeatherBot
1. **Benutzerinteraktion:** Der Benutzer gibt den Befehl `Wie ist das Wetter in Berlin?` ein.
2. **Eingangsverarbeitung:** Der `ChatController` empfängt den Befehl und speichert ihn in der Datenbank.
3. **Bot-Aktivierung:** Der `BotManager` aktiviert den `WeatherBot`.
4. **Befehlsverarbeitung:** Der `WeatherBot` analysiert den Befehl, erkennt die Anfrage nach aktuellem Wetter und verwendet den `ListChecker`, um die Stadt im Befehl zu extrahieren.
5. **API-Kommunikation:** Der `WeatherBot` verwendet den `CurrentWeatherService`, um aktuelle Wetterdaten von der OpenWeatherMap API abzurufen.
6. **Antwort:** Die Wetterinformationen werden dem Benutzer über den `ChatController` zurückgegeben und in der Datenbank gespeichert.

### Szenario 3: Wikipedia-Abfrage an den WikiBot
1. **Benutzerinteraktion:** Der Benutzer gibt den Befehl `@wiki Berlin` ein, um Informationen über Berlin zu erhalten.
2. **Eingangsverarbeitung:** Der `ChatController` empfängt den Befehl und speichert ihn in der Datenbank.
3. **Bot-Aktivierung:** Der `WikiBot` wird aktiviert.
4. **Befehlsverarbeitung:** Der `WikiBot` analysiert den Befehl, extrahiert den Suchbegriff und kommuniziert mit der Wikipedia API.
5. **API-Kommunikation:** Der `WikiBot` ruft eine Zusammenfassung des Wikipedia-Artikels über die Wikipedia API ab.
6. **Antwort:** Die erhaltene Zusammenfassung wird an den Benutzer zurückgegeben und in der Datenbank gespeichert.