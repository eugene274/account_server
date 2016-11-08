package server.model.services;

import org.jetbrains.annotations.TestOnly;
import server.model.customer.CustomerErrors.InternalError;
import server.model.dao.DaoError;
import server.model.dao.ScoreDAO;
import server.model.dao.ScoreJDBC;
import server.model.data.Score;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * Created by ivan on 08.11.16.
 */
public class LeaderBoardServiceImpl implements LeaderBoardService {

    private ScoreDAO dao;

    public LeaderBoardServiceImpl() throws InternalError {
        try {
            dao = new ScoreJDBC();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InternalError();
        }
    }

    @Override
    public void register(Long id) throws InternalError {
        Score score = new Score(id, 0);

        try {
            dao.insert(score);
        } catch (DaoError daoError) {
            daoError.printStackTrace();
            throw new InternalError();
        }
    }

    @Override
    public void remove(Long id) throws InternalError {
        try {
            dao.remove(id);
        } catch (DaoError daoError) {
            daoError.printStackTrace();
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
        } catch (DaoError daoError) {
            // TODO report this
            daoError.printStackTrace();
            throw new InternalError();
        }
        return leaders;
    }

    @Override
    public Score getScore(Long id) throws InternalError {
        try {
            return dao.getById(id);
        } catch (DaoError daoError) {
            daoError.printStackTrace();
            // TODO report this
            throw new InternalError();
        }
    }

    @TestOnly
    public ScoreDAO getDao() {
        return dao;
    }
}
