package server.model.dao;

import org.h2.jdbcx.JdbcDataSource;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import server.model.data.UserProfile;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by eugene on 10/22/16.
 */
public class UserProfileHibernateTest  {


    static {
        dao = new UserProfileHibernate();
    }

    @NotNull
    static UserDAO dao;

    @Test
    public final void insert() throws Exception {
        Long id1 = dao.insert(new UserProfile("test7654","pass")) + 1L;
        Long id2 = dao.insert(new UserProfile("test1trew","pass"));
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
    public final void getByEmail() throws Exception {
        UserProfile user = new UserProfile("test3","pass");
        UserProfile user2;

        dao.insert(user);

        assertNull(dao.getByEmail("nosuchuser"));
        assertNotNull(user2 = dao.getByEmail("test3"));
        assertEquals(user, user2);
    }

    @Test
    public final void getWhere() throws Exception {
        UserProfile user = new UserProfile("test4", "pass");
        dao.insert(user);

        Collection<UserProfile> profiles = dao.getWhere("user.email = 'test4'");
        assertNotNull(profiles);
        assertTrue(profiles.contains(user));

        profiles = dao.getWhere("user.email = 'test45'");
        assertFalse(profiles.contains(user));
    }

    @Test
    public void updateName() throws Exception {
        UserProfile user = new UserProfile("testuser", "testpass");
        dao.insert(user);
        dao.updateName("testuser","testname");
        assertEquals(dao.getByEmail("testuser").getName(),"testname");
    }
}