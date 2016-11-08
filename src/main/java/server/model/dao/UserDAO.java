package server.model.dao;

import org.hibernate.Session;
import org.jetbrains.annotations.TestOnly;
import org.junit.Test;
import server.model.data.UserProfile;

/**
 * Created by eugene on 10/18/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public interface UserDAO extends DAO<UserProfile> {
    UserProfile getByEmail(String login);
    void update(Long id, String field, String value) throws DaoError;

    @TestOnly
    Session getSession();
}
