package server.model;

import server.model.data.Score;
import server.model.data.UserProfile;
import server.model.leaderboard.ConnectionConstants;
import server.model.leaderboard.DatabaseStorage;
import server.model.leaderboard.IStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by eugene on 11/4/16.
 */
public class LeaderBoardService extends TokenService {

    private Set<Score> scores = new HashSet<>();
    private IStorage storage;

    public LeaderBoardService (){
        storage = new DatabaseStorage(ConnectionConstants.DRIVER, ConnectionConstants.DB_USER, ConnectionConstants.DB_PASSWD);
        scores = storage.loadScores();
    }

    public boolean registerUser (UserProfile user) {
        Score score = new Score(user.getId(), 0);
        if (isRegisterd(user))
            return false;
        scores.add(score);
        storage.registerUser(String.valueOf(user.getId()));
        return true;
    }

    public boolean isRegisterd (UserProfile userProfile){
        for (Score score : scores)
            if (score.getUserId() == userProfile.getId())
                return true;
        return false;
    }

    public void addScore (UserProfile user, int amount){
        for (Score score : scores){
            if (score.getUserId() == user.getId()) {
                score.addScore(amount);
                storage.update(String.valueOf(user.getId()), amount);
                return;
            }
        }
    }

    public List<Score> getLeaders (int n){
        List<Score> leaders = new ArrayList<>();
        Set<Score> temp = scores;
        for (int i = 0; i < n; i++){
            Score max = null;
            if (temp.size() == 0)
                return leaders;
            for (Score s : temp){
                if (max == null)
                    max = s;
                if (s.getScore() > max.getScore())
                    max = s;
            }
            temp.remove(max);
        }
        return leaders;
    }

    public List<Score> getLeaders (){
        return getLeaders(scores.size());
    }


    // DONE: registerUser(UserProfile)
    // DONE: List<Score> getLeaders(int N)
    // DONE: List<Score> getLeaders()
    // DONE: void addScore(UserProfile user, int increment)
    // TODO: Each score point is new object with ID, Date created, amount = 1, reason
}
