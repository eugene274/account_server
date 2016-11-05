package server.model.customer;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by eugene on 11/6/16.
 */
public class InternalError extends CustomerRequestError {
    public InternalError() {
        super("Internal server error", 0);
    }
}
