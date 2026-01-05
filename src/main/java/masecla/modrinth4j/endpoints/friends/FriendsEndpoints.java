package masecla.modrinth4j.endpoints.friends;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;
import masecla.modrinth4j.model.user.ModrinthUserFriend;

/**
 * Endpoints for managing friend relationships.
 */
@AllArgsConstructor
public class FriendsEndpoints {
    /** The Gson instance to use */
    private Gson gson;
    /** The HTTP client to use */
    private HttpClient client;

    /**
     * Adds a friend or accepts a friend request.
     * 
     * @param userId - The ID of the user to add as a friend
     * @return A {@link CompletableFuture} that will return an empty response
     */
    public CompletableFuture<EmptyResponse> addFriend(String userId) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", userId);
        return new AddFriend(client, gson).sendRequest(new EmptyRequest(), parameters);
    }

    /**
     * Removes a friend or rejects a friend request.
     * 
     * @param userId - The ID of the user to remove as a friend
     * @return A {@link CompletableFuture} that will return an empty response
     */
    public CompletableFuture<EmptyResponse> removeFriend(String userId) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", userId);
        return new RemoveFriend(client, gson).sendRequest(new EmptyRequest(), parameters);
    }

    /**
     * Gets the current user's friends list.
     * 
     * @return A {@link CompletableFuture} that will return the friends list
     */
    public CompletableFuture<List<ModrinthUserFriend>> getFriends() {
        return new GetFriends(client, gson).sendRequest(new EmptyRequest());
    }
}
