package masecla.modrinth4j.endpoints.teams;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;

public class RemoveMemberFromTeam extends Endpoint<EmptyResponse, EmptyRequest> {

    public RemoveMemberFromTeam(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/team/{id}/members/{user}";
    }

    @Override
    public TypeToken<EmptyRequest> getRequestClass() {
        return TypeToken.get(EmptyRequest.class);
    }

    @Override
    public TypeToken<EmptyResponse> getResponseClass() {
        return TypeToken.get(EmptyResponse.class);
    }

    @Override
    public String getMethod() {
        return "DELETE";
    }
}
