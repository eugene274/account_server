package server.database.executors;

import model.data.Score;
import server.database.Executor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by eugene on 11/7/16.
 */
public class ScoreSingleExecutor implements Executor<Score> {
    @Override
    public Score execute(ResultSet resultSet) throws SQLException {
        resultSet.next();
        return new Score(resultSet.getLong(1), resultSet.getInt(2));
    }
}
