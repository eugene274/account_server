package server.model.services;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.TestOnly;
import server.model.CredentialsPolicy;
import server.model.customer.CustomerErrors.InternalError;
import server.model.customer.CustomerErrors.PolicyViolationError;
import server.model.dao.DaoError;
import server.model.dao.UserDAO;
import server.model.dao.UserProfileHibernate;
import server.model.customer.CustomerRequestError;
import server.model.customer.CustomerErrors.LoginExistsError;
import server.model.customer.CustomerErrors.WrongCredentialsError;
import server.model.dao.exceptions.EntityExists;
import server.model.data.Token;
import server.model.data.UserProfile;


import java.util.Collection;

/**
 * Created by eugene on 10/9/16.
 *
 * Serves all accounting stuff
 * login/logout/register
 */

public class AccountService {
    private static final Logger LOG = LogManager.getLogger("account");

    private UserDAO dao = new UserProfileHibernate();
    private TokenService tokenService = new TokenService();


    @TestOnly
    public UserDAO getDao() {
        return dao;
    }

    /**
     *
     * @param email
     * @param password
     * @return Token
     * @throws CustomerRequestError
     *
     * 1. checks out users already signed in. if present, returns issued token
     * 2. checks out db
     * 3. if ok, issues new Token\
     * 3'. if not ok, throws "Wrong credentials"
     * 4. adds user session and returns token
     */
    public Token signIn( String email, String password)
            throws CustomerRequestError
    {
        // user's already signed in
        Token token = null;
        try {
            token = tokenService.getTokenByEmail(email);
            if(null != token) return token;
            UserProfile user = dao.getByEmail(email);
            if(null == user || !user.getPassword().equals(password)) {
                throw new WrongCredentialsError();
            }
            token = new Token(user);
            tokenService.addUserSession(user, token);
            LOG.info("'" + email + "' logged in");
        } catch (DaoError daoError) {
            LOG.error(daoError.getCause().getMessage());
            throw new InternalError();
        }

        new LeaderBoardServiceImpl().register(dao.getByEmail(email).getId());
        LOG.info("'" + email + "' registered to leader board");

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

        if(!CredentialsPolicy.checkLogin(login) || !CredentialsPolicy.checkPassword(pass)){
            throw new PolicyViolationError();
        }

        try {
            dao.insert(new UserProfile(login,pass));
        }
        catch (EntityExists error){
            throw new LoginExistsError(login);
        }
        catch (DaoError error) {
            LOG.error(error.getCause().getMessage());
            throw new InternalError();
        }
        LOG.info("'" + login + "' signed up");
    }

    public Collection<UserProfile> getOnlineUsers(){
        return tokenService.users();
    }

    public void logout(String tokenString) throws InternalError {
        try {
            new LeaderBoardServiceImpl().remove(tokenService.getUserByTokenString(tokenString).getId());
            tokenService.removeUserSession(tokenString);
        } catch (DaoError daoError) {
            daoError.printStackTrace();
            throw new InternalError();
        }
//        LOG.info(String.format("'%s' logged out", profile.getEmail()));
    }


}
