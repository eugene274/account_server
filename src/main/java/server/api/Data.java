package server.api;

import server.model.services.AccountService;
import server.model.response.ApiErrors.InternalError;
import server.model.response.ApiRequestResponse;
import server.model.data.Score;
import server.model.services.LeaderBoardService;
import server.model.services.LeaderBoardServiceImpl;

import javax.validation.constraints.NotNull;
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
        return ApiRequestResponse.ok(accountService.getOnlineUsers()).toString();
    }

    @GET
    @Path("/leaderboard")
    public String getLeaderboard(
            @QueryParam("n") Integer N,
            @HeaderParam("userId") Long userId
    ){
        LeaderBoardService lb = null;
        try {
            lb = new LeaderBoardServiceImpl();
        } catch (InternalError internalError) {
            return ApiRequestResponse.fail(internalError).toString();
        }

        List<Score> leaders = null;
        try {
            leaders = (N == null)? lb.getLeaders() : lb.getLeaders(N);
            return ApiRequestResponse.ok(leaders).toString();
        } catch (InternalError internalError) {
            return ApiRequestResponse.fail(internalError).toString();
        }
    }

    @GET
    @Path("/leaderboard/me")
    public String getScore (@NotNull @HeaderParam("userId") Long userId){
        LeaderBoardService lbs = null;
        try{
            lbs = new LeaderBoardServiceImpl();
            Score score = lbs.getScore(userId);
            return ApiRequestResponse.ok(score.getScore()).toString();
        } catch (InternalError internalError) {
            return ApiRequestResponse.fail(internalError).toString();
        }
    }

}
