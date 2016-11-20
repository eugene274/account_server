package server.services;

import org.junit.Before;
import org.junit.Test;
import server.database.SessionHolder;
import server.model.data.Token;
import server.model.response.ApiErrors.LoginExistsError;
import server.model.response.ApiRequestError;

import static org.junit.Assert.*;

/**
 * Created by eugene on 10/10/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public class AccountServiceTest {
    static AccountService accountService = new AccountService();
    static TokenService tokenService = new TokenService();

    static final String login = "test";
    static final String pass = "testpass";

    @Before
    public void registerSomeUsers() throws ApiRequestError {
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
        assertNotNull(tokenService.validateToken(token.toString()));
        accountService.logout(token.toString());

        SessionHolder.renew();
        assertNull(tokenService.validateToken(token.toString()));
    }

}