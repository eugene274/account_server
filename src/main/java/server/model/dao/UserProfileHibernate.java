package server.model.dao;

import server.database.TransactionalError;
import server.model.data.UserProfile;
import server.database.DbHibernate;
import org.hibernate.Session;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;

/**
 * Created by eugene on 10/10/16.
 */

public class UserProfileHibernate implements UserDAO {
    private Session session = DbHibernate.newSession();

    @Override
    public Long insert(UserProfile in) {
        return (Long) session.save(in);
    }

    @Override
    public UserProfile getById(Long id) {
        return session.get(UserProfile.class, id);
    }

    @Override
    public Collection<UserProfile> getWhere(String... conditions) {
        throw new NotImplementedException();
    }

    @Override
    public UserProfile getByLogin(String login) {
        return session.bySimpleNaturalId(UserProfile.class).load(login);
    }

    @Override
    public UserProfile updateName(String login, String newName) {
        try {
            DbHibernate.doTransactional(session , s -> {
                s.createQuery("update userProfile u set name=:newName where u.login =:login")
                        .setParameter("newName",newName)
                        .setParameter("login",login)
                        .executeUpdate();
            });
        } catch (TransactionalError ignore) {
            // do something!!!!!!
        }

        return getByLogin(newName);
    }
}
