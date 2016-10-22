package server.model;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.model.dao.UserDAO;
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
public class AccountService {
    private static final ConcurrentHashMap<Token, UserProfile> usersSignedIn
            = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<UserProfile,Token> usersSignedInReverse
            = new ConcurrentHashMap<>();

    private static final AccountService instance = new AccountService();

    private static final Logger LOG = LogManager.getLogger("account");

    public static AccountService getInstance(){
        return instance;
    }

    static {
        LOG.debug("accounting started");
    }

    private final UserDAO dao = new UserProfileInMemo();

    private UserProfile getUserByTokenString(String tokenString){
        return usersSignedIn.get(Token.valueOf(tokenString));
    }

    private Token getTokenByTokenString(String tokenString){
        return usersSignedInReverse.values().stream()
                .filter(token -> token.equals(Token.valueOf(tokenString)))
                .findFirst().orElse(null);
    }

    private static void addUserSession( UserProfile user,  Token token){
        usersSignedIn.putIfAbsent(token, user);
        usersSignedInReverse.putIfAbsent(user,token);
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
        LOG.info("'" + login + "' logged in successfully");
        return token;
    }

    public void signUp( String login, String pass)
            throws CustomerRequestError {
        if(null != dao.getByLogin(login)) {
            LOG.warn("'" + login + "' exists");
            throw new LoginExistsError(login);
        }

        dao.insert(new UserProfile(login,pass));
        LOG.info("'" + login + "' signed up successfully");
    }

    public Collection<UserProfile> getOnlineUsers(){
        return usersSignedIn.values();
    }

    public Collection<Token> getTokens(){ return usersSignedInReverse.values(); }

    public boolean isTokenValid(String tokenString){
        return usersSignedIn.containsKey(Token.valueOf(tokenString));
    }

    public void logout(String tokenString){
        UserProfile remove = usersSignedIn.remove(Token.valueOf(tokenString));
        if(null == remove) {
            return;
        }
        usersSignedInReverse.remove(remove);
        LOG.info("'" + remove.getLogin() + "' logged out");
    }

    public void updateLogin(String tokenString, String newLogin) throws LoginExistsError {
        UserProfile user = getUserByTokenString(tokenString);

        // nothing to do
        if(user.getLogin().equals(newLogin)){
            return;
        }

        // check if new login is ok
        if(null != dao.getByLogin(newLogin)){
            throw new LoginExistsError(newLogin);
        }

        // update sign-in-array copy
        // violates equality!!!
        user.setLogin(newLogin);
        dao.updateLogin(user.getLogin(), newLogin);
    }
}
