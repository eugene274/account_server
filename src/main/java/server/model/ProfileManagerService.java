package server.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.model.customer.CustomerRequestError;
import server.model.customer.InternalError;
import server.model.dao.DaoError;
import server.model.dao.UserDAO;
import server.model.dao.UserProfileHibernate;
import server.model.data.UserProfile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by eugene on 11/5/16.
 */
public class ProfileManagerService {
    private static List<String> mutable = new ArrayList<>();
    private static Logger LOG = LogManager.getLogger("PROFILEMGR");

    private UserDAO dao = new UserProfileHibernate();

    static {
        Class<UserProfile> clazz = UserProfile.class;
        Arrays.asList(clazz.getDeclaredFields()).stream()
                .filter(field -> field.isAnnotationPresent(UserMutable.class))
                .map(field -> field.getName())
                .forEach(s -> mutable.add(s));
        LOG.info("mutable fields " + mutable.toString());
    }

    public void update(String email, String field, String value) throws CustomerRequestError {
        if(!mutable.contains(field)){
            // throw error
        }

        try {
            dao.update(email, field, value);
        } catch (DaoError daoError) {
            throw new InternalError();
        }
    }

    public UserDAO getDao() {
        return dao;
    }
}
