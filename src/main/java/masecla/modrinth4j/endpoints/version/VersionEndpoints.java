package masecla.modrinth4j.endpoints.version;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;
import masecla.modrinth4j.endpoints.version.CreateVersion.CreateVersionRequest;
import masecla.modrinth4j.endpoints.version.GetProjectVersions.GetProjectVersionsRequest;
import masecla.modrinth4j.endpoints.version.GetVersions.GetVersionsRequest;
import masecla.modrinth4j.endpoints.version.ModifyVersion.ModifyVersionRequest;
import masecla.modrinth4j.endpoints.version.files.VersionFilesEndpoints;
import masecla.modrinth4j.model.version.ProjectVersion;

/**
 * Represents the version endpoints.
 */
@AllArgsConstructor
public class VersionEndpoints {
    /** The GSON instance */
    private Gson gson;
    /** The HTTP Client to use */
    private HttpClient httpClient;

    /**
     * Returns the version files endpoints.
     * 
     * @return The version files endpoints.
     */
    public VersionFilesEndpoints files() {
        return new VersionFilesEndpoints(gson, httpClient);
    }

    /**
     * Fetches a list of versions for a project with the given slug, filtered
     * by the given conditions.
     * 
     * @param slug          - The slug of the project to fetch versions for.
     * @param requestObject - The request object to use for filtering
     * @return - A {@link CompletableFuture} that will return a list of versions
     */
    public CompletableFuture<List<ProjectVersion>> getProjectVersions(String slug,
            GetProjectVersionsRequest requestObject) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", slug);

        return new GetProjectVersions(httpClient, gson).sendRequest(requestObject, parameters);
    }

    /**
     * Fetches a version based on the version id
     * 
     * @param versionId - The id of the version to fetch
     * @return CompletableFuture<ProjectVersion> - A {@link CompletableFuture} that
     *         will return a version
     */
    public CompletableFuture<ProjectVersion> getVersion(String versionId) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", versionId);

        return new GetVersion(httpClient, gson).sendRequest(null, parameters);
    }

    /**
     * Fetches multiple versions based on the version ids
     * 
     * @param versionIds - The ids of the versions to fetch
     * @return CompletableFuture<List<ProjectVersion>> - A {@link CompletableFuture}
     *         that will return a list of versions in the same order as the ids.
     *         Putting the same id multiple times will only return one version, but
     *         the order will be preserved.
     * @see #getVersion(String[])
     */
    public CompletableFuture<List<ProjectVersion>> getVersion(List<String> versionIds) {
        return new GetVersions(httpClient, gson).sendRequest(new GetVersionsRequest(versionIds));
    }

    /**
     * Fetches multiple versions based on the version ids
     * 
     * @param versionIds - The ids of the versions to fetch
     * @return CompletableFuture<List<ProjectVersion>> - A {@link CompletableFuture}
     *         that will return a list of versions in the same order as the ids.
     *         Putting the same id multiple times will only return one version, but
     *         the order will be preserved.
     * @see #getVersion(List)
     */
    public CompletableFuture<List<ProjectVersion>> getVersion(String... versionIds) {
        return this.getVersion(Arrays.asList(versionIds));
    }

    /**
     * Modifies a version based on the version id
     * 
     * @param versionId - The id of the version to modify
     * @param request   - The request object to use for modifying
     * @return CompletableFuture<ProjectVersion> - A {@link CompletableFuture} that
     *         will return the modified version object
     */
    public CompletableFuture<ProjectVersion> modifyProjectVersion(String versionId, ModifyVersionRequest request) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", versionId);

        return new ModifyVersion(httpClient, gson).sendRequest(request, parameters);
    }

    /**
     * Deletes a version based on the version id
     * 
     * @param versionId - The id of the version to delete
     * @return CompletableFuture<EmptyResponse> - A {@link CompletableFuture} that
     *         will return an empty response when the version has been deleted
     */
    public CompletableFuture<EmptyResponse> deleteProjectVersion(String versionId) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", versionId);

        return new DeleteVersion(httpClient, gson).sendRequest(null, parameters);
    }

    /**
     * Creates a new version for a project.
     * 
     * @param request - The request object to use for creating
     * @return CompletableFuture<ProjectVersion> - A {@link CompletableFuture} that
     *         will return the created version object once it has been created
     */
    public CompletableFuture<ProjectVersion> createProjectVersion(CreateVersionRequest request) {
        return new CreateVersion(httpClient, gson).sendRequest(request);
    }

    /**
     * Add files to a version.
     * 
     * @param versionId - The id of the version to add files to
     * @param files     - A {@link Map} of file names to {@link InputStream}s which
     *                  represent the file content.
     * @return CompletableFuture<EmptyResponse> - A {@link CompletableFuture} that
     *         will return an empty response when the files have been added
     */
    public CompletableFuture<EmptyResponse> addFilesToVersion(String versionId, Map<String, InputStream> files) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", versionId);

        return new AddFilesToVersion(httpClient, gson).sendRequest(files, parameters);
    }

    /**
     * Add files to version
     * 
     * @param versionId - The id of the version to add files to
     * @param files     - A {@link List} of {@link File}s to add to the version
     * @return CompletableFuture<EmptyResponse> - A {@link CompletableFuture} that
     * @throws FileNotFoundException - If a file in the list does not exist
     */
    public CompletableFuture<EmptyResponse> addFilesToVersion(String versionId, List<File> files)
            throws FileNotFoundException {
        Map<String, InputStream> fileMap = new HashMap<>();
        for (File file : files) {
            fileMap.put(file.getName(), new FileInputStream(file));
        }

        return addFilesToVersion(versionId, fileMap);
    }
}
