package masecla.modrinth4j.endpoints.organization;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.model.project.Project;

/**
 * Endpoint for getting projects belonging to an organization.
 */
public class GetOrganizationProjects extends Endpoint<List<Project>, EmptyRequest> {

    public GetOrganizationProjects(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/v3/organization/{id}/projects";
    }

    @Override
    public String getMethod() {
        return "GET";
    }

    @Override
    public TypeToken<List<Project>> getResponseClass() {
        return new TypeToken<List<Project>>() {};
    }

    @Override
    public TypeToken<EmptyRequest> getRequestClass() {
        return TypeToken.get(EmptyRequest.class);
    }
}
