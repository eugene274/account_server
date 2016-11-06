package server.model;

import org.junit.BeforeClass;
import org.junit.Test;
import server.model.customer.CustomerErrors.WrongFieldError;
import server.model.customer.CustomerRequestError;
import server.model.dao.UserDAO;
import server.model.dao.UserProfileHibernate;
import server.model.data.UserProfile;
import server.model.services.ProfileManagerService;

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
    public static void setService() throws CustomerRequestError {
        Long id = dao.insert(new UserProfile(login,passw));
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

        assertEquals(dao.getByEmail(login).getName(), "qwerty");
    }

}