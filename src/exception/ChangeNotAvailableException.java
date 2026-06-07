package exception;

public class ChangeNotAvailableException extends Exception {
    public ChangeNotAvailableException(String message) { super(message); }
    public ChangeNotAvailableException(String message, Throwable cause) { super(message, cause); }
}
