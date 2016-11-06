package server.model.customer.CustomerErrors;

import server.model.customer.CustomerRequestError;

/**
 * Created by eugene on 10/25/16.
 */
public class PolicyViolationError extends CustomerRequestError {
    public PolicyViolationError() {
        super("Credentials policy violation", 3);
    }
}
