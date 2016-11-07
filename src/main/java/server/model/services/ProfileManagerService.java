package server.model.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.TestOnly;
import server.model.UserCanChange;
import server.model.customer.CustomerRequestError;
import server.model.customer.CustomerErrors.InternalError;
import server.model.customer.CustomerErrors.WrongFieldError;
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
        return field.isAnnotationPresent(UserCanChange.class) && (
                        field.isAnnotationPresent(Basic.class) ||
                        field.isAnnotationPresent(Column.class) && field.getAnnotation(Column.class).updatable()
        );
    }

    static {
        Class<UserProfile> clazz = UserProfile.class;
        Arrays.stream(clazz.getDeclaredFields())
                .filter(ProfileManagerService::checkField)
                .map(Field::getName)
                .forEach(s -> mutable.add(s));
        LOG.info("mutable fields " + mutable.toString());
    }

    private UserProfile profile;

    public ProfileManagerService(Long id) throws CustomerRequestError {
        this.profile = dao.getById(id);

        if(null == profile){
            LOG.warn("ProfileManager initialized with invalid id:" + id.toString());
            throw new InternalError();
        }
    }

    public void update(String field, String value) throws CustomerRequestError {
        if(!mutable.contains(field)){
            throw new WrongFieldError(field);
        }

        try {
            dao.update(profile.getId(), field, value);
            LOG.info(String.format("user '%d' change they '%s' to '%s'", profile.getId(),field,value));
        } catch (DaoError daoError) {
            throw new InternalError();
        }
    }

    public UserProfile getProfile() {
        return profile;
    }

    @TestOnly
    public UserDAO getDao() {
        return dao;
    }
}
