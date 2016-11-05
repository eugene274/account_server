package server.model.dao;

import server.database.TransactionalError;
import server.model.data.UserProfile;
import server.database.DbHibernate;
import org.hibernate.Session;

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
    public List<UserProfile> getWhere(String... conditions) {
        StringJoiner query = new StringJoiner(" and ", String.format("from %s as %s where ", ENTITY_NAME, ALIAS) ,"");
        for (String condition : conditions){
            query.add(condition);
        }

        return session.createQuery(query.toString(), UserProfile.class)
                .list();
    }

    @Override
    public UserProfile getByEmail(String email) {
//        return session.bySimpleNaturalId(UserProfile.class).load(email);
        try {
            return getWhere(String.format("user.email = '%s'", email)).get(0);
        } catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    @Override
    public void updateName(String email, String newName) throws DaoError {
        try {
            DbHibernate.doTransactional(session, s -> {
                UserProfile profile = getByEmail(email);
                profile.setName(newName);
                s.update(profile);
            });
        } catch (TransactionalError error) {
            throw new DaoError(error);
        }
    }

    public void update(String email, String field, String value) throws DaoError {
        try {
            DbHibernate.doTransactional(session, s -> {
                s.createQuery(String.format("update %s set %s = :value where email = :email", ENTITY_NAME, field)).
                        setParameter("value",value).setParameter("email",email).executeUpdate();
            });
        }
        catch (TransactionalError error){
            throw new DaoError(error);
        }
    }
}
