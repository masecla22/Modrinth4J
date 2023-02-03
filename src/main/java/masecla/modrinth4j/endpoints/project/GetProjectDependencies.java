package masecla.modrinth4j.endpoints.project;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.AllArgsConstructor;
import lombok.Data;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.endpoints.project.GetProjectDependencies.GetProjectDependenciesResponse;
import masecla.modrinth4j.model.project.Project;
import masecla.modrinth4j.model.version.ProjectVersion;

/**
 * This endpoint is used to get a project's dependencies.
 */
public class GetProjectDependencies extends Endpoint<GetProjectDependenciesResponse, EmptyRequest> {

    /**
     * Represents the response from the endpoint.
     */
    @Data
    @AllArgsConstructor
    public static class GetProjectDependenciesResponse {
        private List<Project> projects;
        private List<ProjectVersion> versions;
    }

    /**
     * Creates a new instance of the endpoint.
     * 
     * @param client The client to use.
     * @param gson   The gson instance to use.
     */
    public GetProjectDependencies(HttpClient client, Gson gson) {
        super(client, gson);
    }

    /**
     * Returns the endpoint.
     */
    @Override
    public String getEndpoint() {
        return "/project/{id}/dependencies";
    }

    /**
     * Returns the request class.
     *
     * @return - The {@link TypeToken} for the response.
     */
    @Override
    public TypeToken<EmptyRequest> getRequestClass() {
        return TypeToken.get(EmptyRequest.class);
    }

    /**
     * Returns the response class.
     *
     * @return - The {@link TypeToken} for the response.
     */
    @Override
    public TypeToken<GetProjectDependenciesResponse> getResponseClass() {
        return TypeToken.get(GetProjectDependenciesResponse.class);
    }
}
