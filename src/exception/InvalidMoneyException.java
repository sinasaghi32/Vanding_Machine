package exception;

public class InvalidMoneyException extends Exception {
    public InvalidMoneyException(String message) { super(message); }
    public InvalidMoneyException(String message, Throwable cause) { super(message, cause); }
}
