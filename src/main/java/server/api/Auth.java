package server.api;

import model.data.Token;
import model.response.ApiErrors.InternalError;
import model.response.ApiRequestError;
import model.response.ApiRequestResponse;
import server.services.AccountService;

import javax.ws.rs.*;

/**
 * Created by eugene on 10/13/16.
 */
@Path("/auth")
@Produces({"application/json","text/plain"})
public class Auth {
    private static final AccountService accountService = new AccountService();

    @POST
    @Path("login")
    @Consumes("application/x-www-form-urlencoded")
    public String signIn(
            @DefaultValue("") @FormParam("user") String login,
            @DefaultValue("") @FormParam("password") String password
    ){
        try {
            Token token = accountService.signIn(login, password);
            return ApiRequestResponse.ok(token).toString();
        } catch (ApiRequestError authenticationError) {
            return ApiRequestResponse.fail(authenticationError).toString();
        }

    }

    @POST
    @Path("register")
    @Consumes("application/x-www-form-urlencoded")
    public String signUp(
            @DefaultValue("") @FormParam("user") String login,
            @DefaultValue("") @FormParam("password") String password
    ){
        try {
            accountService.signUp(login,password);
            return ApiRequestResponse.ok(null).toString();
        } catch (ApiRequestError apiRequestError) {
            return ApiRequestResponse.fail(apiRequestError).toString();
        }
    }

    @POST
    @Path("/logout")
    @Authorized

    public String logOut(
            @HeaderParam("token") String token,
            @HeaderParam("userId") Long userId
    ){
        try {
            accountService.logout(token);
            return ApiRequestResponse.ok(null).toString();
        } catch (InternalError internalError) {
            return ApiRequestResponse.fail(internalError).toString();
        }

    }
}

