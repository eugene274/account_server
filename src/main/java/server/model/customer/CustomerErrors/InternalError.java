package server.model.customer.CustomerErrors;

import com.fasterxml.jackson.annotation.JsonProperty;
import server.model.customer.CustomerRequestError;

/**
 * Created by eugene on 11/6/16.
 */
public class InternalError extends CustomerRequestError {
    public InternalError() {
        super("Internal server error", 0);
    }
}
