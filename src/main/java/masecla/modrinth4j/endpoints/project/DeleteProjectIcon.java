package masecla.modrinth4j.endpoints.project;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;

/**
 * Deletes the icon of a project.
 */
public class DeleteProjectIcon extends Endpoint<EmptyResponse, EmptyRequest> {

    /**
     * Creates a new instance of the endpoint.
     * 
     * @param client - the client to use
     * @param gson   - the gson instance to use
     */
    public DeleteProjectIcon(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/projects/{slug}/icon";
    }

    @Override
    public TypeToken<EmptyResponse> getResponseClass() {
        return TypeToken.get(EmptyResponse.class);
    }

    @Override
    public TypeToken<EmptyRequest> getRequestClass() {
        return TypeToken.get(EmptyRequest.class);
    }

    @Override
    public String getMethod() {
        return "DELETE";
    }
}
