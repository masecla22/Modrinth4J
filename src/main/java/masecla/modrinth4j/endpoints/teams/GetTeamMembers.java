package masecla.modrinth4j.endpoints.teams;

import com.google.gson.Gson;

import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.model.team.ModrinthTeamMember;

public class GetTeamMembers extends Endpoint<ModrinthTeamMember[], EmptyRequest> {

    public GetTeamMembers(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/team/{id}/members";
    }

    @Override
    public Class<EmptyRequest> getRequestClass() {
        return EmptyRequest.class;
    }

    @Override
    public Class<ModrinthTeamMember[]> getResponseClass() {
        return ModrinthTeamMember[].class;
    }
}
