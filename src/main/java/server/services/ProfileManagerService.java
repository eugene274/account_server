package server.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.TestOnly;
import server.dao.UserDAO;
import server.dao.UserProfileHibernate;
import server.dao.exceptions.DaoException;
import server.misc.UserCanChange;
import server.model.data.UserProfile;
import server.model.response.ApiErrors.InternalError;
import server.model.response.ApiErrors.WrongFieldError;
import server.model.response.ApiRequestError;

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

    public ProfileManagerService(Long id) throws ApiRequestError {
        try {
            this.profile = dao.getById(id);
        } catch (DaoException daoException) {
            // TODO report this
            throw new InternalError();
        }

        if(null == profile){
            LOG.warn("ProfileManager initialized with invalid id:" + id.toString());
            throw new InternalError();
        }
    }

    public void update(String field, String value) throws ApiRequestError {
        if(!mutable.contains(field)){
            throw new WrongFieldError(field);
        }

        try {
            dao.update(profile.getId(), field, value);
            LOG.info(String.format("user '%d' change they '%s' to '%s'", profile.getId(),field,value));
        } catch (DaoException daoException) {
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
