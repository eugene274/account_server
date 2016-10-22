package server.api;

import server.model.AccountService;
import server.model.customer.CustomerRequestError;
import server.model.customer.CustomerRequestResponse;

import javax.ws.rs.*;

/**
 * Created by eugene on 10/19/16.
 */

@SuppressWarnings("DefaultFileTemplate")
@Authorized
@Path("/profile")
public class Profile {

    private static AccountService accountService = AccountService.getInstance();

    @POST
    @Path("name")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    public String renewName(
            @HeaderParam("token") String token,
            @FormParam("name") String name
    ){
        try {
            accountService.updateLogin(token,name);
            return CustomerRequestResponse.ok(null).toString();
        } catch (CustomerRequestError error) {
            return CustomerRequestResponse.fail(error).toString();
        }
    }
}
