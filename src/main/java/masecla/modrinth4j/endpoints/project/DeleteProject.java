package masecla.modrinth4j.endpoints.project;

import com.google.gson.Gson;

import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;

public class DeleteProject extends Endpoint<EmptyResponse, EmptyRequest> {

    public DeleteProject(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/project/{id}";
    }

    @Override
    public Class<EmptyRequest> getRequestClass() {
        return EmptyRequest.class;
    }

    @Override
    public Class<EmptyResponse> getResponseClass() {
        return EmptyResponse.class;
    }

    @Override
    public String getMethod() {
        return "DELETE";
    }

    @Override
    public boolean requiresBody() {
        return false;
    }
}
