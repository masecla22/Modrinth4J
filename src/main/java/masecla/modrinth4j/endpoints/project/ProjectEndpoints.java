package masecla.modrinth4j.endpoints.project;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.project.GetProject.GetProjectRequest;
import masecla.modrinth4j.model.project.Project;

@AllArgsConstructor
public class ProjectEndpoints {
    private Gson gson;
    private HttpClient client;

    public CompletableFuture<Project> get(String project) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", project);
        return new GetProject(client, gson).sendRequest(new GetProjectRequest(), parameters);
    }
}
