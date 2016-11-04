package server.model.data;

import java.util.Calendar;
import java.util.Random;

/**
 * Created by eugene on 10/13/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public class Token {
    private String tokenString;
    private Calendar createdAt;
    private Calendar lastRequestAt;
    private boolean active = true;

    // TODO:
    // @Embedded
    UserProfile user;


    private static Random generator = new Random();

    private static String generateToken(){
        return ((Long) generator.nextLong()).toString();
    }


    public static Token valueOf(String tokenString){
        return new Token(tokenString);
    }


    public Token() {
        this.tokenString = generateToken();
        this.createdAt = Calendar.getInstance();
        this.lastRequestAt = createdAt;
    }

    private Token(String tokenString) {
        this.tokenString = tokenString;
    }

    public Calendar getCreatedAt() {
        return createdAt;
    }

    public Calendar getLastRequestAt() {
        return lastRequestAt;
    }

    public void setLastRequestAt(Calendar lastRequestAt) {
        this.lastRequestAt = lastRequestAt;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token1 = (Token) o;

        return toString().equals(token1.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String toString() {
        return tokenString;
    }
}
