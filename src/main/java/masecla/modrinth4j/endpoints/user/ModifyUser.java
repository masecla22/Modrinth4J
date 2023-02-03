package masecla.modrinth4j.endpoints.user;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;
import masecla.modrinth4j.endpoints.user.ModifyUser.ModifyUserRequest;

/**
 * This endpoint is used to modify a user.
 */
public class ModifyUser extends Endpoint<EmptyResponse, ModifyUserRequest> {
    /**
     * This class is used to represent the request.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ModifyUserRequest {
        /** The user's username */
        private String username;
        /** The user's name */
        private String name;
        /** The user's email */
        private String email;
        /** The user's bio */
        private String bio;
    }

    /**
     * Creates a new instance of the endpoint.
     * 
     * @param client The client to use.
     * @param gson   The gson instance to use.
     */
    public ModifyUser(HttpClient client, Gson gson) {
        super(client, gson);
    }

    /**
     * Returns the endpoint.
     */
    @Override
    public String getEndpoint() {
        return "/user/{id}";
    }

    /**
     * Returns the request class.
     */
    @Override
    public TypeToken<ModifyUserRequest> getRequestClass() {
        return TypeToken.get(ModifyUserRequest.class);
    }

    /**
     * Returns the response class.
     */
    @Override
    public TypeToken<EmptyResponse> getResponseClass() {
        return TypeToken.get(EmptyResponse.class);
    }

    /**
     * Returns the method.
     *
     * @return - The HTTP method being used
     */
    @Override
    public String getMethod() {
        return "PATCH";
    }

}
