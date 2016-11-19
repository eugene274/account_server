package server.model.services;

import org.junit.BeforeClass;
import org.junit.Test;
import server.database.SessionHolder;
import server.model.response.ApiErrors.WrongFieldError;
import server.model.response.ApiRequestError;
import server.model.dao.exceptions.DaoException;
import server.model.dao.UserDAO;
import server.model.dao.UserProfileHibernate;
import server.model.data.UserProfile;

import static org.junit.Assert.*;

/**
 * Created by eugene on 11/5/16.
 */
public class ProfileManagerServiceTest {
    private static ProfileManagerService service;
    private static UserDAO dao = new UserProfileHibernate();

    private final static String login = "testuser555";
    private final static String passw = "passwwdd";

    @BeforeClass
    public static void setService() throws ApiRequestError {
        Long id = null;
        try {
            id = dao.insert(new UserProfile(login,passw));
        } catch (DaoException ignore) {}
        service = new ProfileManagerService(id);
    }

    @Test
    public void update() throws Exception {

        // updating non-existing field
        try {
            service.update("namefff","qwerty");
            fail();
        }
        catch (WrongFieldError ignore){}

        service.update("name", "qwerty");

        SessionHolder.renew();
        assertEquals(dao.getByEmail(login).getName(), "qwerty");
    }

}