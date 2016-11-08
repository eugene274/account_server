package server.model.services;

import server.model.data.Score;

import java.util.List;

/**
 * Created by eugene on 11/8/16.
 */
public interface LeaderBoardService {
    void register(Long id);
    void remove(Long id);
    List<Score> getLeaders(int N);
    List<Score> getLeaders();
}
