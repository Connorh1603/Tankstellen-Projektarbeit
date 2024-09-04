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
ChatController.java: 
Steuert die Hauptlogik des Chatbots und koordiniert die Interaktion zwischen den Bots, den Datenbankoperationen und den externen Services.
#### Model:
BotManager.java: 
Verwaltet die verschiedenen Bot-Instanzen und deren Zustände.
User.java, Message.java: 
Definieren die Datenstrukturen, die für die Verwaltung von Benutzerinformationen und Nachrichtendaten benötigt werden.
ListChecker.java: 
Kann verwendet werden, um bestimmte Validierungen oder Überprüfungen auf Datenlisten durchzuführen, die in der Anwendung verwendet werden.


### Datenzugriffsschicht:
Database.java: 
Steuert den Zugriff auf die Datenbank und die Verwaltung von Benutzerdaten und Chat-Historien.
DatabaseAdapter.java:
Dient als vermittler zwischen der Datenzugriffsschicht und der Anwendungsschicht und sorgt für eine Modularität und einfache wartbarkeit von Datenbanken.

### Service- und Integrationsschicht:
SubapaseDatabase.java: 
Implementiert den Zugriff auf eine externe Datenbank, der SupaBase RestAPI um Daten zu speichern und abzurufen.
CurrentWeatherService.java, TranslationService.java, WikiService.java: 
Diese Klassen kapseln die Logik, um mit spezifischen externen APIs zu interagieren und Daten zu beschaffen, die von den Bots benötigt werden, um ihre Dienste bereitzustellen. Diese Schicht behandelt auch die API-Gateways und Sicherheitsaspekte wie API-Schlüsselverwaltung und Rate Limiting.
BotRegistryService.java: 
Kümmert sich um die Befehle, die die Bots verwalten und sorgt für entlastung der App.java.

## Systemarchitektur

Unten ist das UML-Komponentendiagramm, das die Hauptkomponenten unseres Chatbot-Systems zeigt:
```mermaid
classDiagram
    class App {
        -BotManager botManager
        -FrontendAdapter frontend
        +main(String[] args)
    }

    class BotManager {
        -List~IBot~ bots
        +registerBot(IBot bot)
        +getBot(String name): IBot
    }

    class ChatController {
        -BotManager botManager
        +ChatController(BotManager botManager)
        +processMessage(Message message): String
    }

    class IBot {
        <<interface>>
        +processMessage(String message): String
        +getName(): String
    }

    class TranslationBot {
        -TranslationService translationService
        +TranslationBot(TranslationService service)
        +processMessage(String message): String
        +getName(): String
    }

    class WeatherBot {
        -WeatherForecastService weatherService
        +WeatherBot(WeatherForecastService service)
        +processMessage(String message): String
        +getName(): String
    }

    class WikiBot {
        -WikiService wikiService
        +WikiBot(WikiService service)
        +processMessage(String message): String
        +getName(): String
    }

    class Message {
        -String content
        -User sender
        +Message(String content, User sender)
        +getContent(): String
        +getSender(): User
    }

    class User {
        -String name
        +User(String name)
        +getName(): String
    }

    class IFrontend {
        <<interface>>
        +displayMessage(String message)
        +getUserInput(): String
    }

    class ConsoleView {
        +displayMessage(String message)
        +getUserInput(): String
    }

    class IDatabase {
        <<interface>>
        +saveMessage(Message message)
        +getMessageHistory(): List~Message~
    }

    class Database {
        -List~Message~ messages
        +saveMessage(Message message)
        +getMessageHistory(): List~Message~
    }

    class SupabaseService {
        +saveToDatabase(Message message)
        +fetchFromDatabase(): List~Message~
    }

    class FrontendAdapter {
        -IFrontend frontend
        +FrontendAdapter(IFrontend frontend)
        +displayMessage(String message)
        +getUserInput(): String
    }

    class DatabaseAdapter {
        -IDatabase database
        +DatabaseAdapter(IDatabase database)
        +saveMessage(Message message)
        +getMessageHistory(): List~Message~
    }

    class TranslationService {
        +translate(String text, String targetLanguage): String
    }

    class WeatherForecastService {
        +getForecast(String location): String
    }

    class WikiService {
        +search(String query): String
    }

    App --> BotManager
    App --> FrontendAdapter
    BotManager --> IBot
    ChatController --> BotManager
    IBot <|.. TranslationBot
    IBot <|.. WeatherBot
    IBot <|.. WikiBot
    TranslationBot --> TranslationService
    WeatherBot --> WeatherForecastService
    WikiBot --> WikiService
    Message --> User
    FrontendAdapter --> IFrontend
    ConsoleView --> IFrontend
    DatabaseAdapter --> IDatabase
    Database --> IDatabase
    SupabaseService --> IDatabase
