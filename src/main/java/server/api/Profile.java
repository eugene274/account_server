package server.api;

import server.model.AccountService;
import server.model.customer.CustomerRequestError;
import server.model.customer.CustomerRequestResponse;
import server.model.services.ProfileManagerService;

import javax.ws.rs.*;

/**
 * Created by eugene on 10/19/16.
 */


@Authorized
@Path("/profile")
public class Profile {

    @POST
    @Path("/{field_name}")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    public String renewName(
            @HeaderParam("token") String token,
            @HeaderParam("userId") String userIdString,
            @PathParam("field_name") String fName,
            @FormParam("value") String fValue
    ){
        try {
            new ProfileManagerService(Long.parseLong(userIdString)).update(fName,fValue);
            return CustomerRequestResponse.ok(null).toString();
        } catch (CustomerRequestError error) {
            return CustomerRequestResponse.fail(error).toString();
        }
    }
}
