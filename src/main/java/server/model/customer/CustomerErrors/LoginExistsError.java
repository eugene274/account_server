package server.model.customer.CustomerErrors;

import server.model.customer.CustomerRequestError;

/**
 * Created by eugene on 10/18/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public class LoginExistsError extends CustomerRequestError {
    public LoginExistsError(String login) {
        super("Login '" + login + "' exists", 10);
    }
}
