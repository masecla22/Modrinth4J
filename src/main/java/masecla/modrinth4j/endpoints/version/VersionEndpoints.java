package masecla.modrinth4j.endpoints.version;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.model.version.ProjectVersion;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
public class VersionEndpoints {
    private Gson gson;
    private HttpClient httpClient;

    public CompletableFuture<ProjectVersion[]> getProjectVersions(String id, GetProjectVersions.GetProjectVersionsRequest requestObject) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", id);

        return new GetProjectVersions(httpClient, gson).sendRequest(requestObject, parameters);
    }
}
