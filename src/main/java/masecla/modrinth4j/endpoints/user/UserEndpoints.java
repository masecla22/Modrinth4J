package masecla.modrinth4j.endpoints.user;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;
import masecla.modrinth4j.endpoints.user.ModifyUser.ModifyUserRequest;
import masecla.modrinth4j.model.user.ModrinthUser;

@AllArgsConstructor
public class UserEndpoints {
    private Gson gson;
    private HttpClient client;

    public CompletableFuture<ModrinthUser> getUser(String id) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", id);

        return new GetUser(client, gson).sendRequest(new EmptyRequest(), parameters);
    }

    public CompletableFuture<EmptyResponse> modifyUser(String id, ModifyUserRequest request) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", id);

        return new ModifyUser(client, gson).sendRequest(request, parameters);
    }

    public CompletableFuture<EmptyResponse> deleteUser(String id) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", id);

        return new DeleteUser(client, gson).sendRequest(new EmptyRequest(), parameters);
    }

    public CompletableFuture<ModrinthUser> getSelf() {
        return new GetSelf(client, gson).sendRequest(new EmptyRequest(), new HashMap<>());
    }
}
