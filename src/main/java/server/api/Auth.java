package server.api;

import server.model.AccountService;
import server.model.customer.CustomerRequestError;
import server.model.customer.CustomerRequestResponse;
import server.model.dao.UserProfileHibernate;
import server.model.data.Token;

import javax.ws.rs.*;

/**
 * Created by eugene on 10/13/16.
 */
@SuppressWarnings("DefaultFileTemplate")
@Path("/auth")
@Produces({"application/json","text/plain"})
public class Auth {
    private static final AccountService accountService = new AccountService();

    @POST
    @Path("/login")
    @Consumes("application/x-www-form-urlencoded")
    public String signIn(
            @DefaultValue("") @FormParam("user") String login,
            @DefaultValue("") @FormParam("password") String password
    ){
        try {
            Token token = accountService.signIn(login, password);
            return CustomerRequestResponse.ok(token).toString();
        } catch (CustomerRequestError authenticationError) {
            return CustomerRequestResponse.fail(authenticationError).toString();
        }

    }

    @POST
    @Path("/register")
    @Consumes("application/x-www-form-urlencoded")
    public String signUp(
            @DefaultValue("") @FormParam("user") String login,
            @DefaultValue("") @FormParam("password") String password
    ){
        try {
            accountService.signUp(login,password);
            return CustomerRequestResponse.ok(null).toString();
        } catch (CustomerRequestError customerRequestError) {
            return CustomerRequestResponse.fail(customerRequestError).toString();
        }
    }

    @POST
    @Path("/logout")
    @Authorized

    public String logOut(
            @HeaderParam("token") String token
    ){

        accountService.logout(token);
        return CustomerRequestResponse.ok(null).toString();
    }
}

