package server.services;

import model.data.Score;
import model.response.ApiErrors.InternalError;
import server.dao.exceptions.DaoException;

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
