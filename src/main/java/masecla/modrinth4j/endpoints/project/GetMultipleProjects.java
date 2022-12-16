package masecla.modrinth4j.endpoints.project;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.project.GetMultipleProjects.GetMultipleProjectsRequest;
import masecla.modrinth4j.model.project.Project;

public class GetMultipleProjects extends Endpoint<Project[], GetMultipleProjectsRequest> {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetMultipleProjectsRequest {
        private String[] ids;
    }

    public GetMultipleProjects(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/projects";
    }

    @Override
    public Class<GetMultipleProjectsRequest> getRequestClass() {
        return GetMultipleProjectsRequest.class;
    }

    @Override
    public Class<Project[]> getResponseClass() {
        return Project[].class;
    }

    @Override
    public boolean isJsonBody() {
        return false;
    }
}
