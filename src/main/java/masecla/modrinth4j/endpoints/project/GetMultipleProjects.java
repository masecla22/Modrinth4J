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

public class GetMultipleProjects extends Endpoint<List<Project>, GetMultipleProjectsRequest> {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetMultipleProjectsRequest {
        private List<String> ids;
    }

    public GetMultipleProjects(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/projects";
    }

    @Override
    public TypeToken<GetMultipleProjectsRequest> getRequestClass() {
        return TypeToken.get(GetMultipleProjectsRequest.class);
    }

    @Override
    public TypeToken<List<Project>> getResponseClass() {
        return new TypeToken<List<Project>>() {
        };
    }

    @Override
    public boolean isJsonBody() {
        return false;
    }
}
