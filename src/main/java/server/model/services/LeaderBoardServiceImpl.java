package server.model.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.TestOnly;
import server.model.dao.ScoreDAO;
import server.model.dao.ScoreJDBC;
import server.model.dao.exceptions.DaoException;
import server.model.dao.exceptions.EntityExists;
import server.model.data.Score;
import server.model.response.ApiErrors.InternalError;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * Created by ivan on 08.11.16.
 */
public class LeaderBoardServiceImpl implements LeaderBoardService {

    private ScoreDAO dao;
    private static Logger LOG = LogManager.getLogger(LeaderBoardService.class);

    public LeaderBoardServiceImpl() throws InternalError {
        try {
            dao = new ScoreJDBC();
        } catch (SQLException e) {
            LOG.debug(e.getStackTrace());
            LOG.info(e.getMessage());
            throw new InternalError();
        }
    }

    @Override
    public void register(Long id) throws DaoException {
        Score score = new Score(id, 0);

        try {
            dao.insert(score);
        }
        catch (EntityExists e){
            dao.remove(id);
            dao.insert(score);
        }
    }

    @Override
    public void remove(Long id) throws InternalError {
        try {
            dao.remove(id);
        } catch (DaoException daoException) {
            LOG.info(daoException.getCause().getMessage());
            throw new InternalError();
        }
    }

    @Override
    public List<Score> getLeaders(int N) throws InternalError {
        if(N < 1) return getLeaders();
        return getLeaders().subList(0, N);
    }

    @Override
    public List<Score> getLeaders() throws InternalError {
        List<Score> leaders;
        try {
            leaders = dao.getAll();
            Collections.sort(leaders);
        } catch (DaoException daoException) {
            LOG.info(daoException.getCause().getMessage());
            throw new InternalError();
        }
        return leaders;
    }

    @Override
    public Score getScore(Long id) throws InternalError {
        try {
            return dao.getById(id);
        } catch (DaoException daoException) {
            LOG.debug(daoException.getCause().getStackTrace());
            LOG.info(daoException.getCause().getMessage());
            throw new InternalError();
        }
    }

    @TestOnly
    public ScoreDAO getDao() {
        return dao;
    }
}
