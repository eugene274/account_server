package server.dao;

import server.dao.exceptions.DaoException;
import server.model.data.Score;

import java.util.List;

/**
 * Created by eugene on 11/4/16.
 */
public interface ScoreDAO extends DAO<Score> {
    void addPoints(Long id, Integer points) throws DaoException;
    List<Score> getAllOrdered() throws DaoException;
}
