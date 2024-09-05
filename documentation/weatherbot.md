

# WeatherBot

## Beschreibung
Der Wetterbot ist einer der drei Ersten Bots des Projekts und soll sowohl das Aktuelle Wetter einer Stadt ansagen, als auch das wetter für die nächsten studnen (im 3 Studnen Takt) vorraussagen können. Er reagiert auf fragen nach dem Wetter der Form: "Wie ***wird*** das ***Wetter*** in ***Bielefeld***" und "Wie ***ist*** das ***Wetter*** in ***Bielefeld***".

## Funktionen

### Konstruktor
- Der `WeatherBot`-Konstruktor initialisiert zwei Services:
  - `CurrentWeatherService` für aktuelle Wetterdaten
  - `WeatherForecastService` für Wettervorhersagen.
- Diese Services interagieren mit einer Wetter-API, um die benötigten Daten abzurufen.

### getName() Methode
- Gibt den Namen des Bots, "WeatherBot", zurück.

### processCommand(String command) Methode
- **Initialisierung**: Ein `ListChecker`-Objekt wird erstellt, das eine Liste von Städten aus einer CSV-Datei lädt.
- **Befehlsverarbeitung**:
  - Der Befehl wird in Kleinbuchstaben umgewandelt.
  - Der Bot prüft, ob der Befehl das Schlüsselwort "wetter" enthält.
  - Je nachdem, ob der Befehl "ist" (für aktuelles Wetter) oder "wird" (für Wettervorhersage) enthält, wird der entsprechende Service aufgerufen.
  - Der `ListChecker` sucht nach einem Stadtnamen im Befehl und übergibt diesen an den jeweiligen Service.
  - Die entsprechende Wetter-Service-Klasse ruft die Wetterdaten ab und formatiert sie zur Ausgabe.

## Unterstützende Klassen

### ListChecker
- Lädt und bereinigt Städtenamen aus einer CSV-Datei.
- Bietet eine Methode (`findCityInText`), um in einem Text nach diesen Namen zu suchen. Dies dient dazu, den relevanten Stadtnamen aus dem Benutzerbefehl zu extrahieren.

### CurrentWeatherService
- Stellt eine Methode (`getWeatherInfo`) zur Verfügung, um das aktuelle Wetter für eine bestimmte Stadt zu erfragen.
- Die Methode nutzt eine API, um Echtzeit-Wetterdaten zu erhalten, die sie dann in einem benutzerfreundlichen Format zurückgibt.

### WeatherForecastService
- Funktioniert ähnlich wie `CurrentWeatherService`, ist jedoch auf Wettervorhersagen spezialisiert.
- Ruft Vorhersagedaten ab und verarbeitet sie zu einer detaillierten Wettervorhersage.

## Zusätzliche Anmerkungen
- Der `WeatherBot` und die dazugehörigen Services sind abhängig von der Verfügbarkeit und den Antworten der Wetter-API.
- Fehlerbehandlungsmechanismen sind implementiert, um auf API-Ausfälle oder ungültige Antworten zu reagieren. Dies erhöht die Zuverlässigkeit des Bots und stellt sicher, dass dem Benutzer nützliche Fehlermeldungen bereitgestellt werden.