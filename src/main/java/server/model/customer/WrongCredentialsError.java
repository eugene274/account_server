package server.model.customer;

import server.model.customer.CustomerRequestError;

/**
 * Created by eugene on 10/13/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public class WrongCredentialsError extends CustomerRequestError {
    public WrongCredentialsError() {
        super("Wrong credentials", 1);
    }
}
