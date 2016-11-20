package server.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.TestOnly;
import server.dao.exceptions.DaoException;
import server.dao.exceptions.EntityExists;
import server.dao.exceptions.ScoreDaoException;
import server.database.DbHibernate;
import server.database.JDBCExecutor;
import server.database.executors.ScoreListExecutor;
import server.model.data.Score;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.StringJoiner;

public class ScoreJDBC implements ScoreDAO {
    private static final String TABLE_NAME = "leaderboard";

    private static Logger LOG = LogManager.getLogger(ScoreJDBC.class);

    private static String CREATE_TABLE = null;
    private static String GET_ALL = null;
    private static String INSERT = null;
    private static String DELETE = null;
    private static String UPDATE = null;


    private PreparedStatement createQuery;
    private PreparedStatement getQuery;
    private PreparedStatement insertQuery;
    private PreparedStatement deleteQuery;
    private PreparedStatement updateQuery;

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
        DELETE = "DELETE FROM \"" + TABLE_NAME + "\" WHERE user_id = ?";
        UPDATE = "UPDATE \"" + TABLE_NAME + "\" SET score=score + ? WHERE user_id=?";
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
    public Long insert(Score in) throws DaoException {

//        Long id = in.getUserId();
//        if (getById(id) != null) {
//            remove(id);
//            insert(in);
//            return id;
//        }

        try {
            checkConnection();
            insertQuery.setLong(1,in.getUserId());
            insertQuery.setLong(2,in.getScore());
            JDBCExecutor.doQuery(insertQuery);
            LOG.info("Entity '" + in.getUserId() + "' inserted");
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            LOG.error("SQLState: " + e.getSQLState());
            if(e.getSQLState().equals("23505")) throw new EntityExists();
            throw new ScoreDaoException(e);
        }
        return in.getUserId();
    }

    @Override
    public Score getById(Long id) throws DaoException {
        List<Score> scores = getWhere(String.format("user_id = %d", id));
        try {
            return scores.get(0);
        }
        catch (IndexOutOfBoundsException e){
            return null;
        }

    }

    @Override
    public List<Score> getWhere(String... conditions) throws DaoException {
        StringJoiner selectWhere = new StringJoiner(" AND ", GET_ALL + " WHERE ","");
        for (String condition :
                conditions) {
            selectWhere.add(condition);
        }

        try {
            return JDBCExecutor.getQuery(
                    dbConnection.prepareStatement(selectWhere.toString()),
                    new ScoreListExecutor());
        }
        catch (SQLException e) {
            LOG.error(e.getMessage());
            LOG.error("SQLState: " + e.getSQLState());
            throw new ScoreDaoException(e);
        }
    }

    @Override
    public List<Score> getAll() throws DaoException {
        try {
            return JDBCExecutor.getQuery(getQuery, new ScoreListExecutor());
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            LOG.error("SQLState: " + e.getSQLState());
            throw new ScoreDaoException(e);
        }
    }

    @Override
    public void remove(Score in) throws DaoException {
        throw new NotImplementedException();
    }

    @Override
    public void remove(Long id) throws DaoException {
        try {
            deleteQuery.setLong(1,id);
            JDBCExecutor.doQuery(deleteQuery);
            LOG.info("Entity '" + id.toString() + "' removed");
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            LOG.error("SQLState: " + e.getSQLState());
            throw new ScoreDaoException(e);
        }
    }

    private void checkConnection() throws SQLException {
        if(dbConnection.isClosed()) renewConnection();
    }

    private void renewConnection() throws SQLException {
        LOG.info("Connection renew");
        try {
            dbConnection.commit();
            dbConnection.close();

            createQuery.close();
            getQuery.close();
            insertQuery.close();
            deleteQuery.close();
            updateQuery.close();
        }
        catch (NullPointerException ignore){}

        DbHibernate.newSession().doWork(connection -> {
            dbConnection = connection;
            dbConnection.setAutoCommit(false);
        });

        createQuery = dbConnection.prepareStatement(CREATE_TABLE);
        getQuery = dbConnection.prepareStatement(GET_ALL);
        insertQuery = dbConnection.prepareStatement(INSERT);
        deleteQuery = dbConnection.prepareStatement(DELETE);
        updateQuery = dbConnection.prepareStatement(UPDATE);
    }

    @Override
    public void addPoints(Long id, Integer points) throws DaoException {
        try {
            updateQuery.setLong(1, points);
            updateQuery.setLong(2, id);
            JDBCExecutor.doQuery(updateQuery);
            LOG.info("Entity '" + id.toString() + "' updated");
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            LOG.error("SQLState: " + e.getSQLState());
            throw new ScoreDaoException(e);
        }


    }

    @Override
    public List<Score> getAllOrdered() throws DaoException {
        throw new NotImplementedException();
    }

    @TestOnly
    void removeAll() throws SQLException {
        LOG.debug("Table flushed");
        JDBCExecutor.doQuery(dbConnection.prepareStatement("DELETE FROM \"" + TABLE_NAME + "\""));
    }

    /**
     * Closes current JDBC connection
     * @throws Exception
     */
    public void close() throws Exception {
        throw new NotImplementedException();
    }
}
