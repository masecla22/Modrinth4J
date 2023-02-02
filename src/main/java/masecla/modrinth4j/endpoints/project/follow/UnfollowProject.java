package masecla.modrinth4j.endpoints.project.follow;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;

public class UnfollowProject extends Endpoint<EmptyResponse, EmptyRequest> {

    public UnfollowProject(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/project/{id}/follow";
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
