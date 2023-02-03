package masecla.modrinth4j.endpoints.teams;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;
import masecla.modrinth4j.endpoints.teams.AddMemberToTeam.AddMemberToTeamRequest;

/**
 * This endpoint is used to add a member to a team.
 */
public class AddMemberToTeam extends Endpoint<EmptyResponse, AddMemberToTeamRequest> {
    /**
     * This class is used to represent the request.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddMemberToTeamRequest {
        /** The user ID of the user to add to the team. */
        private String userId;
    }

    /**
     * Creates a new instance of the endpoint.
     * 
     * @param client The client to use.
     * @param gson   The gson instance to use.
     */
    public AddMemberToTeam(HttpClient client, Gson gson) {
        super(client, gson);
    }

    /**
     * Returns the endpoint.
     */
    @Override
    public String getEndpoint() {
        return "/team/{id}/members";
    }

    /**
     * Returns the request class.
     */
    @Override
    public TypeToken<AddMemberToTeamRequest> getRequestClass() {
        return TypeToken.get(AddMemberToTeamRequest.class);
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
     */
    @Override
    public String getMethod() {
        return "POST";
    }
}
