package masecla.modrinth4j.endpoints.teams;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.teams.GetTeamsMembers.GetTeamsMembersRequest;
import masecla.modrinth4j.model.team.ModrinthTeamMember;

/**
 * This endpoint is used to get a list of team members.
 */
public class GetTeamsMembers extends Endpoint<List<List<ModrinthTeamMember>>, GetTeamsMembersRequest> {
    /**
     * This class is used to represent the request.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetTeamsMembersRequest {
        /** The list of team IDs to get the members of. */
        private List<String> ids;
    }

    /**
     * Creates a new instance of the endpoint.
     * 
     * @param client The client to use.
     * @param gson   The gson instance to use.
     */
    public GetTeamsMembers(HttpClient client, Gson gson) {
        super(client, gson);
    }

    /**
     * Returns the endpoint.
     */
    @Override
    public String getEndpoint() {
        return "/teams";
    }

    /**
     * Returns the request class.
     *
     * @return - The {@link TypeToken} for the response.
     */
    @Override
    public TypeToken<GetTeamsMembersRequest> getRequestClass() {
        return TypeToken.get(GetTeamsMembersRequest.class);
    }

    /**
     * Returns the response class.
     *
     * @return - The {@link TypeToken} for the response.
     */
    @Override
    public TypeToken<List<List<ModrinthTeamMember>>> getResponseClass() {
        return new TypeToken<List<List<ModrinthTeamMember>>>() {
        };
    }

    /**
     * Returns whether or not the request is a JSON body.
     */
    @Override
    public boolean isJsonBody() {
        return false;
    }
}
