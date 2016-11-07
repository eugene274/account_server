package server.model.data;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by eugene on 10/13/16.
 */
// @SuppressWarnings("DefaultFileTemplate")
@Entity(name = "Tokens")
@Table(name = "tokens")
public class Token {
    @Id
    @Column(name = "token_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @NaturalId
    @Column(name = "token_string")
    private String tokenString;

    @Column(name = "time_create")
    private Calendar createdAt;

    @Column(name = "last_request")
    private Calendar lastRequestAt;

    @Column(name = "active")
    private boolean active = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

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

    public Token(Long userId) {
        this.userId = userId;
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
