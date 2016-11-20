package server.database.executors;

import model.data.Score;
import server.database.Executor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eugene on 11/7/16.
 */

public class ScoreListExecutor implements Executor<List<Score>>{
    @Override
    public List execute(ResultSet resultSet) throws SQLException {
        List<Score> scores = new ArrayList<>();
        while (resultSet.next()){
            scores.add(new Score(resultSet.getLong(1), resultSet.getInt(2)));
        }
        resultSet.close();
        return scores;
    }
}
