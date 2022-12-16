package masecla.modrinth4j.endpoints.project;

import java.util.List;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Data;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.endpoints.project.GetProjectDependencies.GetProjectDependenciesResponse;
import masecla.modrinth4j.model.project.Project;
import masecla.modrinth4j.model.version.ProjectVersion;

public class GetProjectDependencies extends Endpoint<GetProjectDependenciesResponse, EmptyRequest> {

    @Data
    @AllArgsConstructor
    public static class GetProjectDependenciesResponse {
        private List<Project> projects;
        private List<ProjectVersion> versions;
    }

    public GetProjectDependencies(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/project/{id}/dependencies";
    }

    @Override
    public Class<EmptyRequest> getRequestClass() {
        return EmptyRequest.class;
    }

    @Override
    public Class<GetProjectDependenciesResponse> getResponseClass() {
        return GetProjectDependenciesResponse.class;
    }
}
