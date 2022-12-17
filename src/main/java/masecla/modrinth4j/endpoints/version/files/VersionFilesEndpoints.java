package masecla.modrinth4j.endpoints.version.files;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;
import masecla.modrinth4j.endpoints.version.files.DeleteFileByHash.DeleteFileByHashRequest;
import masecla.modrinth4j.endpoints.version.files.GetProjectLatestVersionFromByHash.GetProjectLatestVersionFromByHashRequest;
import masecla.modrinth4j.endpoints.version.files.GetVersionByHash.GetVersionByHashRequest;
import masecla.modrinth4j.endpoints.version.files.GetVersionsFromHashes.GetVersionsFromHashesRequest;
import masecla.modrinth4j.endpoints.version.files.GetVersionsFromHashes.HashProjectVersionMap;
import masecla.modrinth4j.model.version.FileHash;
import masecla.modrinth4j.model.version.ProjectVersion;

@AllArgsConstructor
public class VersionFilesEndpoints {
    private Gson gson;
    private HttpClient client;

    public CompletableFuture<ProjectVersion> getVersionByHash(FileHash algorithm, String hash) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("hash", hash);

        return new GetVersionByHash(client, gson).sendRequest(new GetVersionByHashRequest(algorithm), parameters);
    }

    public CompletableFuture<HashProjectVersionMap> getVersionByHash(FileHash algorithm, String... hashes) {
        return new GetVersionsFromHashes(client, gson)
                .sendRequest(new GetVersionsFromHashesRequest(algorithm, hashes));
    }

    public CompletableFuture<EmptyResponse> deleteFileByHash(String hash, FileHash algorithm) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("hash", hash);

        return new DeleteFileByHash(client, gson).sendRequest(new DeleteFileByHashRequest(algorithm), parameters);
    }

    public CompletableFuture<ProjectVersion> getLatestVersionByHash(String hash,
            GetProjectLatestVersionFromByHashRequest request) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("hash", hash);

        return new GetProjectLatestVersionFromByHash(client, gson).sendRequest(request, parameters);
    }

}
