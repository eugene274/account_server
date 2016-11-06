package server.model;

import org.junit.Test;
import server.model.customer.CustomerErrors.WrongFieldError;
import server.model.data.UserProfile;

import static org.junit.Assert.*;

/**
 * Created by eugene on 11/5/16.
 */
public class ProfileManagerServiceTest {
    private ProfileManagerService service =
            new ProfileManagerService();

    @Test
    public void update() throws Exception {
        service.getDao().insert(new UserProfile("testuser5","testpass"));

        // updating non-existing field
        try {
            service.update("testuser5","namefff","qwerty");
            fail();
        }
        catch (WrongFieldError ignore){}

        service.update("testuser5", "name", "qwerty");

        assertEquals(service.getDao().getByEmail("testuser5").getName(), "qwerty");

    }

}