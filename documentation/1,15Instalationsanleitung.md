# Schritt-für-Schritt-Anleitung zur Installation der Software
## 1. Systemanforderungen

    Betriebssystem: Windows, macOS, Linux
    Java-Version: Java SE 11 oder höher
    Entwicklungsumgebung (IDE): IntelliJ IDEA, Visual Studio Code, Eclipse oder eine andere Java-IDE
    Git: Für den Zugriff auf das Repository
    Internetverbindung: Zum Herunterladen von Abhängigkeiten und für den Zugriff auf REST-APIs

## 2. Voraussetzungen

Java Development Kit (JDK): Stellen Sie sicher, dass das JDK (Version 11 oder höher) auf Ihrem System installiert ist. Sie können die Installation prüfen, indem Sie den folgenden Befehl in Ihrem Terminal oder in der Eingabeaufforderung ausführen:

    java -version

IDE Installation: Laden Sie eine Java-IDE Ihrer Wahl herunter und installieren Sie diese:
    IntelliJ IDEA
    Visual Studio Code
    Eclipse

Git: Installieren Sie Git, falls es noch nicht installiert ist:
    Git herunterladen

## 3. Erforderliche Bibliotheken

Zusätzlich zu den Standard-Java-Bibliotheken müssen die folgenden .jar-Dateien heruntergeladen und in Ihr Projekt eingebunden werden:

    json-20240303.jar
    kotlin-stdlib-2.0.20.jar
    okhttp-4.9.3.jar
    okio-2.10.0.jar

Diese Bibliotheken können entweder manuell in das Projektverzeichnis kopiert oder über eine Build-Management-Tool wie Maven oder Gradle eingebunden werden.

## 4. Installation und Einrichtung
### Repository klonen

    Öffnen Sie Ihr Terminal oder Ihre Eingabeaufforderung.
    Navigieren Sie zu dem Verzeichnis, in dem Sie das Projekt speichern möchten.
    Klonen Sie das Repository von GitHub:

    bash

git clone https://github.com/IhrRepository/chatbot-basissystem.git

Navigieren Sie in das geklonte Verzeichnis:

bash

    cd chatbot-basissystem

### Projekt in der IDE öffnen

    Öffnen Sie Ihre IDE (z.B. IntelliJ IDEA).
    Wählen Sie "Projekt öffnen" oder "Ordner öffnen" (je nach IDE).
    Navigieren Sie zum geklonten Verzeichnis und wählen Sie es aus.
    Die IDE sollte das Projekt und seine Struktur automatisch erkennen.

### Bibliotheken einbinden

    Manuelles Hinzufügen:
        Kopieren Sie die .jar-Dateien (json-20240303.jar, kotlin-stdlib-2.0.20.jar, okhttp-4.9.3.jar, okio-2.10.0.jar) in das libs-Verzeichnis Ihres Projekts (falls noch kein libs-Verzeichnis vorhanden ist, erstellen Sie es).
        Fügen Sie die .jar-Dateien zum Projekt in Ihrer IDE hinzu:
            In IntelliJ IDEA: Klicken Sie mit der rechten Maustaste auf das Projekt > "Moduleinstellungen" > "Bibliotheken" > "Hinzufügen" > Wählen Sie die .jar-Dateien aus.
            In Visual Studio Code: Verwenden Sie eine Build-Datei wie pom.xml (Maven) oder build.gradle (Gradle) und fügen Sie die entsprechenden Abhängigkeiten hinzu.

    Maven/Gradle (optional):
        Falls Sie Maven oder Gradle verwenden, fügen Sie die Bibliotheken als Abhängigkeiten in Ihre pom.xml (Maven) oder build.gradle (Gradle) Datei hinzu.

### Projekt bauen und ausführen

    Stellen Sie sicher, dass alle Abhängigkeiten korrekt eingebunden sind.
    Bauen Sie das Projekt in der IDE:
        In IntelliJ IDEA: Klicken Sie auf "Build" > "Build Project".
        In Visual Studio Code: Verwenden Sie das integrierte Terminal oder Tasks, um das Projekt zu bauen.
    Führen Sie das Hauptprogramm aus:
        In IntelliJ IDEA: Rechtsklick auf App.java > "Run 'App.main()'".
        In Visual Studio Code: Verwenden Sie den "Run"-Button oder konfigurieren Sie eine launch.json.

## Überprüfung

    Tests durchführen: Führen Sie Unit-Tests durch, um sicherzustellen, dass das System wie erwartet funktioniert.
    Verbindungstests: Stellen Sie sicher, dass die Anwendung erfolgreich mit den externen REST-APIs kommunizieren kann.

## Fehlerbehebung

    Fehlende Abhängigkeiten: Überprüfen Sie, ob alle .jar-Dateien korrekt eingebunden wurden.
    Netzwerkprobleme: Stellen Sie sicher, dass Ihre Internetverbindung stabil ist, um auf die REST-APIs zuzugreifen.
    IDE-Probleme: Stellen Sie sicher, dass Ihre IDE korrekt eingerichtet ist, und dass das JDK richtig konfiguriert ist.