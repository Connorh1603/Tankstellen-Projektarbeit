## 1.14. Fehlerbehebung

### Typische Fehler und deren Behebung

1. **Fehler:** Der `TranslationBot` liefert keine Übersetzung.
   - **Ursachen:** Ungültiger Sprachcode, API-Key abgelaufen oder Netzwerkprobleme.
   - **Lösung:** Überprüfen Sie den Sprachcode, erneuern Sie den API-Key und überprüfen Sie die Netzwerkeinstellungen.

2. **Fehler:** Wetterinformationen werden nicht abgerufen.
   - **Ursachen:** Stadtname nicht erkannt, API nicht verfügbar.
   - **Lösung:** Überprüfen Sie die Städte-Liste im `ListChecker` und die API-Verfügbarkeit.

3. **Fehler:** Der `WikiBot` liefert keine Ergebnisse.
   - **Ursachen:** Suchbegriff zu spezifisch oder falsch geschrieben, Wikipedia API nicht erreichbar.
   - **Lösung:** Suchen Sie breitere Begriffe und überprüfen Sie die API-Verfügbarkeit.