# 1.12 Risiken und Technische Schulden
## Bekannte Risiken

### Externe Abhängigkeiten von REST-APIs
#### Problem: 
Das System verlässt sich stark auf externe REST-APIs (z.B. Wetter-API, Übersetzungs-API, Wikipedia-API). Diese Abhängigkeit birgt das Risiko, dass bei Ausfall oder Änderungen der externen APIs das System nicht mehr korrekt funktioniert.
#### Risiko: 
Wenn eine API nicht verfügbar ist, können die entsprechenden Bots keine Antworten liefern, was zu einem negativen Benutzererlebnis führt.
#### Mitigation: 
Implementierung von Fallback-Mechanismen oder Caching-Strategien für häufige Anfragen, um temporäre API-Ausfälle abzufangen.

### Mangelnde Fehlerbehandlung und Logging
#### Problem: 
In vielen Fällen wenig bis keine Fehlerbehandlung
#### Risiko: 
Ungefangene Ausnahmen können zum Absturz des Systems führen oder dazu, dass unklare Fehlermeldungen an den Benutzer weitergegeben werden.
#### Mitigation: 
Implementierung von umfassendem Exception-Handling und Logging, um Fehler zu erfassen und das System stabiler zu machen.

### Single Point of Failure
#### Problem: 
Das System läuft auf einer einzigen Maschine. Sollte die lokale Maschine ausfallen, wäre das gesamte System nicht verfügbar.
#### Risiko: 
Hardwarefehler, Netzwerkprobleme oder andere technische Schwierigkeiten könnten das gesamte System zum Stillstand bringen.
#### Mitigation: 
Einsatz von redundanter Hardware oder Cloud-Deployments, um Ausfallsicherheit zu gewährleisten.

## Technische Schulden

### Eng gekoppelte Architektur
#### Problem: 
Die Bots und die zugehörigen Services sind stark miteinander gekoppelt, was die Erweiterbarkeit und Wartbarkeit des Systems beeinträchtigen kann.
#### Risiko: 
Änderungen an einem Bot oder Service können unerwartete Auswirkungen auf andere Teile des Systems haben.
#### Mitigation: 
Einführung von loseren Kopplungen durch den Einsatz von Dependency Injection oder durch die Verwendung von Schnittstellen, um die Abhängigkeiten zu verringern.

### Mangelnde Modularität und Wiederverwendbarkeit
#### Problem: 
Einige Komponenten, wie die FrontendAdapter und DatabaseAdapter, sind speziell für eine bestimmte Implementierung ausgelegt, was die Wiederverwendbarkeit in anderen Kontexten einschränkt.
#### Risiko: 
Erhöhte Wartungskosten und Schwierigkeiten bei der Integration neuer Features oder beim Austausch bestehender Implementierungen.
#### Mitigation: 
Refactoring der Adapter, um sie generischer und modularer zu gestalten, damit sie in verschiedenen Kontexten wiederverwendet werden können.

### Fehlende Unit-Tests 
#### Problem: 
Wir haben unseren Code ausgiebig getestet aber keine Tests nach "Hanbuch durchgeführt".
#### Risiko: 
Fehler können in den Produktionscode gelangen, was zu erhöhter Fehleranfälligkeit und längeren Entwicklungszeiten führt.
#### Mitigation: 
Implementierung einer umfassenden Testabdeckung, einschließlich Unit-Tests, Integrationstests und möglicherweise End-to-End-Tests, um die Qualität des Codes sicherzustellen.

## Verletzung bekannter Prinzipien
### Single Responsibility Principle (SRP)
#### Problem: 
Einige Klassen, wie z.B. der BotManager, haben möglicherweise zu viele Verantwortlichkeiten (z.B. die Verwaltung von Bots und die Kommunikation mit dem Controller).
#### Risiko: 
Verstöße gegen das SRP können zu schwer wartbarem Code führen, da eine Änderung an einer Klasse unerwartete Seiteneffekte haben kann.
#### Mitigation: 
Aufteilung der Verantwortlichkeiten auf kleinere, spezialisierte Klassen.

### DRY (Don't Repeat Yourself)
#### Problem: 
Falls Code-Duplizierungen auftreten, beispielsweise bei der Implementierung ähnlicher Logiken in verschiedenen Bots oder Services, wird das DRY-Prinzip verletzt.
#### Risiko: 
Dies führt zu einem erhöhten Wartungsaufwand, da Änderungen an einem Teil des Codes in mehreren Bereichen vorgenommen werden müssen.
#### Mitigation: 
Refactoring des Codes, um wiederverwendbare Komponenten und Methoden zu extrahieren, die in verschiedenen Teilen des Systems genutzt werden können.