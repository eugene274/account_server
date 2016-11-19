package server.model.response.ApiErrors;

import server.model.response.ApiRequestError;

/**
 * Created by eugene on 10/18/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public class LoginExistsError extends ApiRequestError {
    public LoginExistsError(String login) {
        super("Login '" + login + "' exists", 10);
    }
}
