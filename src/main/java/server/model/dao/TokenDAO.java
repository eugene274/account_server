package server.model.dao;

import server.model.data.Token;
import server.model.data.UserProfile;

import java.util.List;

/**
 * Created by eugene on 11/4/16.
 */
public interface TokenDAO extends DAO<Token> {
    Token getTokenByTokenString(String tokenString);
    void removeByTokenString(String tokenString) throws DaoError;
    List<Token> getAll();
}
