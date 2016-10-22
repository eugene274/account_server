package server.model.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import server.model.data.UserProfile;
import server.model.mixins.TokenMixin;
import server.model.mixins.UserProfileMixin;
import server.model.data.Token;

import java.io.IOException;

/**
 * Created by eugene on 10/13/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public class CustomerRequestResponse {
    private static String PROCESSING_ERROR = "{" +
            "\"status\": \"ERROR\"," +
            "\"error\" : { " +
            "\"code\"  : 0," +
            "\"reason\": \"JSON Processing Error\"" +
            "}" +
            "}";

    private CustomerRequestStatus status;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private CustomerRequestError error;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private Object response;

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        // nulls
        mapper.enable(JsonParser.Feature.ALLOW_MISSING_VALUES);

        mapper.addMixIn(UserProfile.class, UserProfileMixin.class);
        mapper.addMixIn(Token.class, TokenMixin.class);
    }

    public static CustomerRequestResponse ok(Object response){
        return new CustomerRequestResponse(CustomerRequestStatus.OK, null, response);
    }

    public static CustomerRequestResponse fail(CustomerRequestError error){
        return new CustomerRequestResponse(CustomerRequestStatus.ERROR, error, null);
    }

    public static CustomerRequestResponse readJson(String input) throws IOException {
        return mapper.readValue(input,CustomerRequestResponse.class);
    }

    @Override
    public String toString() {
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.getStackTrace();
            return PROCESSING_ERROR;
        }
    }

    private CustomerRequestResponse() {
    }

    private CustomerRequestResponse(CustomerRequestStatus status, CustomerRequestError error, Object response) {
        this.status = status;
        this.error = error;
        this.response = response;
    }


    public CustomerRequestStatus getStatus() {
        return status;
    }

    public CustomerRequestError getError() {
        return error;
    }

    public Object getResponse() {
        return response;
    }
}
