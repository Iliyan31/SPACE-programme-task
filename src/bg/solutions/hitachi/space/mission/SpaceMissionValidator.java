package bg.solutions.hitachi.space.mission;

public abstract class SpaceMissionValidator {
    void validateFilePath(String filePath) {
        if (filePath == null || filePath.isEmpty() || filePath.isBlank()) {
            throw new IllegalArgumentException("The given file path cannot be null, empty or blank!");
        }
    }

    void validateSenderEmailAddress(String senderEmailAddress) {
        if (senderEmailAddress == null || senderEmailAddress.isEmpty() || senderEmailAddress.isBlank()) {
            throw new IllegalArgumentException("The given sender email address cannot be null, empty or blank!");
        }
    }

    void validatePassword(String password) {
        if (password == null || password.isEmpty() || password.isBlank()) {
            throw new IllegalArgumentException("The given password cannot be null, empty or blank!");
        }
    }

    void validateReceiverEmailAddress(String receiverEmailAddress) {
        if (receiverEmailAddress == null || receiverEmailAddress.isEmpty() || receiverEmailAddress.isBlank()) {
            throw new IllegalArgumentException("The given receiver email address cannot be null, empty or blank!");
        }
    }
}