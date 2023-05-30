package bg.solutions.hitachi.space.exceptions;

public class EmptyWeatherDataFile extends RuntimeException {
    public EmptyWeatherDataFile(String message) {
        super(message);
    }

    public EmptyWeatherDataFile(String message, Throwable cause) {
        super(message, cause);
    }
}