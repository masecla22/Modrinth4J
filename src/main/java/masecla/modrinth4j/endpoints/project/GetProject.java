package masecla.modrinth4j.endpoints.project;

import com.google.gson.Gson;

import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.project.GetProject.GetProjectRequest;
import masecla.modrinth4j.model.project.Project;

public class GetProject extends Endpoint<Project, GetProjectRequest> {

    public static class GetProjectRequest {
    }

    public GetProject(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/project/{id}";
    }

    @Override
    public Class<GetProjectRequest> getRequestClass() {
        return GetProjectRequest.class;
    }

    @Override
    public Class<Project> getResponseClass() {
        return Project.class;
    }

    @Override
    public boolean requiresBody() {
        return false;
    }

}
