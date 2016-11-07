package server.model.dao;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import server.model.data.Token;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by ivan on 06.11.16.
 */
public class TokenHibernateTest {

    static {
        dao = new TokenHibernate();
    }

    @NotNull
    static TokenDAO dao;

    @Test
    public final void insert() throws Exception {
        Long id1 = dao.insert(new Token()) + 1L;
        Long id2 = dao.insert(new Token());
        assertEquals(id1, id2);
    }

    @Test
    public final void getById() throws Exception {
        Token token = new Token();
        Token token2;

        Long id = dao.insert(token);

        assertNull(dao.getById(-1L));
        assertNotNull(token2 = (Token) dao.getById(id));
        assertEquals(token, token2);
    }

    @Test
    public final void getWhere() throws Exception {
        Token token = new Token(10L);
        dao.insert(token);

        Collection<Token> tokens = dao.getWhere("token.userId = '10'");
        assertNotNull(tokens);
        assertTrue(tokens.contains(token));

        tokens = dao.getWhere("token.userId = '135'");
        assertFalse(tokens.contains(token));
    }

    @Test
    public final void getAll() throws Exception {

        Token token = new Token();
        dao.insert(token);
        Collection<Token> tokens = dao.getAll();
        assertEquals(tokens.size(), 1);
        assertTrue(tokens.contains(token));

        Token token1 = new Token();
        dao.insert(token1);
        tokens = dao.getAll();
        assertEquals(tokens.size(), 2);
        assertTrue(tokens.contains(token1));
        assertTrue(tokens.contains(token));

        Token token2 = new Token();
        dao.insert(token2);
        tokens = dao.getAll();
        assertEquals(tokens.size(), 3);
        assertTrue(tokens.contains(token2));
        assertTrue(tokens.contains(token1));
        assertTrue(tokens.contains(token));
    }

    @Test
    public final void getTokenByTokenString() throws Exception {

        Token token = new Token();
        dao.insert(token);

        String tokenString = token.toString();
        Token token1 = dao.getTokenByTokenString(tokenString);

        assertEquals(token, token1);
    }
}
