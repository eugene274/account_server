package server.model.customer.CustomerErrors;

import com.fasterxml.jackson.annotation.JsonProperty;
import server.model.customer.CustomerRequestError;

/**
 * Created by eugene on 11/5/16.
 */
public class WrongFieldError extends CustomerRequestError {
    public WrongFieldError(String field) {
        super(String.format("Field '%s' doesn't exists", field), 15);
    }
}
