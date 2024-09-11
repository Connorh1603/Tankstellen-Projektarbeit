## 1.6 Lösungsstrategie

# Technologieentscheidungen

Für die Entwicklung des Chatbot-Systems haben wir uns für die Verwendung der Programmiersprache Java entschieden, unterstützt durch Standard Java-Bibliotheken und spezifische externe Bibliotheken wie json-20240303.jar für JSON-Verarbeitung, kotlin-stdlib-2.0.20.jar für Kotlin-Interoperabilität, okhttp-4.9.3.jar für HTTP-Anfragen und okio-2.10.0.jar zur effizienten I/O-Operationen. Diese Technologieauswahl unterstützt die Entwicklung durch bewährte Robustheit und eine reichhaltige Ökosystem für Netzwerkkommunikation und Datenverarbeitung.

# Top-Level-Zerlegung des Systems

Das System ist um einen zentralen ChatController herum aufgebaut, der als Hauptsteuereinheit dient. Dieser Controller interagiert mit BotManager und der Datenbank, die wiederum die Bots steuern und Datenverwaltung übernehmen. Die Systemarchitektur ist stark modular mit einer klaren Trennung von Verantwortlichkeiten durch die Verwendung von Interfaces wie IBot, IDatabase, und IFrontend, was die Wartbarkeit und Erweiterbarkeit verbessert.

# Entwurfs- und Architekturmuster

Die Bots implementieren das IBot Interface, was eine einheitliche Interaktionsschnittstelle garantiert und die Integration neuer Bot-Typen vereinfacht. Service-Klassen wie CurrentWeatherService, TranslationService, und WikiService kapseln die API-Zugriffe. Dies stellt sicher, dass Änderungen an externen APIs minimale Auswirkungen auf die Bot-Klassen haben, da nur die Service-Klassen angepasst werden müssen.

# Organisatorische Entscheidungen

Die Entwicklungsarbeit wurde mit Hilfe von GitHub koordiniert, wodurch eine kollaborative und iterative Entwicklungsumgebung ermöglicht wurde. Jedes Teammitglied war für die Entwicklung und Pflege eines spezifischen Bots sowie einieger Systemsegmente verantwortlich, was eine effiziente Aufgabenverteilung und Spezialisierung innerhalb des Projekts ermöglichte.

# Motivation

Die modulare Struktur und die Verwendung von Interfaces unterstützen unsere Ziele der Skalierbarkeit und Wartbarkeit, indem sie es ermöglichen, Teile des Systems unabhängig voneinander zu entwickeln und zu testen. Die Nutzung von Service-Klassen für API-Anbindungen erhöht die Anpassungsfähigkeit des Systems gegenüber Änderungen in externen Diensten.