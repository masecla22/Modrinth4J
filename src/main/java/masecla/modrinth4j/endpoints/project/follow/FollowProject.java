package masecla.modrinth4j.endpoints.project.follow;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;

/**
 * This endpoint is used to follow a project.
 */
public class FollowProject extends Endpoint<EmptyResponse, EmptyRequest> {

    /**
     * Creates a new instance of the endpoint.
     * 
     * @param client The client to use.
     * @param gson   The gson instance to use.
     */
    public FollowProject(HttpClient client, Gson gson) {
        super(client, gson);
    }

    /**
     * Returns the endpoint.
     */
    @Override
    public String getEndpoint() {
        return "/project/{id}/follow";
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
    public TypeToken<EmptyResponse> getResponseClass() {
        return TypeToken.get(EmptyResponse.class);
    }

    /**
     * Returns the method.
     *
     * @return - The HTTP method being used
     */
    @Override
    public String getMethod() {
        return "POST";
    }
}
