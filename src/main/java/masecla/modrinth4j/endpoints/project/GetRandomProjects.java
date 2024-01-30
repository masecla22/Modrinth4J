package masecla.modrinth4j.endpoints.project;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.model.project.Project;

/**
 * Gets a list of random projects.
 */
public class GetRandomProjects extends Endpoint<List<Project>, EmptyRequest> {

    /**
     * Creates a new instance of the endpoint.
     * 
     * @param client - the client to use
     * @param gson   - the gson instance to use
     */
    public GetRandomProjects(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/projects_random?count={count}";
    }

    @Override
    public TypeToken<List<Project>> getResponseClass() {
        return new TypeToken<List<Project>>() {
        };
    }

    @Override
    public TypeToken<EmptyRequest> getRequestClass() {
        return TypeToken.get(EmptyRequest.class);
    }

}
