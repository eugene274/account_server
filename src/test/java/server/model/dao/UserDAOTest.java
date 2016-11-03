package server.model.dao;

import org.jetbrains.annotations.NotNull;
import server.model.dao.UserProfileInMemo;
import server.model.data.UserProfile;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by eugene on 10/18/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public abstract class UserDAOTest {
    @NotNull
    static UserDAO dao;

    @Test
    public final void insert() throws Exception {
        Long id1 = dao.insert(new UserProfile("test","pass")) + 1L;
        Long id2 = dao.insert(new UserProfile("test1","pass"));
        assertEquals(id2, id1);
    }

    @Test
    public final void getById() throws Exception {
        UserProfile user = new UserProfile("test2","pass");
        UserProfile user2;

        Long id = dao.insert(user);

        assertNull(dao.getById(-1L));
        assertNotNull(user2 = dao.getById(id));
        assertEquals(user, user2);

        assertEquals(user.getRegistrationDate(), user2.getRegistrationDate());

    }

    @Test
    public final void getByLogin() throws Exception {
        UserProfile user = new UserProfile("test3","pass");
        UserProfile user2;

        dao.insert(user);

        assertNull(dao.getByLogin("nosuchuser"));
        assertNotNull(user2 = dao.getByLogin("test3"));
        assertEquals(user, user2);
    }

    @Test
    public final void getWhere() throws Exception {
        UserProfile user = new UserProfile("test4", "pass");
        Long id = dao.insert(user);

        Collection<UserProfile> profiles = dao.getWhere("user.email = 'test4'");
        assertNotNull(profiles);
        assertTrue(profiles.contains(user));

        profiles = dao.getWhere("user.email = 'test45'");
        assertFalse(profiles.contains(user));
    }

}