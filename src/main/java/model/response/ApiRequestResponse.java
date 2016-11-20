package model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.data.Token;
import model.data.UserProfile;
import model.mixins.TokenMixin;
import model.mixins.UserProfileMixin;

import java.io.IOException;

/**
 * Created by eugene on 10/13/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public class ApiRequestResponse {
    private static String PROCESSING_ERROR = "{" +
            "\"status\": \"ERROR\"," +
            "\"error\" : { " +
            "\"code\"  : 0," +
            "\"reason\": \"JSON Processing Error\"" +
            "}" +
            "}";

    private ApiRequestStatus status;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private ApiRequestError error;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private Object response;

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        // nulls
        mapper.enable(JsonParser.Feature.ALLOW_MISSING_VALUES);

        mapper.addMixIn(UserProfile.class, UserProfileMixin.class);
        mapper.addMixIn(Token.class, TokenMixin.class);
    }

    public static ApiRequestResponse ok(Object response){
        return new ApiRequestResponse(ApiRequestStatus.OK, null, response);
    }

    public static ApiRequestResponse fail(ApiRequestError error){
        return new ApiRequestResponse(ApiRequestStatus.ERROR, error, null);
    }

    public static ApiRequestResponse readJson(String input) throws IOException {
        return mapper.readValue(input,ApiRequestResponse.class);
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

    private ApiRequestResponse() {
    }

    private ApiRequestResponse(ApiRequestStatus status, ApiRequestError error, Object response) {
        this.status = status;
        this.error = error;
        this.response = response;
    }


    public ApiRequestStatus getStatus() {
        return status;
    }

    public ApiRequestError getError() {
        return error;
    }

    public Object getResponse() {
        return response;
    }
}
