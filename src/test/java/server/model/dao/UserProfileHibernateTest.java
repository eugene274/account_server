package server.model.dao;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.Test;
import server.model.data.UserProfile;

import static org.junit.Assert.*;

/**
 * Created by eugene on 10/22/16.
 */
public class UserProfileHibernateTest extends UserDAOTest {
    @Test
    public void updateName() throws Exception {
        UserProfile user = new UserProfile("testuser","testpass");
        dao.insert(user);
        UserProfile profile = dao.updateName(user.getEmail(), "testname");
        assertEquals(profile.getName(),"testname");
    }

    private static final JdbcDataSource jdbcDataSource;

    static {
        dao = new UserProfileHibernate();

        jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setUrl("./testdb");
        jdbcDataSource.setUser("testuser");
        jdbcDataSource.setPassword("qwerty1");
    }
}