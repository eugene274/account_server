package server.model.services;

import server.model.dao.exceptions.DaoException;
import server.model.response.ApiErrors.InternalError;
import server.model.data.Score;

import java.util.List;

/**
 * Created by eugene on 11/8/16.
 */
public interface LeaderBoardService {
    void register(Long id) throws DaoException;
    void remove(Long id) throws InternalError;
    Score getScore(Long id) throws InternalError;
    List<Score> getLeaders(int N) throws InternalError;
    List<Score> getLeaders() throws InternalError;
}
