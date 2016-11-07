package server.model.leaderboard;

import server.model.data.Score;

import java.sql.*;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class DatabaseStorage implements IStorage{
    private final String driver;
    private final String db_user;
    private final String db_passwd;

    private Connection db;

    public DatabaseStorage (String driver, String db_user, String db_passwd){
        this.driver = driver;
        this.db_user = db_user;
        this.db_passwd = db_passwd;
    }

    private void init (){
        try {
            if (driver.contains("sqlite")) {
                Class.forName("org.sqlite.JDBC");
            } else if (driver.contains("mysql")) {
                Class.forName("com.mysql.jdbc.Driver");
            }
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }

        try {
            Properties prop = new Properties();
            prop.setProperty("user", db_user);
            prop.setProperty("password", db_passwd);
            prop.setProperty("useUnicode", "true");
            prop.setProperty("characterEncoding", "UTF-8");
            db = DriverManager.getConnection(driver, prop);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        try {
            db.createStatement().execute("CREATE TABLE IF NOT EXISTS `atom_leaderboard` " +
                    "(`name` varchar(16) NOT NULL UNIQUE, `score` varchar(16) NOT NULL);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean registerUser(String userName) {
        try {
            checkConnection();
            PreparedStatement ps = db.prepareStatement("INSERT INTO `atom_leaderboard` (`name`, `score`) VALUES (?, ?)");
            ps.setString(1, userName.toLowerCase());
            ps.setString(2, String.valueOf(0));
            ps.execute();
            ps.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(String userName, int amount) {
        try {
            checkConnection();

            PreparedStatement state = db.prepareStatement("DELETE from `atom_leaderboard` WHERE `name` LIKE ?");
            state.setString(1, userName.toLowerCase());
            state.execute();
            state.close();

            PreparedStatement ps = db.prepareStatement("INSERT INTO `atom_leaderboard` (`name`, `score`) VALUES (?, ?)");
            ps.setString(1, userName.toLowerCase());
            ps.setString(2, String.valueOf(amount));
            ps.execute();
            ps.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Set<Score> loadScores() {
        Set<Score> scores = new HashSet<>();
        try {
            PreparedStatement ps = db.prepareStatement("SELECT * FROM `BurnStat_online`");
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                scores.add(new Score(Long.valueOf(res.getString("name")), Integer.valueOf(res.getString("score"))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scores;
    }

    private void checkConnection (){
        try {
            if (db.isClosed()){
                init();
            }
            PreparedStatement ps = db.prepareStatement("SELECT 1");
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
