package server.model.customer;

/**
 * Created by eugene on 10/25/16.
 */
public class PolicyViolationError extends CustomerRequestError {
    public PolicyViolationError() {
        super("Credentials policy violation", 3);
    }
}
