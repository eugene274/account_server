package server.api;

import server.model.AccountService;
import server.model.customer.CustomerRequestResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by eugene on 10/18/16.
 */

@SuppressWarnings("DefaultFileTemplate")
@Authorized
@Path("/data")
@Produces("application/json")
public class Data {

    private static final AccountService accountService = AccountService.getInstance();

    @GET
    @Path("/users")
    public String getAllUsers(){
        return CustomerRequestResponse.ok(accountService.getOnlineUsers()).toString();
    }

}
