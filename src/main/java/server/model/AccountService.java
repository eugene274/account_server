package server.model;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.model.customer.CustomerErrors.InternalError;
import server.model.customer.CustomerErrors.PolicyViolationError;
import server.model.dao.DaoError;
import server.model.dao.UserDAO;
import server.model.dao.UserProfileHibernate;
import server.model.customer.CustomerRequestError;
import server.model.customer.CustomerErrors.LoginExistsError;
import server.model.customer.CustomerErrors.WrongCredentialsError;
import server.model.data.Token;
import server.model.data.UserProfile;


import java.util.Collection;

/**
 * Created by eugene on 10/9/16.
 *
 * Serves all accounting stuff
 * login/logout/register
 */

public class AccountService extends TokenService {
    private static final Logger LOG = LogManager.getLogger("account");

    private UserDAO dao = new UserProfileHibernate();

    public AccountService() {
    }

    public UserDAO getDao() {
        return dao;
    }

    public void setDao(UserDAO dao) {
        this.dao = dao;
    }

    /**
     *
     * @param login
     * @param pass
     * @return Token
     * @throws CustomerRequestError
     *
     * 1. checks out users already signed in. if present, returns issued token
     * 2. checks out db
     * 3. if ok, issues new Token\
     * 3'. if not ok, throws "Wrong credentials"
     * 4. adds user session and returns token
     */
    public Token signIn( String login, String pass)
            throws CustomerRequestError
    {
        // user's already signed in
        Token token = usersSignedInReverse.search(4, (u, t) -> {
            if(u.checkCredentials(login,pass)) return t;
            else return null;
        });
        if(null != token) return token;

        UserProfile user = dao.getByEmail(login);
        if(null == user || !user.getPassword().equals(pass)) {
            throw new WrongCredentialsError();
        }

        token = new Token();
        addUserSession(user, token);
        LOG.info("'" + login + "' logged in");
        return token;
    }

    /**
     *
     * @param login
     * @param pass
     * @throws CustomerRequestError
     *
     * 1. checks db for login existence
     * 2. if ok, check policy violation (empty login, forbidden symbols)
     * 2'. if not ok, throws LoginExistsError
     * 2''. if login violates policy, throws PolicyVoilationError
     * 3. add new user to db
     */

    public void signUp( String login, String pass)
            throws CustomerRequestError {
        if(null != dao.getByEmail(login)) {
            LOG.warn("'" + login + "' exists");
            throw new LoginExistsError(login);
        }

        if(!CredentialsPolicy.checkLogin(login) || !CredentialsPolicy.checkPassword(pass)){
            throw new PolicyViolationError();
        }

        dao.insert(new UserProfile(login,pass));
        LOG.info("'" + login + "' signed up");
    }

    public Collection<UserProfile> getOnlineUsers(){
        return users();
    }

    public Collection<Token> getTokens(){ return tokens(); }

    public void logout(String tokenString){
        UserProfile profile = removeUserSession(Token.valueOf(tokenString));
        LOG.info(String.format("'%s' logged out", profile.getEmail()));
    }

}
