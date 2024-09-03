# 1.9 Infrastruktursicht
## Technische Infrastruktur

Unser Chatbot-System ist als lokale Anwendung konzipiert, die auf einem einzelnen Computer oder Server läuft. Die technische Infrastruktur ist daher relativ einfach und fokussiert sich auf die Interaktion mit externen Diensten über REST APIs.

    Lokale Anwendung: Die Hauptanwendung, bestehend aus den Java-Programmen und -Bibliotheken (einschließlich json-20240303.jar, kotlin-stdlib-2.0.20.jar, okhttp-4.9.3.jar, und okio-2.10.0.jar), wird auf einem einzelnen Rechner oder Server ausgeführt.

## Erweiterte Systemtopologie

### Präsentationsschicht:
#### Frontend:
ConsoleView.java: 
Bietet die Benutzeroberfläche, über die Benutzer mit dem Chatbot interagieren können. Diese Schicht nimmt Benutzereingaben entgegen und zeigt die Ergebnisse der Chatbot-Interaktionen an.
FrontendAdapter.java: 
Agiert als Vermittler zwischen der Präsentationsschicht und der Anwendungsschicht, indem es Anfragen weiterleitet und Antworten in einem benutzerfreundlichen Format darstellt.

### Anwendungsschicht:
#### Controller:
ChatController.java: Steuert die Hauptlogik des Chatbots und koordiniert die Interaktion zwischen den Bots, den Datenbankoperationen und den externen Services.
#### Model:
BotManager.java: 
Verwaltet die verschiedenen Bot-Instanzen und deren Zustände.
DatabaseManager.java: 
Steuert den Zugriff auf die Datenbank und die Verwaltung von Benutzerdaten und Chat-Historien.
User.java, Message.java: 
Definieren die Datenstrukturen, die für die Verwaltung von Benutzerinformationen und Nachrichtendaten benötigt werden.

### Datenzugriffsschicht:
SubapaseDatabase.java: 
Implementiert den Zugriff auf eine externe Datenbank, möglicherweise über REST APIs, um persistente Daten zu speichern und abzurufen.
ListChecker.java: 
Kann verwendet werden, um bestimmte Validierungen oder Überprüfungen auf Datenlisten durchzuführen, die in der Anwendung verwendet werden.

### Service- und Integrationsschicht:
CurrentWeatherService.java, TranslationService.java, WikiService.java: 
Diese Klassen kapseln die Logik, um mit spezifischen externen APIs zu interagieren und Daten zu beschaffen, die von den Bots benötigt werden, um ihre Dienste bereitzustellen. Diese Schicht behandelt auch die API-Gateways und Sicherheitsaspekte wie API-Schlüsselverwaltung und Rate Limiting.

## Systemarchitektur

Unten ist das UML-Komponentendiagramm, das die Hauptkomponenten unseres Chatbot-Systems zeigt:

```mermaid
classDiagram
    class ChatController {
      +BotManager botManager
      +DatabaseManager databaseManager
    }
    class BotManager {
      +List bots
    }
    class DatabaseManager {
      +SubapaseDatabase database
    }
    class SubapaseDatabase {
      +connect()
      +disconnect()
    }
    class ConsoleView {
      +displayOutput()
      +collectInput()
    }
    class FrontendAdapter {
      +sendRequest()
      +receiveResponse()
    }
    class IBot {
      <<interface>>
      +sendMessage()
      +receiveMessage()
    }
    class CurrentWeatherService {
      +getWeather()
    }
    class TranslationService {
      +translateText()
    }
    class WikiService {
      +fetchArticle()
    }
    class User {
      +String name
      +String id
    }
    class Message {
      +String text
      +String timestamp
    }

    ChatController "1" --> "*" BotManager : uses
    ChatController "1" --> "1" DatabaseManager : uses
    BotManager "1" --> "*" IBot : manages
    DatabaseManager "1" --> "1" SubapaseDatabase : connects
    ConsoleView "1" --> "1" FrontendAdapter : interacts
    FrontendAdapter "1" --> "1" ChatController : sends commands
    CurrentWeatherService ..> IBot : provides data to
    TranslationService ..> IBot : provides data to
    WikiService ..> IBot : provides data to


Verteilungsdiagramm
graph TD
    node1[Computer]
    subgraph node1
        app[App.jar]
        json[json-20240303.jar]
        kotlin[kotlin-stdlib-2.0.20.jar]
        okhttp[okhttp-4.9.3.jar]
        okio[okio-2.10.0.jar]
    end
    node2[External API Server]
    subgraph node2
        weatherAPI[Weather API]
        translationAPI[Translation API]
        wikiAPI[Wiki API]
    end
    app -->|calls| weatherAPI
    app -->|calls| translationAPI
    app -->|calls| wikiAPI

Im Verteilungsdiagramm für unsere lokale Anwendung würden folgende Elemente dargestellt:

    Node: Ein physischer Computer oder Server, auf dem die Java-Anwendung ausgeführt wird.
    Artefakte: Die Java JAR-Dateien, die auf diesem Computer deployed sind.
    Kommunikation: Die API-Anfragen, die von den Service-Klassen an externe Server über das Internet gesendet werden, um Daten für die Chatbot-Funktionalitäten zu erhalten.

Motivation

Eine einfache, lokale Infrastruktur wurde gewählt, um die Entwicklung und Wartung des Systems zu vereinfachen und die Abhängigkeit von externen Diensten zu minimieren. Dies erleichtert die schnelle Iteration und das Debugging während der Entwicklungsphase. Die detaillierte Schichtung der Systemkomponenten ermöglicht ein besseres Verständnis der Funktionen und Verantwortlichkeiten innerhalb des Systems, was die Modularität und die Wartbarkeit verbessert.
Form

Für die Darstellung der Infrastruktursicht empfehlen wir die Verwendung eines UML-Verteilungsdiagramms, das die lokale Anwendung und die externe Kommunikation klar visualisiert. Dies kann mit einfachen Zeichentools oder UML-Modellierungswerkzeugen erstellt werden.