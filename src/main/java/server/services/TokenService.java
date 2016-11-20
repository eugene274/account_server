package server.services;

import model.data.Token;
import model.data.UserProfile;
import org.jetbrains.annotations.TestOnly;
import server.dao.TokenDAO;
import server.dao.TokenHibernate;
import server.dao.exceptions.DaoException;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by eugene on 10/28/16.
 *
 * Deals with usersSignedIn hashmap
 */
public class TokenService {
    private TokenDAO dao = new TokenHibernate();

    protected UserProfile getUserByTokenString(String tokenString){
        return dao.getTokenByTokenString(tokenString).getUser();
    }

    protected void addUserSession(UserProfile user, Token token) throws DaoException {
        token.setUser(user);
        dao.insert(token);
    }

    protected void removeUserSession(String tokenString) throws DaoException {
        dao.removeByTokenString(tokenString);
    }

    protected Collection<UserProfile> users(){
        return dao.getAll().stream().map(Token::getUser)
                .collect(Collectors.toList());
    }

    protected Collection<Token> tokens(){
        return dao.getAll();
    }

    protected Token getTokenByEmail(String email) throws DaoException {
        List<Token> result = dao.getWhere(String.format("user.email = '%s'", email));
        if(result.size() != 1){
            // TODO report
            return null;
        }
        else return result.get(0);
    }

    public Long validateToken(String tokenString){
        try {
            return getUserByTokenString(tokenString).getId();
        }
        catch (NullPointerException e){
            return null;
        }
    }

    @TestOnly
    public TokenDAO getDao() {
        return dao;
    }
}
