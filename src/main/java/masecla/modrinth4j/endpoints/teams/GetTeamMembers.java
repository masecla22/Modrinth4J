package masecla.modrinth4j.endpoints.teams;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.model.team.ModrinthTeamMember;

public class GetTeamMembers extends Endpoint<List<ModrinthTeamMember>, EmptyRequest> {

    public GetTeamMembers(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/team/{id}/members";
    }

    @Override
    public TypeToken<EmptyRequest> getRequestClass() {
        return TypeToken.get(EmptyRequest.class);
    }

    @Override
    public TypeToken<List<ModrinthTeamMember>> getResponseClass() {
        return new TypeToken<List<ModrinthTeamMember>>() {
        };
    }
}
