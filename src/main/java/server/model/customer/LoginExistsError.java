package server.model.customer;

/**
 * Created by eugene on 10/18/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public class LoginExistsError extends CustomerRequestError{
    public LoginExistsError(String login) {
        super("Login '" + login + "' exists", 10);
    }
}
