package server.model.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.TestOnly;
import server.database.DbError;
import server.database.DbHibernate;
import server.database.JDBCExecutor;
import server.database.executors.ScoreListExecutor;
import server.model.dao.DaoError;
import server.model.dao.ScoreDAO;
import server.model.data.Score;

import java.sql.*;
import java.util.*;

public class ScoreJDBC implements ScoreDAO {
    private static final String TABLE_NAME = "leaderboard";

    private static Logger LOG = LogManager.getLogger("SCOREDAO");

    private static String CREATE_TABLE = null;
    private static String GET_ALL = null;
    private static String GET_WHERE = null;
    private static String INSERT = null;

    private PreparedStatement createQuery;
    private PreparedStatement getQuery;
    private PreparedStatement insertQuery;


    static {
        CREATE_TABLE = "CREATE TABLE IF NOT EXISTS \"" + TABLE_NAME + "\"" +
                 "(\n" +
                "   user_id bigint NOT NULL, \n" +
                "   score int NOT NULL DEFAULT 0, \n" +
                "   CONSTRAINT user_id PRIMARY KEY (user_id), \n" +
                "   CONSTRAINT unique_user_id UNIQUE (user_id)\n" +
                ")";

        GET_ALL = "SELECT user_id, score FROM \"" + TABLE_NAME + "\"";
        INSERT = "INSERT INTO \"" + TABLE_NAME + "\" (user_id, score) VALUES (?, ?)";

    }

    private Connection dbConnection;

    private void init() throws SQLException {
        JDBCExecutor.doQuery(createQuery);
    }

    public ScoreJDBC() throws SQLException {
        renewConnection();
        init();
    }


    @Override
    public Long insert(Score in) throws DaoError {
        try {
            insertQuery.setLong(1,in.getUserId());
            insertQuery.setLong(2,in.getScore());
            JDBCExecutor.doQuery(insertQuery);
        } catch (SQLException e) {
            throw new DaoError(e);
        }
        return in.getUserId();
    }

    @Override
    public Score getById(Long id) throws DaoError {
        List<Score> scores = getWhere(String.format("user_id = %d", id));
        try {
            return scores.get(0);
        }
        catch (IndexOutOfBoundsException e){
            return null;
        }

    }

    @Override
    public List<Score> getWhere(String... conditions) throws DaoError {
        StringJoiner selectWhere = new StringJoiner(" AND ", GET_ALL + " WHERE ","");
        for (String condition :
                conditions) {
            selectWhere.add(condition);
        }

        try {
            return JDBCExecutor.getQuery(
                    dbConnection.prepareStatement(selectWhere.toString()),
                    new ScoreListExecutor());
        } catch (SQLException | DbError e) {
            throw new DaoError(e);
        }
    }

    @Override
    public List<Score> getAll() throws DaoError {
        try {
            return JDBCExecutor.getQuery(getQuery, new ScoreListExecutor());
        } catch (DbError | SQLException dbError) {
            throw new DaoError(dbError);
        }
    }

    private void checkConnection() throws SQLException {
        if(dbConnection.isClosed()) renewConnection();
    }

    private void renewConnection() throws SQLException {
        try {
            dbConnection.commit();
            dbConnection.close();

            createQuery.close();
            getQuery.close();
            insertQuery.close();
        }
        catch (NullPointerException ignore){}

        DbHibernate.newSession().doWork(connection -> {
            dbConnection = connection;
        });

        createQuery = dbConnection.prepareStatement(CREATE_TABLE);
        getQuery = dbConnection.prepareStatement(GET_ALL);
        insertQuery = dbConnection.prepareStatement(INSERT);
    }

    @TestOnly
    public void drop() throws SQLException {
        JDBCExecutor.doQuery(dbConnection.prepareStatement("DROP TABLE \"" + TABLE_NAME + "\""));
    }
}
