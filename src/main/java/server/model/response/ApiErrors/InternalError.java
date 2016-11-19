package server.model.response.ApiErrors;

import server.model.response.ApiRequestError;

/**
 * Created by eugene on 11/6/16.
 */
public class InternalError extends ApiRequestError {
    public InternalError() {
        super("Internal server error", 0);
    }
}
