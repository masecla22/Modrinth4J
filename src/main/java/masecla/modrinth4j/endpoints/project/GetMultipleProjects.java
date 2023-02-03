package masecla.modrinth4j.endpoints.project;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.project.GetMultipleProjects.GetMultipleProjectsRequest;
import masecla.modrinth4j.model.project.Project;

/**
 * This endpoint is used to get multiple projects.
 */
public class GetMultipleProjects extends Endpoint<List<Project>, GetMultipleProjectsRequest> {

    /**
     * This class is used to represent the request.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetMultipleProjectsRequest {
        private List<String> ids;
    }

    /**
     * Creates a new instance of the endpoint.
     * 
     * @param client The client to use.
     * @param gson   The gson instance to use.
     */
    public GetMultipleProjects(HttpClient client, Gson gson) {
        super(client, gson);
    }

    /**
     * Returns the endpoint.
     */
    @Override
    public String getEndpoint() {
        return "/projects";
    }

    /**
     * Returns the request class.
     */
    @Override
    public TypeToken<GetMultipleProjectsRequest> getRequestClass() {
        return TypeToken.get(GetMultipleProjectsRequest.class);
    }

    /**
     * Returns the response class.
     */
    @Override
    public TypeToken<List<Project>> getResponseClass() {
        return new TypeToken<List<Project>>() {
        };
    }

    /**
     * Returns whether the endpoint requires a json body.
     */
    @Override
    public boolean isJsonBody() {
        return false;
    }
}
