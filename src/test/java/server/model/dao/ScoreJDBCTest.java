package server.model.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import server.model.dao.ScoreJDBC;
import server.model.data.Score;

import static org.junit.Assert.*;

/**
 * Created by eugene on 11/7/16.
 */
public class ScoreJDBCTest {



    private static ScoreJDBC scoreDAO = null;

    @Before
    public void setDao() throws Exception {
        scoreDAO = new ScoreJDBC();
    }

    @After
    public void dropDb() throws Exception {
        scoreDAO.drop();
    }

    @Test
    public void insert() throws Exception {
        Score score = new Score(100L,2);
        scoreDAO.insert(score);

        assertTrue(scoreDAO.getAll().contains(score));
    }

    @Test
    public void getAll() throws Exception {
        Score score = new Score(100L,1);
        Score score1 = new Score(200L,2);

        int size = scoreDAO.getAll().size();
        scoreDAO.insert(score);
        scoreDAO.insert(score1);

        assertEquals(scoreDAO.getAll().size(), size + 2);

    }

    @Test
    public void getById() throws Exception {
        Score score = new Score(450L,122);
        scoreDAO.insert(score);

        assertEquals(scoreDAO.getById(450L), score);
        assertNull(scoreDAO.getById(451L));
    }

    @Test
    public void getWhere() throws Exception {

    }

}