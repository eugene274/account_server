package server.model.dao;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eugene on 10/22/16.
 */
public class UserProfileHibernateTest extends UserDAOTest {
    private static final JdbcDataSource jdbcDataSource;

    static {
        dao = new UserProfileHibernate();

        jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setUrl("./testdb");
        jdbcDataSource.setUser("testuser");
        jdbcDataSource.setPassword("qwerty1");
    }
}