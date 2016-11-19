package server.model.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import server.database.SessionHolder;
import server.database.TransactionHolder;
import server.model.dao.exceptions.EntityExists;
import server.model.data.UserProfile;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by eugene on 10/10/16.
 */

public class UserProfileHibernate
        implements UserDAO {
    @Override
    public void remove(Long id) {
        throw new NotImplementedException();
    }

    @Override
    public void remove(UserProfile in) throws DaoException {
        throw new NotImplementedException();
    }

    private static String ENTITY_NAME = "Profiles";
    private static String ALIAS = "user";
    private static Logger LOG = LogManager.getLogger(UserProfileHibernate.class);

    private final SessionHolder holder = SessionHolder.getHolder();

    private org.hibernate.query.Query<UserProfile> getWhereQuery(String ...conditions){
        StringJoiner query = new StringJoiner(" and ", String.format("from %s as %s where ", ENTITY_NAME, ALIAS) ,"");
        for (String condition : conditions){
            query.add(condition);
        }

        return holder.getSession().createQuery(query.toString(), UserProfile.class);
    }

    @Override
    public Long insert(UserProfile in) throws DaoException {
        try (TransactionHolder holder = TransactionHolder.getTransactionHolder()) {
            return (Long) holder.getSession().save(in);
        }
        catch (PersistenceException e){
            if(e.getCause() instanceof ConstraintViolationException) throw new EntityExists();
            LOG.error(e.getMessage());
            throw new DaoException(e);
        }
        catch (Exception e) {
            LOG.error(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public UserProfile getById(Long id) {
        return holder.getSession().get(UserProfile.class, id);
    }

    @Override
    public List<UserProfile> getWhere(String... conditions) {
        return getWhereQuery(conditions).list();
    }

    @Override
    public List<UserProfile> getAll() throws DaoException {
        throw new NotImplementedException();
    }

    @Override
    public UserProfile getByEmail(String email) {
        return holder.getSession().byNaturalId(UserProfile.class).using("email",email).loadOptional().orElse(null);
    }

    public void update(Long id, String field, String value) throws DaoException {
        try (TransactionHolder holder = TransactionHolder.getTransactionHolder()) {
            holder.getSession().createQuery(String.format("update versioned %s set %s = :value where id = :id", ENTITY_NAME, field)).
                    setParameter("value",value).
                    setParameter("id",id).
                    executeUpdate();
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new DaoException(e);
        }
    }

}
