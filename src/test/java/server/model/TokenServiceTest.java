package server.model;

import org.junit.Test;
import server.model.dao.TokenDAO;
import server.model.data.Token;
import server.model.data.UserProfile;

import static org.junit.Assert.*;

/**
 * Created by ivan on 07.11.16.
 */
public class TokenServiceTest {

    static TokenService tokenService = new TokenService();
    static TokenDAO dao = tokenService.getDao();

    @Test
    public static void addUserSessionTest() throws Exception {
        UserProfile user = new UserProfile();
        Token token = new Token();

        tokenService.addUserSession(user, token);

        Token token1 = dao.getTokenByTokenString(token.toString());
        assertEquals(token, token1);
    }
}
