package masecla.modrinth4j.endpoints.teams;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.model.team.ModrinthTeamMember;

@AllArgsConstructor
public class TeamsEndpoints {
    private Gson gson;
    private HttpClient client;

    public CompletableFuture<ModrinthTeamMember[]> getTeamMembers(String projectId) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", projectId);

        return new GetProjectTeamMembers(client, gson).sendRequest(new EmptyRequest(), parameters);
    }
}
