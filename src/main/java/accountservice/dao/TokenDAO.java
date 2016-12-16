package accountservice.dao;

import accountservice.dao.exceptions.DaoException;
import accountservice.model.data.Token;

import java.util.List;

/**
 * Created by eugene on 11/4/16.
 */
public interface TokenDAO extends DAO<Token> {
    Token getTokenByTokenString(String tokenString);
    void removeByTokenString(String tokenString) throws DaoException;
    List<Token> getAll();
}
