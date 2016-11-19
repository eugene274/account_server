package server.model.dao;

/**
 * Created by eugene on 11/4/16.
 */
public class DaoException extends Exception {
    public DaoException(Throwable cause) {
        super(cause);
    }

    public DaoException() {
        super();
    }

    public DaoException(String message) {
        super(message);
    }
}
