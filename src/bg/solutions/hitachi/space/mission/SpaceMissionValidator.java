package bg.solutions.hitachi.space.mission;

import bg.solutions.hitachi.space.exceptions.EmptyWeatherDataFile;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public abstract class SpaceMissionValidator {
    void validateFilePath(String filePath, boolean isGermanSet) {
        if (filePath == null || filePath.isEmpty() || filePath.isBlank()) {
            if (isGermanSet) {
                throw new IllegalArgumentException("Der angegebene Dateipfad darf nicht null, leer oder leer sein!");
            }

            throw new IllegalArgumentException("The given file path cannot be null, empty or blank!");
        }
    }

    void validateSenderEmailAddress(String senderEmailAddress, boolean isGermanSet) {
        if (senderEmailAddress == null || senderEmailAddress.isEmpty() || senderEmailAddress.isBlank()) {
            if (isGermanSet) {
                throw new IllegalArgumentException(
                    "Die angegebene Absender-E-Mail-Adresse darf nicht null, leer oder leer sein!");
            }

            throw new IllegalArgumentException("The given sender email address cannot be null, empty or blank!");
        }
    }

    void validatePassword(String password, boolean isGermanSet) {
        if (password == null || password.isEmpty() || password.isBlank()) {
            if (isGermanSet) {
                throw new IllegalArgumentException("Das angegebene Passwort darf nicht null, leer oder leer sein!");
            }

            throw new IllegalArgumentException("The given password cannot be null, empty or blank!");
        }
    }

    void validateReceiverEmailAddress(String receiverEmailAddress, boolean isGermanSet) {
        if (receiverEmailAddress == null || receiverEmailAddress.isEmpty() || receiverEmailAddress.isBlank()) {
            if (isGermanSet) {
                throw new IllegalArgumentException(
                    "Die angegebene Empfänger-E-Mail-Adresse darf nicht null, leer oder leer sein!");
            }

            throw new IllegalArgumentException("The given receiver email address cannot be null, empty or blank!");
        }
    }

    void validateForecastBuffer(List<String[]> forecastBuffer, int numberOfRows, boolean isGermanSet) {
        if (forecastBuffer.size() != numberOfRows) {
            if (isGermanSet) {
                throw new IllegalArgumentException("Es sind nicht genügend Zeilen vorhanden!");
            }

            throw new IllegalArgumentException("There are not enough rows!");
        }
    }

    void validateForEmptyFile(Reader reader, boolean isGermanSet) {
        if (!checkForNonEmptyWeatherData(reader, isGermanSet)) {
            if (isGermanSet) {
                throw new EmptyWeatherDataFile("Die angegebene Datei enthält keine Wetterdaten!");
            }

            throw new EmptyWeatherDataFile("There is no weather data in the given file!");
        }
    }

    private boolean checkForNonEmptyWeatherData(Reader weatherDataReader, boolean isGermanSet) {
        try {
            return weatherDataReader.ready();
        } catch (IOException e) {
            if (isGermanSet) {
                throw new RuntimeException(
                    "Bei der Suche nach leeren Wetterdatendateien ist ein Problem aufgetreten!", e);
            }

            throw new RuntimeException(
                "There was problem when checking for empty weather data file!", e);
        }
    }

    void validateSplitRows(String[] row, int columnsNumber, boolean isGermanSet) {
        if (row.length != columnsNumber) {
            if (isGermanSet) {
                throw new IllegalArgumentException(
                    "Jede Zeile in der Datei muss die gleiche Spaltennummer haben wie die Kopfzeile!");
            }

            throw new IllegalArgumentException(
                "Every row in the file must have the same columns number as the header row!");
        }
    }

    void validateStringInSplitRow(String[] row, boolean isGermanSet) {
        for (String str : row) {
            if (str == null || str.isEmpty() || str.isBlank()) {
                if (isGermanSet) {
                    throw new IllegalArgumentException("Keiner der Zeilenwerte darf null, leer oder leer sein!");
                }
                throw new IllegalArgumentException("None of the row values can be null, empty or blank!");
            }
        }
    }
}