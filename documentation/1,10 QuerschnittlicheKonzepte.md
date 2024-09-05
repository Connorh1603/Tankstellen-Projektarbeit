## 1.10 Querschnittliche Konzepte

# Übergreifende Prinzipien und Lösungsansätze

## 1. Domänenmodellierung

### Konzepte:
- **Domänenmodelle** bieten abstrahierte Darstellungen wesentlicher Geschäftsobjekte und deren Beziehungen. Diese Modelle sind zentral für die Datenstruktur und -verarbeitung im System.

### Beispiel im Code:
- **Message-Klasse**: Repräsentiert die Entität „Nachricht“ im Chat-System. Attribute wie `id`, `sender`, `content` und `timestamp` speichern zentrale Informationen. Der Konstruktor ermöglicht sowohl die Erstellung neuer Nachrichten als auch die Verwaltung bestehender.
- **User-Klasse**: Stellt einen Systembenutzer mit den Attributen `username` und `password` dar. Die Methode `validatePassword` prüft die Authentizität der Anmeldeinformationen.

### Motivation:
- Eine präzise Modellierung der Geschäftsobjekte sichert konsistente und nachvollziehbare Datenverarbeitung, fördert die Integrität und erleichtert Wartung sowie Erweiterungen des Systems.

## 2. Architekturmuster und -stile

### Konzepte:
- **Schichtenarchitektur**: Diese Architektur gliedert das System in verschiedene Schichten, die jeweils spezifische Verantwortungen haben:
  - **Präsentationsschicht**: Verwalten der Benutzeroberfläche und Interaktion mit dem Benutzer.
  - **Anwendungsschicht**: Koordination der Anwendungslogik und Verwaltung der Interaktionen zwischen Benutzeroberfläche und Persistenz.
  - **Persistenzschicht**: Verwaltung der Datenpersistenz und Datenbankinteraktionen.
  - **Diensteschicht**: Bereitstellung von Funktionen und Schnittstellen für externe APIs und interne Dienste.

### Beispiel im Code:
- **Präsentationsschicht**: `ConsoleView` und `FrontendAdapter` verwalten die Benutzerinteraktion und Verbindung zum Controller.
- **Anwendungsschicht**: `ChatController` koordiniert die Logik für die Chat-Interaktionen und steuert die Kommunikation zwischen den Schichten.
- **Persistenzschicht**: `Database`, `DatabaseAdapter`, und `SupabaseService` kümmern sich um die Speicherung und den Zugriff auf Daten.
- **Diensteschicht**: `CurrentWeatherService`, `WeatherForecastService`, `WikiService`, und `TranslationService` integrieren externe APIs für zusätzliche Funktionen.

### Motivation:
- Die Schichtenarchitektur ermöglicht eine klare Trennung der Verantwortlichkeiten, was die Wartung und Erweiterung des Systems erleichtert. Jede - - Schicht ist unabhängig und kann separat angepasst werden.

## 3. Technologiestack und Integration

### Konzepte:
- **Adapter-Muster**: Wird verwendet, um unterschiedliche Schnittstellen miteinander kompatibel zu machen und die Integration von Komponenten zu erleichtern.
- **RESTful APIs**: Ermöglichen die Integration externer Dienste und erweitern die Funktionalität ohne tiefgreifende interne Implementierungen.

### Beispiel im Code:
- **Adapter-Muster**: `DatabaseAdapter` passt die `IDatabase`-Schnittstelle an die konkrete Datenbankimplementierung an.
- **RESTful APIs**:
  - **WeatherForecastService**: Nutzt die OpenWeatherMap-API zur Wettervorhersage.
  - **TranslationService**: Verwendet die DeepL-API für Übersetzungen.
  - **WikiService**: Greift auf die Wikipedia-API zu, um Seitenzusammenfassungen abzurufen.

### Motivation:
- Die Verwendung von Adaptern und RESTful APIs ermöglicht eine flexible und erweiterbare Systemarchitektur. Dies reduziert Entwicklungsaufwand und fördert die Wiederverwendbarkeit von Komponenten und externen Diensten.

## 4. Sicherheitskonzepte

### Konzepte:
- **Authentifizierung und Autorisierung**: Diese Sicherheitsmaßnahmen gewährleisten den Schutz des Systems und der Daten, indem sie sicherstellen, dass nur autorisierte Benutzer Zugang zu bestimmten Funktionen erhalten.

### Beispiel im Code:
- Die `User`-Klasse bietet Methoden zur Passwortvalidierung, um sicherzustellen, dass nur authentifizierte Benutzer Zugang zu bestimmten Funktionen erhalten.

### Motivation:
- Sicherheitskonzepte schützen das System vor unbefugtem Zugriff und gewährleisten, dass sensible Daten und Funktionen nur von berechtigten Benutzern verwendet werden können.

