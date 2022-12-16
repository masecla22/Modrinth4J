package masecla.modrinth4j.endpoints.project;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;
import masecla.modrinth4j.endpoints.project.GetMultipleProjects.GetMultipleProjectsRequest;
import masecla.modrinth4j.endpoints.project.ModifyProject.ModifyProjectRequest;
import masecla.modrinth4j.model.project.Project;

@AllArgsConstructor
public class ProjectEndpoints {
    private Gson gson;
    private HttpClient client;

    public CompletableFuture<Project> get(String project) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", project);
        return new GetProject(client, gson).sendRequest(new EmptyRequest(), parameters);
    }

    public CompletableFuture<Project[]> get(String... project) {
        return new GetMultipleProjects(client, gson)
                .sendRequest(new GetMultipleProjectsRequest(project));
    }

    public CompletableFuture<ModifyProjectRequest> modify(String project, ModifyProjectRequest request) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", project);
        return new ModifyProject(client, gson).sendRequest(request, parameters);
    }

    public CompletableFuture<EmptyResponse> delete(String project) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", project);
        return new DeleteProject(client, gson).sendRequest(new EmptyRequest(), parameters);
    }
}
