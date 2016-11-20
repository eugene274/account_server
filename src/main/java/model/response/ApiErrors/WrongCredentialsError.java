package model.response.ApiErrors;

import model.response.ApiRequestError;

/**
 * Created by eugene on 10/13/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public class WrongCredentialsError extends ApiRequestError {
    public WrongCredentialsError() {
        super("Wrong credentials", 1);
    }
}
