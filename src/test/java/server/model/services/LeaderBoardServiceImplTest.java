package server.model.services;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import server.model.customer.CustomerErrors.InternalError;
import server.model.data.Score;

import static org.junit.Assert.*;

/**
 * Created by eugene on 11/8/16.
 */
public class LeaderBoardServiceImplTest {
    private static LeaderBoardServiceImpl leaderBoardService = null;

    @BeforeClass
    public static void initBoard() throws InternalError {
        leaderBoardService = new LeaderBoardServiceImpl();
    }


    @Test
    public void register() throws Exception {
        leaderBoardService.register(1444L);
        assertNotNull(leaderBoardService.getDao().getById(1444L));
    }

    @Test
    public void remove() throws Exception {
        leaderBoardService.register(2444L);
        assertNotNull(leaderBoardService.getDao().getById(2444L));
        leaderBoardService.remove(2444L);
        assertNull(leaderBoardService.getDao().getById(2444L));

    }
}