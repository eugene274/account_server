package server.model.mixins;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by eugene on 10/18/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public interface TokenMixin {
    @JsonProperty("token") String toString();
}
