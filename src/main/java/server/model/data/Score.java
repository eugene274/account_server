package server.model.data;

public class Score {
    private final Long userId;
    Integer score;

    public Score (Long userId){
        this.userId = userId;
        this.score = 0;
    }

    public int addScore (int amount){
        this.score += amount;
        return getScore();
    }

    public int getScore (){
        return this.score;
    }

    public Long getUserId() {
        return userId;
    }
}
