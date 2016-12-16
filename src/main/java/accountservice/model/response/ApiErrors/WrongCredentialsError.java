package accountservice.model.response.ApiErrors;

import accountservice.model.response.ApiRequestError;

/**
 * Created by eugene on 10/13/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public class WrongCredentialsError extends ApiRequestError {
    public WrongCredentialsError() {
        super("Wrong credentials", 1);
    }
}
