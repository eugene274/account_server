package server.model;

import server.model.data.Token;
import server.model.data.UserProfile;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by eugene on 10/28/16.
 *
 * Deals with usersSignedIn hashmap
 */
public class TokenService {
    protected static final ConcurrentHashMap<Token, UserProfile> usersSignedIn
            = new ConcurrentHashMap<>();
    protected static final ConcurrentHashMap<UserProfile,Token> usersSignedInReverse
            = new ConcurrentHashMap<>();

    protected static UserProfile getUserByTokenString(String tokenString){
        return usersSignedIn.get(Token.valueOf(tokenString));
    }

    protected static void addUserSession(UserProfile user, Token token){
        usersSignedIn.putIfAbsent(token, user);
        usersSignedInReverse.putIfAbsent(user,token);
    }

    protected static UserProfile removeUserSession(UserProfile user){
        if (user == null) {
            return null;
        }
        Token token = usersSignedInReverse.remove(user);
        if (token == null) {
            return null;
        }
        return usersSignedIn.remove(token);
    }

    protected static UserProfile removeUserSession(Token token){
        if (token == null) {
            return null;
        }
        UserProfile profile = usersSignedIn.remove(token);
        if (profile == null) {
            return null;
        }
        usersSignedInReverse.remove(profile);
        return profile;
    }

    protected static Collection<UserProfile> users(){
        return usersSignedIn.values();
    }

    protected static Collection<Token> tokens(){
        return usersSignedInReverse.values();
    }

    public boolean isTokenValid(String tokenString){
        return tokens().contains(Token.valueOf(tokenString));
    }
}
