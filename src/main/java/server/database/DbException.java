package server.database;

/**
 * Created by eugene on 11/7/16.
 */
class DbException extends RuntimeException {
    public DbException(Throwable cause) {
        super(cause);
    }
}
