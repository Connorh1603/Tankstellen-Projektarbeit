package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ListChecker {
    private List<String> cities;

    // Konstruktor, der die Städte-Datei lädt
    public ListChecker(String filePath) {
        this.cities = readCitiesFromCsvFile(filePath);
    }

    // Methode, um die Liste der Städte aus der CSV-Datei zu lesen und zu bereinigen
    private List<String> readCitiesFromCsvFile(String filePath) {
        List<String> cities = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Spalten basierend auf dem Komma trennen
                String[] values = line.split(",");

                for (String value : values) {
                    // Entferne unsichtbare Zeichen und überflüssige Leerzeichen
                    String cleanedValue = value.replaceAll("\\u200B", "").trim();

                    // Normalisiere den String, um Sonderzeichen zu entfernen
                    cleanedValue = Normalizer.normalize(cleanedValue, Normalizer.Form.NFD)
                            .replaceAll("\\p{M}", "");

                    // Stadt zur Liste hinzufügen, wenn sie nicht leer ist
                    if (!cleanedValue.isEmpty()) {
                        cities.add(cleanedValue);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cities;
    }

    // Methode, die nach einer Stadt im Text sucht
    public String findCityInText(String text) {
        for (String city : cities) {
            // Erstelle einen Regex für exakte Wortübereinstimmung
            String regex = "\\b" + Pattern.quote(city) + "\\b";
            if (Pattern.compile(regex).matcher(text).find()) {
                return city; // Rückgabe der gefundenen Stadt
            }
        }
        return null; // Rückgabe von null, wenn keine Stadt gefunden wurde
    }
}
