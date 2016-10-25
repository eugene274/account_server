package server.model.dao;

import server.model.data.UserProfile;

/**
 * Created by eugene on 10/18/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public interface UserDAO extends DAO<UserProfile> {
    UserProfile getByLogin(String login);
    UserProfile updateName(String login, String newlogin);
}
