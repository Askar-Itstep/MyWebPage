package exception;

public class ConnectionManagerErrorHandler extends RuntimeException {
    public ConnectionManagerErrorHandler() {
        super();
    }

    public ConnectionManagerErrorHandler(String message) {
        super(message);
    }

    public ConnectionManagerErrorHandler(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionManagerErrorHandler(Throwable cause) {
        super(cause);
    }
}
