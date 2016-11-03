package server.model.dao;

import org.hibernate.Transaction;
import org.hibernate.jpa.boot.spi.EntityManagerFactoryBuilder;
import org.hibernate.query.Query;
import server.database.TransactionalError;
import server.model.data.UserProfile;
import server.database.DbHibernate;
import org.hibernate.Session;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.jws.soap.SOAPBinding;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by eugene on 10/10/16.
 */

public class UserProfileHibernate implements UserDAO {
    private static String TABLE_NAME = "Profiles";
    private static String ALIAS = "user";

    private Session session = DbHibernate.newSession();

    @Override
    public Long insert(UserProfile in) {
        try {
            return (Long) DbHibernate.getTransactional(session, s -> s.save(in));
        } catch (TransactionalError transactionalError) {
            return -1L;
        }
    }

    @Override
    public UserProfile getById(Long id) {
        return session.get(UserProfile.class, id);
    }

    @Override
    public Collection<UserProfile> getWhere(String... conditions) {
        StringJoiner query = new StringJoiner(" and ", String.format("from %s as %s where ", TABLE_NAME, ALIAS) ,"");
        for (String condition : conditions){
            query.add(condition);
        }

        List<UserProfile> result = session.createQuery(query.toString()).list();
        return result;
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
