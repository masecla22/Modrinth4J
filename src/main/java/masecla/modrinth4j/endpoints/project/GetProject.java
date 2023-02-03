package masecla.modrinth4j.endpoints.project;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.model.project.Project;

/**
 * This endpoint is used to get a project.
 */
public class GetProject extends Endpoint<Project, EmptyRequest> {

    /**
     * Creates a new instance of the endpoint.
     * 
     * @param client The client to use.
     * @param gson   The gson instance to use.
     */
    public GetProject(HttpClient client, Gson gson) {
        super(client, gson);
    }

    /**
     * Returns the endpoint.
     */
    @Override
    public String getEndpoint() {
        return "/project/{id}";
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
    public TypeToken<Project> getResponseClass() {
        return TypeToken.get(Project.class);
    }

    /**
     * Returns whether the endpoint requires a body.
     */
    @Override
    public boolean requiresBody() {
        return false;
    }

}
