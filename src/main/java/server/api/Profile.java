package server.api;

import server.model.AccountService;
import server.model.customer.CustomerRequestError;
import server.model.customer.CustomerRequestResponse;
import server.model.data.UserProfile;
import server.model.services.ProfileManagerService;

import javax.ws.rs.*;

/**
 * Created by eugene on 10/19/16.
 */


@Authorized
@Path("/profile")
@Produces("application/json")
public class Profile {

    @POST
    @Path("/{field_name}")
    @Consumes("application/x-www-form-urlencoded")
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

    @GET
    @Path("/")
    public String getMe(
            @HeaderParam("token") String token,
            @HeaderParam("userId") String userIdString
    ){
        try {
            UserProfile profile = new ProfileManagerService(Long.parseLong(userIdString)).getProfile();
            return CustomerRequestResponse.ok(profile).toString();
        } catch (CustomerRequestError customerRequestError) {
            return CustomerRequestResponse.fail(customerRequestError).toString();
        }
    }
}
