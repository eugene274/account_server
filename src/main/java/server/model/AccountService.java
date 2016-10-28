package server.model;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.model.customer.PolicyViolationError;
import server.model.dao.UserDAO;
import server.model.dao.UserProfileHibernate;
import server.model.dao.UserProfileInMemo;
import server.model.customer.CustomerRequestError;
import server.model.customer.LoginExistsError;
import server.model.customer.WrongCredentialsError;
import server.model.data.Token;
import server.model.data.UserProfile;


import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by eugene on 10/9/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public class AccountService extends UsersSignedInService {
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

    public Token signIn( String login, String pass)
            throws CustomerRequestError
    {
        // user's already signed in
        Token token = usersSignedInReverse.search(4, (u, t) -> {
            if(u.checkCredentials(login,pass)) return t;
            else return null;
        });
        if(null != token) return token;

        UserProfile user = dao.getByLogin(login);
        if(null == user || !user.getPassword().equals(pass)) {
            throw new WrongCredentialsError();
        }

        token = new Token();
        addUserSession(user, token);
        LOG.info("'" + login + "' logged in");
        return token;
    }

    public void signUp( String login, String pass)
            throws CustomerRequestError {
        if(null != dao.getByLogin(login)) {
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

    public boolean isTokenValid(String tokenString){
        return tokens().contains(Token.valueOf(tokenString));
    }

    public void logout(String tokenString){
        UserProfile profile = removeUserSession(Token.valueOf(tokenString));
        LOG.info(String.format("'%s' logged out", profile.getLogin()));
    }


    public void updateName(String tokenString, String newName) throws LoginExistsError {
        UserProfile user = getUserByTokenString(tokenString);

        // nothing to do
        if(newName.equals(user.getName())){
            return;
        }

        // update sign-in-array copy
        // violates equality!!!
        dao.updateName(user.getLogin(), newName);
        user.setName(newName);
    }
}
