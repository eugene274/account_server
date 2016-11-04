package server.model.dao;

import server.model.data.UserProfile;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;
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
    public Collection<UserProfile> getWhere(String... conditions) {
        throw new NotImplementedException();
    }

    @Override
    public UserProfile getByEmail(String login) {
        return users.values().stream().filter(u -> u.getEmail().equals(login)).findFirst().orElse(null);
    }

    @Override
    public UserProfile updateName(String login, String newName) {
        UserProfile user = getByEmail(login);
        user.setName(newName);
        return user;
    }
}
