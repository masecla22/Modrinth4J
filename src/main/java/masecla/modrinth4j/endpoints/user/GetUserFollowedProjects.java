package masecla.modrinth4j.endpoints.user;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.model.project.Project;

/**
 * This endpoint is used to get a list of projects from a user.
 */
public class GetUserFollowedProjects extends Endpoint<List<Project>, EmptyRequest> {

    /**
     * Creates a new instance of the endpoint.
     * 
     * @param client The client to use.
     * @param gson   The gson instance to use.
     */
    public GetUserFollowedProjects(HttpClient client, Gson gson) {
        super(client, gson);
    }

    /**
     * Returns the endpoint.
     */
    @Override
    public String getEndpoint() {
        return "/user/{id}/follows";
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
    public TypeToken<List<Project>> getResponseClass() {
        return new TypeToken<List<Project>>() {
        };
    }
}
