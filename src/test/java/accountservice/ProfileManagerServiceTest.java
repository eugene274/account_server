package accountservice;

import accountservice.dao.UserDAO;
import accountservice.dao.UserProfileHibernate;
import accountservice.dao.exceptions.DaoException;
import accountservice.database.SessionHolder;
import accountservice.model.data.UserProfile;
import accountservice.model.response.ApiErrors.WrongFieldError;
import accountservice.model.response.ApiRequestError;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by eugene on 11/5/16.
 */
public class ProfileManagerServiceTest {
    private static ProfileManagerService service;
    private static final UserDAO dao = new UserProfileHibernate();

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