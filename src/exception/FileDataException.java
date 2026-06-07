package exception;

public class FileDataException extends Exception {
    public FileDataException(String message) { super(message); }
    public FileDataException(String message, Throwable cause) { super(message, cause); }
}
