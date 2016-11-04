package server.database;

/**
 * Created by eugene on 10/22/16.
 */
public class TransactionalError extends Exception {
    public TransactionalError(String message) {
        super(message);
    }

    public TransactionalError(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionalError(RuntimeException e) {
        super(e);
    }
}
