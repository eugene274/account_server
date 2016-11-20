package server.dao;

import server.dao.exceptions.DaoException;
import server.model.data.UserProfile;

/**
 * Created by eugene on 10/18/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public interface UserDAO extends DAO<UserProfile> {
    UserProfile getByEmail(String login);
    void update(Long id, String field, String value) throws DaoException;
}
