package server.misc;

/**
 * Created by eugene on 10/25/16.
 */
public class CredentialsPolicy {
    public static boolean checkLogin(String login){
        return login.matches("^[a-zA-Z0-9]{4,}$");
    }

    public static boolean checkPassword(String password){
        return password.matches("^[a-zA-Z0-9]{6,}$");
    }
}
