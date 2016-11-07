package server.model.data;


public class Score {
    private final Long userId;
    private Integer score;

    public Score (Long userID, int score){
        this.score = score;
        this.userId = userID;
    }

    public Long getUserId() {
        return userId;
    }

    public Integer getScore() {
        return score;
    }

    public void addScore (int amount){
        score += amount;
    }
}
