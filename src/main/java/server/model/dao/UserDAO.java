package server.model.dao;

import server.model.data.UserProfile;

/**
 * Created by eugene on 10/18/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public interface UserDAO extends DAO<UserProfile> {
    UserProfile getByEmail(String email);

    void updateName(String email, String newName) throws DaoError;
    void update(String email, String field, String value) throws DaoError;
}
