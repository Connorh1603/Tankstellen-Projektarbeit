# Dokumentation des WikiBots

## Kontextabgrenzung

### Fachlicher Kontext

Der WikiBot ist ein Bot innerhalb eines Chat-Systems, der Informationen aus Wikipedia bereitstellt. Er liefert relevante Zusammenfassungen von Wikipedia-Seiten als Antwort auf Benutzeranfragen und ermöglicht es den Benutzern, schnell auf Wissensinhalte zuzugreifen, ohne das Chat-System verlassen zu müssen. Der Bot ist besonders nützlich für allgemeine Wissensfragen oder spezifische Themen, bei denen Wikipedia als vertrauenswürdige Informationsquelle dient.

### Technischer Kontext

Technisch interagiert der WikiBot mit der Wikipedia-API, um Suchanfragen zu stellen und die Antworten zu verarbeiten. Die Implementierung erfolgt in Java und ist in das Gesamt-Chat-System integriert, das auch andere Bots wie den WeatherBot und den TranslationBot umfasst. Der WikiBot ist Teil des `ChatController`, der die Anfragen an den Bot weiterleitet und die Ergebnisse an die Benutzeroberfläche übergibt.

## Lösungsstrategie

**Technologieentscheidungen:**

- **Wikipedia-API**: Die Wikipedia-API (`https://de.wikipedia.org/w/rest.php/v1/search/page`) wird verwendet, um Suchanfragen zu Wikipedia-Seiten zu stellen. Die API liefert strukturierte JSON-Daten, die zur Erstellung von Zusammenfassungen verwendet werden.
- **Java**: Die Implementierung des WikiBots erfolgt in Java, was hohe Kompatibilität und Wiederverwendbarkeit im bestehenden System bietet.

**Top-Level-Zerlegung:**

- **Service-Schicht**: Der WikiBot wird als Service innerhalb des Chat-Systems implementiert und ist verantwortlich für die Kommunikation mit der Wikipedia-API sowie die Verarbeitung der erhaltenen Daten.
- **Integration**: Der WikiBot ist in den `ChatController` integriert, der Anfragen an den Bot weiterleitet und die Antworten an die Benutzeroberfläche zurückgibt.

## Bausteinsicht

**Baustein-Übersicht:**

- **WikiService**: Der zentrale Baustein für die Kommunikation mit der Wikipedia-API.
- **API-Kommunikationsmechanismus**: Beinhaltet die HTTP-Anfragen an die Wikipedia-API und die Verarbeitung der JSON-Antworten.

**Baustein-Details:**

- **WikiService**:
  - **Funktion**: Sendet Anfragen an die Wikipedia-API, empfängt und verarbeitet die Antwort, und gibt eine Zusammenfassung zurück.
  - **Methoden**:
    - `fetchWikiSummary(String searchTerm)`: Führt eine Suchanfrage bei Wikipedia durch und gibt eine Zusammenfassung der gefundenen Seiten zurück.
    - `buildWikiApiUrl(String searchTerm)`: Baut die URL für die API-Anfrage basierend auf dem Suchbegriff.
    - `parseWikiResponse(String jsonResponse)`: Extrahiert und formatiert die relevanten Informationen aus der JSON-Antwort der API.

## Laufzeitsicht

**Typischer Ablauf bei einer Übersetzungsanfrage:**

1. **Benutzeranfrage**: Der Benutzer gibt einen Suchbegriff im Chat-System ein.
2. **Anfrage an den Controller**: Der `ChatController` empfängt die Anfrage und entscheidet, dass der WikiBot die Anfrage bearbeiten soll.
3. **Aufruf des WikiService**: Der `ChatController` ruft den `WikiService` auf, um die Anfrage an die Wikipedia-API weiterzuleiten.
4. **API-Anfrage**: Der `WikiService` baut die API-URL und sendet die Anfrage an die Wikipedia-API.
5. **Verarbeitung der Antwort**: Der `WikiService` empfängt die Antwort, parst die JSON-Daten und extrahiert die Zusammenfassungen.
6. **Antwort an den Controller**: Die Zusammenfassungen werden an den `ChatController` zurückgesendet.
7. **Anzeige der Ergebnisse**: Der `ChatController` gibt die Zusammenfassungen an die Benutzeroberfläche weiter, wo sie dem Benutzer angezeigt werden.

**Wichtige Szenarien:**

- **Erfolgreiche Anfrage**: Der Benutzer erhält eine präzise und gut formatierte Zusammenfassung der Wikipedia-Seite.
- **Keine Ergebnisse gefunden**: Der Bot gibt eine Rückmeldung, dass keine relevanten Seiten gefunden wurden.
- **API-Fehler**: Der Bot behandelt Fehler von der Wikipedia-API (z.B. keine Verbindung, ungültige Antwort) und informiert den Benutzer über ein Problem.

## Schnittstellen

### Externe Schnittstellen

- **Wikipedia-API**: `https://de.wikipedia.org/w/rest.php/v1/search/page?q=searchTerm&limit=3` - Diese API wird verwendet, um Suchanfragen zu stellen und die entsprechenden Daten zu erhalten.

### Interne Schnittstellen

- **`ChatController`**: Der `ChatController` integriert den WikiBot, leitet Benutzeranfragen an den `WikiService` weiter und übergibt die Ergebnisse an die View-Komponente.
- **`FrontendAdapter`**: Diese interne Schnittstelle zur View-Komponent
