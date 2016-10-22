package server.model.dao;

import server.model.data.UserProfile;
import server.model.dao.UserDAO;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by eugene on 10/18/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public class UserProfileInMemo implements UserDAO {
    private static final ConcurrentHashMap<Long,UserProfile> users = new ConcurrentHashMap<>();
    private static Long nextid = 1L;

    @Override
    public Long insert(UserProfile user) {
        Long id = nextid;
        user.setId(id);

        users.put(id, user);
        nextid++;
        return id;
    }

    @Override
    public UserProfile getById(Long id) {
        return users.get(id);
    }

    @Override
    public UserProfile getByLogin(String login) {
        return users.values().stream().filter(u -> u.getLogin().equals(login)).findFirst().orElse(null);
    }

    @Override
    public UserProfile updateLogin(String login, String newlogin) {
        UserProfile user = getByLogin(login);
        user.setLogin(newlogin);
        return user;
    }
}
