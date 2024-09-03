Bausteinschicht Whitebox/Blackbox:

In der Bausteinschicht wird die statische Struktur des Chat-Bots durch die Unterscheidung zwischen White-Boxes und Black-Boxes dargestellt. White-Boxes bieten detaillierte Einblicke in die internen Abläufe der Systemkomponenten, während Black-Boxes eine vereinfachte, funktionale Sichtweise bieten. Diese Methode hilft, die Systemarchitektur klarer zu verstehen.

White-Boxen

    Controller-Verzeichnis (White-Box):
        Beschreibung: Dieses Verzeichnis enthält Steuerungskomponenten des Systems, die zentrale Logikfunktionen koordinieren. Es wird als White-Box betrachtet, da es aus mehreren Klassen besteht, die das Verhalten und die Interaktionen im System steuern.
        Enthaltene Black-Box:
            ChatController.class: Diese Klasse steuert die Chat-Interaktionen und vermittelt zwischen verschiedenen Systemkomponenten.

    Interfaces-Verzeichnis (White-Box):
        Beschreibung: Das Interfaces-Verzeichnis definiert Schnittstellen, die die Kommunikationsverträge zwischen verschiedenen Systemkomponenten festlegen. Es ist eine White-Box, da es die Struktur und die Interaktionsmuster des Systems vorgibt.
        Enthaltene Black-Boxen:
            IBot.class: Schnittstelle für die Bot-Komponenten, die deren Funktionalitäten definiert.
            IDatabase.class: Schnittstelle für Datenbankoperationen, die den Zugriff auf und die Verwaltung von Daten regelt.
            IFrontend.class: Schnittstelle für das Frontend, die die Kommunikation mit der Benutzeroberfläche definiert.

    Bots-Verzeichnis (White-Box):
        Beschreibung: Dieses Verzeichnis enthält verschiedene spezialisierte Bot-Klassen, die jeweils spezifische Aufgaben im System erfüllen. Es wird als White-Box klassifiziert, da die interne Struktur und Organisation der Bots für das Systemverständnis wichtig ist.
        Enthaltene Black-Boxen:
            TranslationBot.class: Implementiert einen Bot für Übersetzungsdienste.
            WeatherBot.class: Implementiert einen Bot zur Wetterabfrage.
            WikiBot.class: Implementiert einen Bot zur Abfrage von Wikipedia-Informationen.

Black-Boxen

    Root-Verzeichnis (Black-Box):
        App.class: Diese Hauptklasse der Anwendung dient als Einstiegspunkt. Sie wird als Black-Box betrachtet, da ihre primäre Funktion darin besteht, das System zu initialisieren, ohne dass die internen Abläufe für das Verständnis der Gesamtarchitektur von Bedeutung sind.
