## 1.7 Bausteinschicht

## 1.7 Bausteinschicht

## White-Boxen

### View-Verzeichnis (White-Box)
- **Beschreibung**: Dieses Verzeichnis enthält die Komponenten, die für die Benutzeroberfläche und die Interaktion mit dem Benutzer verantwortlich sind. 
- **Enthaltene Black-Boxen**:
  - **ConsoleView.class**: Implementiert die Konsolenanzeige und die Benutzerinteraktion.
  - **FrontendAdapter.class**: Adaptiert die Konsolenansicht für die Verwendung mit dem Frontend-Interface.

### Controller-Verzeichnis (White-Box)
- **Beschreibung**: Dieses Verzeichnis enthält die Steuerungskomponenten des Systems, die zentrale Logikfunktionen koordinieren und die Kommunikation zwischen verschiedenen Systemkomponenten managen.
- **Enthaltene Black-Boxen**:
  - **ChatController.class**: Steuert die Chat-Interaktionen und verwaltet die Kommunikation zwischen Benutzereingaben und Systemreaktionen.

### Persistence-Verzeichnis (White-Box)
- **Beschreibung**: Dieses Verzeichnis enthält Komponenten, die für die Datenpersistenz verantwortlich sind. Dazu gehört die Verwaltung und Speicherung von Daten sowie die Interaktion mit der Datenbank.
- **Enthaltene Black-Boxen**:
  - **Database.class**: Verwaltet die Datenbankoperationen und die Datenpersistenz.
  - **DatabaseAdapter.class**: Adapter für die Datenbank, der die Datenbankoperationen implementiert und an das System anbindet.
  - **SupabaseService.class**: Stellt die Verbindung zur Supabase-Datenbank her.

### Services-Verzeichnis (White-Box)
- **Beschreibung**: Dieses Verzeichnis enthält verschiedene Dienstklassen, die spezifische Funktionen und Schnittstellen für externe APIs und interne Dienste bereitstellen.
- **Enthaltene Black-Boxen**:
  - **CurrentWeatherService.class**: Bietet Wetterdaten von der OpenWeatherMap-API.
  - **WeatherForecastService.class**: Bietet Wettervorhersagedaten von der OpenWeatherMap-API.
  - **WikiService.class**: Stellt Wikipedia-Suchanfragen bereit.
  - **TranslationService.class**: Bietet Textübersetzungsdienste über die DeepL-API.

## Black-Boxen

### Root-Verzeichnis (Black-Box)
- **App.class**: Die Hauptklasse der Anwendung, die als Einstiegspunkt dient. Sie initialisiert das System und startet die Anwendung, ohne dass die internen Abläufe für das Verständnis der Gesamtarchitektur von Bedeutung sind.
- **BotManager.class**: Verwaltet die Bots, ermöglicht deren Registrierung und Aktivierung.
- **IDatabase.class**: Schnittstelle für Datenbankoperationen.
- **IBot.class**: Schnittstelle für Bot-Komponenten.

## Bausteinsicht - Diagramm

```mermaid
classDiagram
    %% White-Boxen
    class View {
        <<White-Box>>
    }
    class ConsoleView {
        +display(String message)
        +readInput(): String
        +run(ChatController controller, String user)
        +displayMessageHistory(List<Message> messageHistory)
    }
    class FrontendAdapter {
        -ConsoleView consoleView
        +FrontendAdapter(ConsoleView consoleView)
        +start(ChatController controller, String user)
        +displayMessage(String message)
        +getUserInput(): String
        +displayMessageHistory(List<Message> messageHistory)
    }
    
    class Controller {
        <<White-Box>>
    }
    class ChatController {
        -IDatabase db
        +initializeBots()
        +processInput(String command, String user)
        +listAvailableBots()
        +activateBot(int botId)
        +deactivateBot(int botId)
        +displayMessageHistory(List<Message> messageHistory)
    }
    
    class Persistence {
        <<White-Box>>
    }
    class Database {
        +authenticateUser(String username, String password): User
        +loadMessages(String username, int limit): List<Message>
    }
    class DatabaseAdapter {
        -Database database
        +DatabaseAdapter(Database database)
        +authenticateUser(String username, String password): User
        +loadMessages(String username, int limit): List<Message>
    }
    class SupabaseService {
        +getDatabaseUrl(): String
        +getApiKey(): String
        +getClient(): OkHttpClient
    }
    
    class Services {
        <<White-Box>>
    }
    class CurrentWeatherService {
        +getWeatherInfo(String city): String
    }
    class WeatherForecastService {
        +getForecastInfo(String city): String
    }
    class WikiService {
        +fetchWikiSummary(String searchTerm): String
    }
    class TranslationService {
        +translate(String text, String targetLang): String
    }
    
    %% Black-Boxen
    class Root {
        <<Black-Box>>
    }
    class App {
        -ChatController controller
        -IFrontend frontend
        +main(String[] args)
    }
    class BotManager {
        +registerBot(int id, IBot bot)
        +activateBot(int id)
        +deactivateBot(int id)
        +getBot(int id): IBot
        +getActiveBots(): Map<Integer, IBot>
        +getAvailableBots(): Map<Integer, IBot>
    }
    class IDatabase {
        +authenticateUser(String username, String password): User
        +saveMessage(Message message, Integer relatedMessageId): int
        +loadMessages(String username, int limit): List<Message>
    }
    class IBot {
        +getName(): String
        +processCommand(String command): String
    }

    %% Beziehungen
    View --> FrontendAdapter
    FrontendAdapter --> ConsoleView

    Controller --> ChatController

    Persistence --> Database
    Persistence --> DatabaseAdapter
    Persistence --> SupabaseService

    Services --> CurrentWeatherService
    Services --> WeatherForecastService
    Services --> WikiService
    Services --> TranslationService

    App --> ChatController
    App --> FrontendAdapter
    App --> BotManager
    App --> IDatabase
    App --> SupabaseService
    BotManager --> IBot
    IDatabase <|-- DatabaseAdapter
    IBot <|-- TranslationBot
    IBot <|-- WeatherBot
    IBot <|-- WikiBot

```
