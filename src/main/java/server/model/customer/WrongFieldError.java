package server.model.customer;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by eugene on 11/5/16.
 */
public class WrongFieldError extends CustomerRequestError {
    public WrongFieldError(String field) {
        super(String.format("Field '%s' doesn't exists", field), 15);
    }
}
