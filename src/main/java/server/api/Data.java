package server.api;

import server.model.AccountService;
import server.model.customer.CustomerErrors.InternalError;
import server.model.customer.CustomerRequestResponse;
import server.model.data.Score;
import server.model.services.LeaderBoardService;
import server.model.services.LeaderBoardServiceImpl;

import javax.ws.rs.*;
import java.util.List;

/**
 * Created by eugene on 10/18/16.
 */

@SuppressWarnings("DefaultFileTemplate")
@Authorized
@Path("/data")
@Produces("application/json")
public class Data {

    private static final AccountService accountService = new AccountService();

    @GET
    @Path("/users")
    public String getAllUsers(){
        return CustomerRequestResponse.ok(accountService.getOnlineUsers()).toString();
    }

    @GET
    @Path("/leaderboard")
    public String getLeaderboard(
            @QueryParam("n") Integer N,
            @HeaderParam("userId") String userId
    ){
        LeaderBoardService lb = new LeaderBoardServiceImpl();

        List<Score> leaders = null;
        try {
            leaders = (N == null)? lb.getLeaders() : lb.getLeaders(N);
            return CustomerRequestResponse.ok(leaders).toString();
        } catch (InternalError internalError) {
            return CustomerRequestResponse.fail(internalError).toString();
        }
    }

}
