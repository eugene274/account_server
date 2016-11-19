package server.api;

import com.squareup.okhttp.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import server.Server;
import server.database.DbHibernate;
import server.model.response.ApiRequestResponse;
import server.model.response.ApiRequestStatus;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.junit.Assert.*;

/**
 * Created by eugene on 11/4/16.
 */
public class AuthTest {
    private static Thread thread = new Thread(new Server());
    private static OkHttpClient client = new OkHttpClient();

    private static String AUTH_URL = "http://localhost:8080/auth/";

    private static String credentialsBody(String user, String pass){
        return new StringBuilder()
                .append("user=")
                .append(user)
                .append("&password=")
                .append(pass).toString();
    }

    private static RequestBody requestBodyWrapper(String body){
        return RequestBody.create(MediaType.parse(
                "application/x-www-form-urlencoded"),
                body
        );
    }

    private static Request genAuthRequest(String method, RequestBody body){
        return new Request.Builder()
                .url(AUTH_URL + method)
                .post(body)
                .build();
    }

    private static Request genAuthRequest(String token, String method, RequestBody body){
        return new Request.Builder()
                .url(AUTH_URL + method)
                .post(body)
                .addHeader("Authorization","Bearer " + token)
                .build();
    }

    private static ApiRequestResponse parseResponse(Response response) throws IOException {
        return ApiRequestResponse.readJson(response.body().string());
    }

    private static ApiRequestResponse register(String name, String password) throws IOException {
        Request request = genAuthRequest("register", requestBodyWrapper(credentialsBody(name, password)));
        Response response = client.newCall(request).execute();
        return parseResponse(response);
    }

    private static ApiRequestResponse login(String name, String password) throws IOException {
        Request request = genAuthRequest("login", requestBodyWrapper(credentialsBody(name, password)));
        Response response = client.newCall(request).execute();
        return parseResponse(response);
    }


    @BeforeClass
    public static void runServer() throws InterruptedException {
        DbHibernate.newSession();
        thread.start();
        Thread.sleep(10_000);
    }

    @AfterClass
    public static void  stopServer(){
        thread.interrupt();
    }

    @Test
    public void signIn() throws Exception {
        String name = "testuser2";
        String pass = "testpass";

        register(name, pass);

        ApiRequestResponse response = login(name,pass);

        assertEquals(
                response.getStatus(),
                ApiRequestStatus.OK
        );

        String tokenString = ((LinkedHashMap<String,String>) response.getResponse()).get("token");
        assertNotNull(tokenString);

        String tokenString2 = ((HashMap<String,String>) login(name,pass).getResponse()).get("token");
        assertNotNull(tokenString2);

        assertEquals(
                tokenString2,
                tokenString
        );

    }

    @Test
    public void signUp() throws Exception {
        String name = "testuser";
        String pass = "testpass";

        assertEquals(
                register(name,pass).getStatus(),
                ApiRequestStatus.OK
        );

        // login exists
        assertEquals(
                register(name, pass).getStatus(),
                ApiRequestStatus.ERROR
        );


    }

    @Test
    public void logOut() throws Exception {
        String name = "testuser3";
        String pass = "testpass";

        register(name,pass);
        String token = ((HashMap<String,String>) login(name,pass).getResponse()).get("token");

        // Unauthorized request
        Request request = genAuthRequest("logout", requestBodyWrapper(""));
        Response response = client.newCall(request).execute();
        assertEquals(response.code(), 401);

        request = genAuthRequest(token,"logout",requestBodyWrapper(""));
        response = client.newCall(request).execute();
        assertEquals(response.code(), 200);

        assertEquals(parseResponse(response).getStatus(), ApiRequestStatus.OK);

        // invalid token
        request = genAuthRequest(token,"logout",requestBodyWrapper(""));
        response = client.newCall(request).execute();
        assertEquals(response.code(), 401);

    }

}