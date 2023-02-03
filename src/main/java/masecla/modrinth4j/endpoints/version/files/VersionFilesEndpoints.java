package masecla.modrinth4j.endpoints.version.files;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;
import masecla.modrinth4j.endpoints.version.files.DeleteFileByHash.DeleteFileByHashRequest;
import masecla.modrinth4j.endpoints.version.files.GetProjectLatestVersionFromHash.GetProjectLatestVersionFromHashRequest;
import masecla.modrinth4j.endpoints.version.files.GetProjectLatestVersionsFromHashes.GetProjectLatestVersionsFromHashesRequest;
import masecla.modrinth4j.endpoints.version.files.GetVersionByHash.GetVersionByHashRequest;
import masecla.modrinth4j.endpoints.version.files.GetVersionsFromHashes.GetVersionsFromHashesRequest;
import masecla.modrinth4j.model.version.FileHash;
import masecla.modrinth4j.model.version.ProjectVersion;
import masecla.modrinth4j.model.version.files.HashProjectVersionMap;

/**
 * This class is used to represent the endpoints for version files.
 */
@AllArgsConstructor
public class VersionFilesEndpoints {
    /** The Gson instance to use */
    private Gson gson;

    /** The HTTP client to use */
    private HttpClient client;

    /**
     * This endpoint is used to get the latest version of a project from a hash.
     * 
     * @param algorithm The algorithm to use
     * @param hash      The hash to use
     * @return A {@link CompletableFuture} that will return the latest version of
     *         the project with the given hash.
     */
    public CompletableFuture<ProjectVersion> getVersionByHash(FileHash algorithm, String hash) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("hash", hash);

        return new GetVersionByHash(client, gson).sendRequest(new GetVersionByHashRequest(algorithm), parameters);
    }

    /**
     * This endpoint is used to get the latest version of a project from a hash.
     * 
     * @param algorithm - The algorithm to use
     * @param hashes    - The hashes to use
     * @return - A {@link CompletableFuture} that will return the latest version of
     *         the project with the given hash.
     */
    public CompletableFuture<HashProjectVersionMap> getVersionByHash(FileHash algorithm, List<String> hashes) {
        return new GetVersionsFromHashes(client, gson)
                .sendRequest(new GetVersionsFromHashesRequest(algorithm, hashes));
    }

    /**
     * This endpoint is used to get the latest version of a project from a hash.
     * 
     * @param algorithm - The algorithm to use
     * @param hashes    - The hashes to use
     * @return - A {@link CompletableFuture} that will return the latest version of
     *         the project with the given hash.
     */
    public CompletableFuture<HashProjectVersionMap> getVersionByHash(FileHash algorithm, String... hashes) {
        return this.getVersionByHash(algorithm, Arrays.asList(hashes));
    }

    /**
     * This endpoint is used to delete a file from a project version.
     * 
     * @param algorithm The algorithm to use
     * @param hash      The hash to use
     * @return A {@link CompletableFuture} that will return an empty response.
     */
    public CompletableFuture<EmptyResponse> deleteFileByHash(FileHash algorithm, String hash) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("hash", hash);

        return new DeleteFileByHash(client, gson).sendRequest(new DeleteFileByHashRequest(algorithm), parameters);
    }

    /**
     * This endpoint is used to get the latest version of a project from a hash.
     * 
     * @param algorithm The algorithm to use
     * @param hash      The hash to use
     * @param request   The request to use
     * @return A {@link CompletableFuture} that will return the latest version of
     *         the project with the given hash.
     */
    public CompletableFuture<ProjectVersion> getLatestVersionByHash(
            FileHash algorithm, String hash,
            GetProjectLatestVersionFromHashRequest request) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("hash", hash);
        parameters.put("algorithm", algorithm.name().toLowerCase());

        return new GetProjectLatestVersionFromHash(client, gson).sendRequest(request, parameters);
    }

    /**
     * This endpoint is used to get the latest version of a project from a hash.
     * 
     * @param request The request to use
     * @return A {@link CompletableFuture} that will return the latest version of
     *         the project with the given hash.
     */
    public CompletableFuture<HashProjectVersionMap> getLatestVersionsByHash(
            GetProjectLatestVersionsFromHashesRequest request) {
        return new GetProjectLatestVersionsFromHashes(client, gson).sendRequest(request);
    }

}
