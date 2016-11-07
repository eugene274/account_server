package server.database;

/**
 * Created by eugene on 11/7/16.
 */
public class DbError extends Exception {
    public DbError(Throwable cause) {
        super(cause);
    }
}
