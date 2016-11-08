package server.model.services;

import server.model.customer.CustomerErrors.InternalError;
import server.model.data.Score;

import java.util.List;

/**
 * Created by eugene on 11/8/16.
 */
public interface LeaderBoardService {
    void register(Long id) throws InternalError;
    void remove(Long id) throws InternalError;
    List<Score> getLeaders(int N) throws InternalError;
    List<Score> getLeaders() throws InternalError;
}
