package server.model;

import server.model.customer.CustomerErrors.InternalError;
import server.model.dao.DaoError;
import server.model.dao.TokenDAO;
import server.model.dao.TokenHibernate;
import server.model.data.Token;
import server.model.data.UserProfile;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by eugene on 10/28/16.
 *
 * Deals with usersSignedIn hashmap
 */
public class TokenService {
    private TokenDAO dao = new TokenHibernate();

    protected static final ConcurrentHashMap<Token, UserProfile> usersSignedIn
            = new ConcurrentHashMap<>();
    protected static final ConcurrentHashMap<UserProfile,Token> usersSignedInReverse
            = new ConcurrentHashMap<>();

    protected UserProfile getUserByTokenString(String tokenString){
        return dao.getTokenByTokenString(tokenString).getUser();
    }

    protected void addUserSession(UserProfile user, Token token){
        token.setUser(user);
        dao.insert(token);
    }

    protected void removeUserSession(String tokenString) throws DaoError {
        dao.removeByTokenString(tokenString);
    }

    protected Collection<UserProfile> users(){
        return dao.getAll().stream().map(Token::getUser)
                .collect(Collectors.toList());
    }

    protected Collection<Token> tokens(){
        return dao.getAll();
    }

    protected Token getTokenByEmail(String email){
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
}
