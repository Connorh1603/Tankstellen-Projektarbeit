# 1.9 Infrastruktursicht
## Technische Infrastruktur

Unser Chatbot-System ist als lokale Anwendung konzipiert, die auf einem einzelnen Computer läuft. Die technische Infrastruktur ist daher relativ einfach und fokussiert sich auf die Interaktion mit externen Diensten über REST APIs.

Lokale Anwendung: Die Hauptanwendung, bestehend aus den Java-Programmen und -Bibliotheken (einschließlich json-20240303.jar, kotlin-stdlib-2.0.20.jar, okhttp-4.9.3.jar, und okio-2.10.0.jar), wird lokal auf einem einzelnen Rechner ausgeführt.

## Erweiterte Systemtopologie

### Präsentationsschicht (Frontend Layer)

#### Zweck: 
Diese Schicht ist verantwortlich für die Interaktion mit dem Benutzer und die Darstellung der Ausgabe.
#### Hauptkomponenten:
###### App.java: 
Startpunkt des Programms, der die Anwendung initialisiert und den Ablauf steuert.
######  FrontendAdapter.java: 
Vermittelt zwischen der Logik und der tatsächlichen Benutzeroberfläche.
###### ConsoleView.java: 
Konkrete Implementierung des Frontends, die Benutzereingaben entgegennimmt und Ausgaben anzeigt.
###### IFrontend.java: 
Interface, das die Methoden definiert, die jede Benutzeroberfläche implementieren muss.
```mermaid
classDiagram
    class App {
        -BotManager botManager
        -FrontendAdapter frontend
        +main(String[] args)
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
     class FrontendAdapter {
        -IFrontend frontend
        +FrontendAdapter(IFrontend frontend)
        +displayMessage(String message)
        +getUserInput(): String
    }

    App --> IFrontend
    FrontendAdapter <|-- IFrontend
    FrontendAdapter --> ConsoleView
```
### Anwendungsschicht (Application/Business Logic Layer)

#### Zweck: 
Diese Schicht enthält die Geschäftslogik des Systems und steuert die Interaktionen zwischen verschiedenen Komponenten.
#### Hauptkomponenten:
##### Bots:
###### IBot.java: 
Interface, das die grundlegende Funktionalität eines Bots definiert.
###### TranslationBot.java, WeatherBot.java, WikiBot.java: 
Implementierungen von verschiedenen Chatbots, die spezifische Services nutzen.
##### Controller:
###### ChatController.java: 
Verantwortlich für die Verarbeitung von Nachrichten und das Weiterleiten an den entsprechenden Bot.
##### Manager:
###### BotManager.java:
Verwalten der Registrierung und Auswahl von Chatbots.
```mermaid
classDiagram
    class ChatController{
      -BotManager botManager
      +ChatController(BotManager botManager)
      +processMessage(Message message): String
    }
    class BotManager {
      -List~IBot~ bots
      +registerBot(IBot bot)
      +getBot(String name): IBot
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

    ChatController --> BotManager
    BotManager --> IBot
    IBot <|.. TranslationBot
    IBot <|.. WeatherBot
    IBot <|.. WikiBot
```
### Diensteschicht (Service Layer)

#### Zweck:
Diese Schicht ist verantwortlich für die Kommunikation mit externen Diensten und APIs.
#### Hauptkomponenten:
###### TranslationService.java: 
Schnittstelle zu einem Übersetzungsdienst (REST API).
###### WeatherForecastService.java:
Schnittstelle zu einem Wettervorhersagedienst für Wetter in der Zukunft(REST API).
###### CurrentWeatherService.java: 
Schnittstelle zu einem Wettervorhersagedienst für das Aktuelle Wetter(REST API).
###### WikiService.java: 
Schnittstelle zu einem Wikipedia-API.
###### SupabaseService.java: 
Spezifischer Service für den Datenbankzugriff über die Supabase-API.
```mermaid
classDiagram
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

    class TranslationService {
      +translate(String text, String targetLanguage): String
    }

    class CurrentWeatherService {
      +getWeatherInfo(String location): String
    }

    class WeatherForecastService {
        +getForecast(String location): String
    }

    class WikiService {
        +search(String query): String
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
    class RESTAPIs
    style RESTAPIs fill:#f9f,stroke:#333,stroke-width:4px

    Database --> SupabaseService 
    TranslationBot --> TranslationService
    WeatherBot --> WeatherForecastService
    WeatherBot --> CurrentWeatherService
    WikiBot --> WikiService
    TranslationService --> RESTAPIs
    WeatherForecastService --> RESTAPIs
    SupabaseService --> RESTAPIs
    CurrentWeatherService --> RESTAPIs
    WikiService --> RESTAPIs
```

### Datenhaltungsschicht (Data Access Layer)

#### Zweck: Diese Schicht kümmert sich um die Persistenz von Daten und den Zugriff auf die Datenbank.
#### Hauptkomponenten:
###### IDatabase.java: 
Interface, das die grundlegenden Datenbankoperationen definiert.
###### Database.java: 
Konkrete Implementierung der Datenbanklogik, hält die Nachrichten in einer lokalen Liste.
###### DatabaseAdapter.java: 
Vermittelt zwischen der Anwendung und der konkreten Datenbankimplementierung (z. B. Supabase).
###### SupabaseService.java: 
Service, der eine Verbindung zur Supabase-Datenbank herstellt.
```mermaid
classDiagram
    class ChatController{
      -BotManager botManager
      +ChatController(BotManager botManager)
      +processMessage(Message message): String
    }
    class App {
      -BotManager botManager
      -FrontendAdapter frontend
      +main(String[] args)
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
    class DatabaseAdapter {
      -IDatabase database
      +DatabaseAdapter(IDatabase database)
      +saveMessage(Message message)
      +getMessageHistory(): List~Message~
    }

    ChatController --> DatabaseAdapter : uses
    App --> DatabaseAdapter :uses
    DatabaseAdapter --|> IDatabase
    DatabaseAdapter --> Database
    Database --> SupabaseService
```

Die Schichten Agieren wie folgt miteinander:
```mermaid
classDiagram
    class Anwendungsschicht
    class Datenhaltungsschicht
    class Diensteschicht
    class Präsentationsschicht

    Anwendungsschicht --> Diensteschicht 
    Datenhaltungsschicht --> Diensteschicht 
    Präsentationsschicht --> Anwendungsschicht
    Anwendungsschicht --> Datenhaltungsschicht
    Präsentationsschicht --> Datenhaltungsschicht
```
```mermaid
graph TD
    subgraph "Lokale Maschine"
        direction TB
        A[App] --> C[ChatController]
        C --> B[BotManager]
        A --> D[FrontendAdapter]
        D --> E[ConsoleView]
        B --> F[TranslationBot]
        B --> G[WeatherBot]
        B --> H[WikiBot]
        C --> I[DatabaseAdapter]
        I --> J[Database]
    end

    subgraph "Services"
        direction TB
        F --> K[TranslationService]
        G --> L[WeatherForecastService]
        H --> M[WikiService]
        I --> N[SupabaseService]
    end

    subgraph "Externe REST APIs"
        direction TB
        K --> O[Translation API]
        L --> P[Weather API]
        M --> Q[Wikipedia API]
        N --> R[Supabase API]
    end


```