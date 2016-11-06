package server;

import org.junit.Before;
import org.junit.Test;
import server.model.AccountService;
import server.model.dao.UserProfileHibernate;
import server.model.data.Token;
import server.model.customer.CustomerRequestError;
import server.model.customer.CustomerErrors.LoginExistsError;
import server.model.data.UserProfile;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by eugene on 10/10/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public class AccountServiceTest {


    static AccountService accountService = new AccountService();
    static {
        accountService.setDao(new UserProfileHibernate());
    }

    static final String login = "test";
    static final String pass = "testpass";

    @Before
    public void registerSomeUsers() throws CustomerRequestError {
        try {
            accountService.signUp(login,pass);
        } catch (LoginExistsError ignore) {
        }
    }

    @Test
    public void signUp() throws Exception {
        accountService.signUp("eugene222","testpass");
        try {
            accountService.signUp("eugene222","testpass2");
            fail();
        }
        catch(LoginExistsError ignore){
        }

    }

    @Test
    public void signIn() throws Exception {
        Token token1 = accountService.signIn(login,pass);
        Token token2 = accountService.signIn(login,pass);
        assertEquals(token1, token2);
    }

    @Test
    public void logout() throws Exception {
        Token token = accountService.signIn(login,pass);
        assertNotNull(accountService.validateToken(token.toString()));
        accountService.logout(token.toString());
        assertNull(accountService.validateToken(token.toString()));
    }

    @Test
    public void updateName() throws Exception {
        Token token = accountService.signIn(login,pass);
        accountService.updateName(token.toString(),"test2");

        Collection<UserProfile> profiles = accountService.getDao().getWhere("name = 'test2'");
        assertTrue(profiles.contains(new UserProfile(login,pass)));
    }

}