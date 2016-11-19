package server.model.dao;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import server.model.dao.exceptions.EntityExists;
import server.model.data.Score;

import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Created by eugene on 11/7/16.
 */
public class ScoreJDBCTest {
    private static ScoreJDBC scoreDAO = null;

    @BeforeClass
    public static void setUpDAO() throws SQLException {
        scoreDAO = new ScoreJDBC();
    }

    @Before
    public void removeStuff() throws Exception {
        scoreDAO.removeAll();
    }

    @Test
    public void insert() throws Exception {
        Score score = new Score(100L,2);
        scoreDAO.insert(score);
        assertTrue(scoreDAO.getAll().contains(score));

        try {
            scoreDAO.insert(score);
            fail();
        }
        catch (EntityExists e){
        }
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

    @Test
    public void addPoints() throws Exception {

        Integer points = 2;
        Integer addedPoints = 123;
        Long id = 100L;

        Score score = new Score(id, points);

        scoreDAO.insert(score);

        scoreDAO.addPoints(id, addedPoints);
        Integer scoresAfterAdd = scoreDAO.getById(id).getScore();

        assertEquals(Integer.valueOf(addedPoints + points), scoresAfterAdd);
    }

}