package server.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.model.customer.CustomerRequestError;
import server.model.customer.InternalError;
import server.model.customer.WrongFieldError;
import server.model.dao.DaoError;
import server.model.dao.UserDAO;
import server.model.dao.UserProfileHibernate;
import server.model.data.UserProfile;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.lang.reflect.Field;
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

    private static boolean checkField(Field field){
        return field.isAnnotationPresent(UserCanChange.class) &&
                (
                        field.isAnnotationPresent(Basic.class) ||
                                field.isAnnotationPresent(Column.class) && field.getAnnotation(Column.class).updatable()
                        );
    }

    static {
        Class<UserProfile> clazz = UserProfile.class;
        Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> checkField(field))
                .map(Field::getName)
                .forEach(s -> mutable.add(s));
        LOG.info("mutable fields " + mutable.toString());
    }

    public void update(String email, String field, String value) throws CustomerRequestError {
        if(!mutable.contains(field)){
            throw new WrongFieldError(field);
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
