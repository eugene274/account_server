package server.model.dao;

import server.model.data.UserProfile;
import server.database.DbHibernate;
import org.hibernate.Session;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by eugene on 10/10/16.
 */

public class UserProfileHibernate implements UserDAO {
    private static Session session = DbHibernate.newSession();

    @Override
    public Long insert(UserProfile in) {
        return (Long) session.save(in);
    }

    @Override
    public UserProfile getById(Long id) {
        return session.get(UserProfile.class, id);
    }

    @Override
    public UserProfile getByLogin(String login) {
        return session.bySimpleNaturalId(UserProfile.class).load(login);
    }

    @Override
    public UserProfile updateLogin(String login, String newlogin) {
        throw new NotImplementedException();
    }
}
