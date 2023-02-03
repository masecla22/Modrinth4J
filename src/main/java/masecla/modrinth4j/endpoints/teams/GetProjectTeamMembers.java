package masecla.modrinth4j.endpoints.teams;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.model.team.ModrinthTeamMember;

/**
 * This endpoint is used to get a list of members from a project.
 */
public class GetProjectTeamMembers extends Endpoint<List<ModrinthTeamMember>, EmptyRequest> {

    /**
     * Creates a new instance of the endpoint.
     * 
     * @param client The client to use.
     * @param gson   The gson instance to use.
     */
    public GetProjectTeamMembers(HttpClient client, Gson gson) {
        super(client, gson);
    }

    /**
     * Returns the endpoint.
     */
    @Override
    public String getEndpoint() {
        return "/project/{id}/members";
    }

    /**
     * Returns the request class.
     */
    @Override
    public TypeToken<EmptyRequest> getRequestClass() {
        return TypeToken.get(EmptyRequest.class);
    }

    /**
     * Returns the response class.
     */
    @Override
    public TypeToken<List<ModrinthTeamMember>> getResponseClass() {
        return new TypeToken<List<ModrinthTeamMember>>() {
        };
    }
}
