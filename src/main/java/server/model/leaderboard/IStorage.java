package server.model.leaderboard;

import server.model.data.Score;

import java.util.Set;

public interface IStorage {

    boolean registerUser (String userName);
    boolean update (String userName, int amount);
    Set<Score> loadScores ();
}
