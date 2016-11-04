package server.model.dao;

import server.database.TransactionalError;
import server.model.data.UserProfile;
import server.database.DbHibernate;
import org.hibernate.Session;

import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by eugene on 10/10/16.
 */

public class UserProfileHibernate implements UserDAO {
    private static String ENTITY_NAME = "Profiles";
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
        StringJoiner query = new StringJoiner(" and ", String.format("from %s as %s where ", ENTITY_NAME, ALIAS) ,"");
        for (String condition : conditions){
            query.add(condition);
        }

        List<UserProfile> result = session.createQuery(query.toString()).list();
        return result;
    }

    @Override
    public UserProfile getByEmail(String email) {
        return session.bySimpleNaturalId(UserProfile.class).load(email);
    }

    @Override
    public UserProfile updateName(String email, String newName) throws DaoError {
        try {
            int result = DbHibernate.getTransactional(session , s ->  s.createQuery("update " + ENTITY_NAME + " set name=:newName where email=:email")
                        .setParameter("newName",newName)
                        .setParameter("email",email)
                        .executeUpdate()
            );

            if(result != 1){
                throw new DaoError();
            }
        } catch (TransactionalError error) {
            throw new DaoError(error);
        }

        return getByEmail(email);
    }
}
