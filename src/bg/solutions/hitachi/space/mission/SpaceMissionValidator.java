package bg.solutions.hitachi.space.mission;

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
}