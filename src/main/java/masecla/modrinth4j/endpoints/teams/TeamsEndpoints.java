package masecla.modrinth4j.endpoints.teams;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;
import masecla.modrinth4j.endpoints.teams.AddMemberToTeam.AddMemberToTeamRequest;
import masecla.modrinth4j.model.team.ModrinthTeamMember;

@AllArgsConstructor
public class TeamsEndpoints {
    private Gson gson;
    private HttpClient client;

    public CompletableFuture<ModrinthTeamMember[]> getProjectTeamMembers(String projectId) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", projectId);

        return new GetProjectTeamMembers(client, gson).sendRequest(new EmptyRequest(), parameters);
    }

    public CompletableFuture<ModrinthTeamMember[]> getTeamMembers(String teamId) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", teamId);

        return new GetTeamMembers(client, gson).sendRequest(new EmptyRequest(), parameters);
    }

    /**
     * Adds a member to a team
     * 
     * @param teamId - The ID of the team
     * @param userId - The ID of the user (NOT the username)
     * @return - An empty response
     */
    public CompletableFuture<EmptyResponse> addMemberToTeam(String teamId, String userId) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", teamId);

        return new AddMemberToTeam(client, gson).sendRequest(new AddMemberToTeamRequest(userId), parameters);
    }
}
