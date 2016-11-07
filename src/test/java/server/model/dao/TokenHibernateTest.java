package server.model.dao;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import server.model.data.Token;
import server.model.data.UserProfile;

import static org.junit.Assert.*;

/**
 * Created by ivan on 06.11.16.
 */
public class TokenHibernateTest {
    static UserProfile user = new UserProfile("testiuytre","test");
    static UserProfile user2 = new UserProfile("test2ytrew","test");

    @NotNull
    static TokenDAO tokendao;
    @NotNull
    static UserDAO userdao;


    static {
        tokendao = new TokenHibernate();
        userdao = new UserProfileHibernate();

        userdao.insert(user);
        userdao.insert(user2);
    }



    @Test
    public final void insert() throws Exception {
        Long id1 = tokendao.insert(new Token(user));
        Long id2 = tokendao.insert(new Token(user2));
        assertEquals(Long.valueOf(id1 + 1L), id2);

        assertEquals(tokendao.getById(id1).getUser().getEmail(), user.getEmail());
        assertEquals(tokendao.getById(id2).getUser().getEmail(), user2.getEmail());
    }

}
