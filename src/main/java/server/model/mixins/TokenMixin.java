package server.model.mixins;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import server.model.data.UserProfile;

/**
 * Created by eugene on 10/18/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public interface TokenMixin {
    @JsonProperty("token") String toString();
    @JsonIgnore UserProfile getUser();
}
