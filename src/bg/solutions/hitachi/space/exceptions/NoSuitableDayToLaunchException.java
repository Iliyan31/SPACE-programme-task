package bg.solutions.hitachi.space.exceptions;

public class NoSuitableDayToLaunchException extends RuntimeException {
    public NoSuitableDayToLaunchException(String message) {
        super(message);
    }

    public NoSuitableDayToLaunchException(String message, Throwable cause) {
        super(message, cause);
    }
}