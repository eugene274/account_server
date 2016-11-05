package server.model;

import server.model.data.Score;
import server.model.data.UserProfile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LeaderBoardService extends UsersSignedInService {

    private Set<Score> scores = new HashSet<>();

    /**
     *
     * @param user
     * @return Is user already registered
     *
     */

    public boolean registerUser (UserProfile user){
        Score userScore = new Score(user.getId());
        boolean alreadyRegistered = scores.contains(userScore);

        if (!alreadyRegistered)
            scores.add(userScore);

        return alreadyRegistered;
    }

    /**
     *
     * @param n
     * @return Leaders list of Score
     */

    public List<Score> getLeaders (int n){
        List<Score> leaders = new ArrayList<>();

        if (n > scores.size())
            throw new NullPointerException("There are less leaders than you requested.");

        Set <Score> temp = scores;
        for (int i = 0; i < n; i++) {
            Score t = null;
            for (Score score : temp) {
                if (t == null)
                    t = score;
                if (t.getScore() < score.getScore())
                    t = score;
            }
            leaders.add(t);
            temp.remove(t);
        }
        return leaders;
    }

    /**
     *
     * @return Leaders
     */

    public List<Score> getLeaders (){
        return getLeaders(scores.size());
    }

    /**
     *
     * @param user
     * @param amount
     * @return New user's score
     */

    public int addScore (UserProfile user, int amount){
        Score score = getScoreByUserID(user.getId());
        return score.addScore(amount);
    }

    /**
     *
     * @param userID
     * @return Score by user's ID
     */

    public Score getScoreByUserID (Long userID){
        for (Score score : scores)
            if (score.getUserId() == userID)
                return score;
        return null;
    }

    // TODO: Each score point is new object with ID, Date created, amount = 1, reason


}
