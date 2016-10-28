package server;

import org.junit.Before;
import org.junit.Test;
import server.model.AccountService;
import server.model.dao.UserProfileHibernate;
import server.model.data.Token;
import server.model.customer.CustomerRequestError;
import server.model.customer.LoginExistsError;
import server.model.customer.WrongCredentialsError;

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
        assertTrue(accountService.isTokenValid(token.toString()));
        accountService.logout(token.toString());
        assertFalse(accountService.isTokenValid(token.toString()));
    }

    @Test
    public void updateName() throws Exception {
        Token token = accountService.signIn(login,pass);
        accountService.updateName(token.toString(),"test2");
        assertTrue(
                accountService.getOnlineUsers().stream().
                        filter(userProfile -> "test2".equals(userProfile.getName())).
                        count() > 0L );
    }

}