package masecla.modrinth4j.endpoints.version;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;
import masecla.modrinth4j.endpoints.version.AddFilesToVersion.AddFilesToVersionRequest;
import masecla.modrinth4j.endpoints.version.CreateVersion.CreateVersionRequest;
import masecla.modrinth4j.endpoints.version.GetVersions.GetVersionsRequest;
import masecla.modrinth4j.endpoints.version.ModifyVersion.ModifyVersionRequest;
import masecla.modrinth4j.endpoints.version.files.VersionFilesEndpoints;
import masecla.modrinth4j.model.version.ProjectVersion;

@AllArgsConstructor
public class VersionEndpoints {
    private Gson gson;
    private HttpClient httpClient;

    public VersionFilesEndpoints files() {
        return new VersionFilesEndpoints(gson, httpClient);
    }

    public CompletableFuture<ProjectVersion[]> getProjectVersions(String slug,
            GetProjectVersions.GetProjectVersionsRequest requestObject) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", slug);

        return new GetProjectVersions(httpClient, gson).sendRequest(requestObject, parameters);
    }

    public CompletableFuture<ProjectVersion> getVersion(String versionId) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", versionId);

        return new GetVersion(httpClient, gson).sendRequest(null, parameters);
    }

    public CompletableFuture<ProjectVersion[]> getVersions(String... versionIds) {
        return new GetVersions(httpClient, gson).sendRequest(new GetVersionsRequest(versionIds));
    }

    public CompletableFuture<ProjectVersion> modifyProjectVersion(String versionId, ModifyVersionRequest request) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", versionId);

        return new ModifyVersion(httpClient, gson).sendRequest(request, parameters);
    }

    public CompletableFuture<EmptyResponse> deleteProjectVersion(String versionId) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", versionId);

        return new DeleteVersion(httpClient, gson).sendRequest(null, parameters);
    }

    public CompletableFuture<ProjectVersion> createProjectVersion(CreateVersionRequest request) {
        return new CreateVersion(httpClient, gson).sendRequest(request);
    }

    public CompletableFuture<EmptyResponse> addFilesToVersion(String versionId, AddFilesToVersionRequest request){
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", versionId);

        return new AddFilesToVersion(httpClient, gson).sendRequest(request, parameters);
    }
}
