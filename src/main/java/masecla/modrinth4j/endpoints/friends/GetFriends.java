package masecla.modrinth4j.endpoints.friends;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.model.user.ModrinthUserFriend;

/**
 * Endpoint for getting the current user's friends list.
 */
public class GetFriends extends Endpoint<List<ModrinthUserFriend>, EmptyRequest> {

    public GetFriends(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/v3/friends";
    }

    @Override
    public String getMethod() {
        return "GET";
    }

    @Override
    public TypeToken<List<ModrinthUserFriend>> getResponseClass() {
        return new TypeToken<List<ModrinthUserFriend>>() {};
    }

    @Override
    public TypeToken<EmptyRequest> getRequestClass() {
        return TypeToken.get(EmptyRequest.class);
    }
}
