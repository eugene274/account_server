package server.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by eugene on 11/7/16.
 */
public class JDBCExecutor {

    public static void doQuery(PreparedStatement statement) throws SQLException {
        try {
            statement.executeUpdate();
            statement.getConnection().commit();
        } catch (SQLException e){
            statement.getConnection().rollback();
            throw e;
        }
    }

    public static <T> T getQuery(PreparedStatement statement, Executor<T> operation) throws SQLException {
        ResultSet resultSet = statement.executeQuery();
        return operation.execute(resultSet);
    }
}
