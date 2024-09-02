## 1.14. Fehlerbehebung

### Typische Fehler und deren Behebung

1. **Fehler:** Der `TranslationBot` liefert keine Übersetzung oder gibt eine Fehlermeldung zurück.
    - **Mögliche Ursachen:**
        - Falscher Sprachcode wird verwendet.
        - DeepL API-Key ist ungültig oder abgelaufen.
        - Netzwerkprobleme verhindern die Kommunikation mit der API.
    - **Lösung:**
        - Überprüfen Sie den Sprachcode und stellen Sie sicher, dass er korrekt ist.
        - Überprüfen Sie den API-Key im `TranslationService` und erneuern Sie ihn bei Bedarf.
        - Überprüfen Sie die Netzwerkverbindung und die API-Verfügbarkeit.

2. **Fehler:** Wetterinformationen werden nicht abgerufen oder sind ungenau.
    - **Mögliche Ursachen:**
        - Stadtname wird nicht erkannt.
        - Die OpenWeatherMap API ist nicht erreichbar oder liefert ungenaue Daten.
    - **Lösung:**
        - Überprüfen Sie die Liste der Städte im `ListChecker` und stellen Sie sicher, dass sie korrekt ist.
        - Überprüfen Sie die API-Verfügbarkeit und verwenden Sie gegebenenfalls eine alternative Quelle.

3. **Fehler:** Der `WikiBot` liefert keine Ergebnisse oder gibt eine leere Antwort zurück.
    - **Mögliche Ursachen:**
        - Der Suchbegriff ist zu spezifisch oder falsch geschrieben.
        - Wikipedia API hat den Suchbegriff nicht gefunden.
    - **Lösung:**
        - Überprüfen Sie den Suchbegriff auf Rechtschreibfehler und führen Sie eine breitere Suche durch.
        - Stellen Sie sicher, dass die API verfügbar ist und richtig angesprochen wird.