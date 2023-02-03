package masecla.modrinth4j.endpoints.user;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.user.GetUsers.GetUsersRequest;
import masecla.modrinth4j.model.user.ModrinthUser;

/**
 * This endpoint is used to get a list of users.
 */
public class GetUsers extends Endpoint<List<ModrinthUser>, GetUsersRequest> {

    /**
     * This class is used to represent the request.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetUsersRequest {
        /** The list of ids to get. */
        public List<String> ids;
    }

    /**
     * Creates a new instance of the endpoint.
     * 
     * @param client The client to use.
     * @param gson   The gson instance to use.
     */
    public GetUsers(HttpClient client, Gson gson) {
        super(client, gson);
    }

    /**
     * Returns the endpoint.
     */
    @Override
    public String getEndpoint() {
        return "/users";
    }

    /**
     * Returns the request class.
     *
     * @return - The {@link TypeToken} for the response.
     */
    @Override
    public TypeToken<GetUsersRequest> getRequestClass() {
        return TypeToken.get(GetUsersRequest.class);
    }

    /**
     * Returns the response class.
     *
     * @return - The {@link TypeToken} for the response.
     */
    @Override
    public TypeToken<List<ModrinthUser>> getResponseClass() {
        return new TypeToken<List<ModrinthUser>>() {
        };
    }

    /**
     * Returns whether or not the request is a json body.
     */
    @Override
    public boolean isJsonBody() {
        return false;
    }

}
