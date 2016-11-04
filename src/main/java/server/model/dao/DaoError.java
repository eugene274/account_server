package server.model.dao;

/**
 * Created by eugene on 11/4/16.
 */
public class DaoError extends Exception {
    public DaoError(Throwable cause) {
        super(cause);
    }

    public DaoError() {
        super();
    }

    public DaoError(String message) {
        super(message);
    }
}
