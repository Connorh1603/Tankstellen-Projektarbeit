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
      +"botCommands()"
      +listAvailableBots()
      +processInput()
      +getMessageHistory()
      +displayMessageHistory()
    }
    class BotManager {
      +"botCommands()"
      +getBot()
      getActiveBots()
      getAvailableBots()
    }
    class DatabaseManager {
      +registerDatabase()
      +authenticateUser()
      +saveMessage()
      +loadMessages()
      +escapeJson()
      +getMessageIdsAsCsv()
    }
    class SubapaseDatabase {
      +getDatabaseUrl()
      +getApiKey()
  	  +getClient() 
    }
    class ConsoleView {
      +display()
      +readInput()
      +run()
      +displayMessageHistory()
    }
    class FrontendAdapter {
      +start()
      +displayMessage()
      +getUserInput()
      +displayMessageHistory()
    }
    class IFrontend {
      <<interface>>
      +start()
      +displayMessage()
      +getUserInput()
      +displayMessageHistory()
    }
    class IBot {
      <<interface>>
      getName();
      processCommand(String command);
    }
    class WeatherBot {
      +getName()
      +processCommand()
    }
    class WikiBot {
      +getName()
      +processCommand()
    }
    class TranslationBot {
      -handleDirectTranslation()
      +getName()
      +processCommand()
    }
    class CurrentWeatherService {
      +getWeatherInfo()
    }
    class WeatherForecastService {
      +getForecastInfo()
    }
    class TranslationService {
      +getInstance()
    }
    class WikiService {
      +fetchWikiSummary()
    }
    class User {
      +getUsername()
      +validatePassword()
    }
    class Message {
      +getId()
      +setId()
      +getSender()
      +getContent()
      +getTimestamp()
    }

    ChatController "1" --> "*" BotManager : uses
    ChatController "1" --> "1" DatabaseManager : uses
    BotManager "1" --> "*" IBot : manages
    DatabaseManager "1" --> "1" SubapaseDatabase : connects
    FrontendAdapter --|> IFrontend : implements
    ConsoleView --> FrontendAdapter : uses
    IBot <|-- WeatherBot
    IBot <|-- WikiBot
    IBot <|-- TranslationBot
    WeatherBot --> CurrentWeatherService : uses
    WeatherBot --> WeatherForecastService : uses
    WikiBot --> WikiService : uses
    TranslationBot --> TranslationService : uses