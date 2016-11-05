package server.model;

import org.junit.Test;
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
        service.update("testuser5","namefff","qwerty");

    }

}