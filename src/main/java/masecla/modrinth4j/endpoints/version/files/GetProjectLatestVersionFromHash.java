package masecla.modrinth4j.endpoints.version.files;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.version.files.GetProjectLatestVersionFromHash.GetProjectLatestVersionFromHashRequest;
import masecla.modrinth4j.model.version.ProjectVersion;

/**
 * This endpoint is used to get the latest version from a hash.
 */
public class GetProjectLatestVersionFromHash
        extends Endpoint<ProjectVersion, GetProjectLatestVersionFromHashRequest> {

    /**
     * This class is used to represent the request.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetProjectLatestVersionFromHashRequest {
        /** The loaders to check. */
        @Default
        private List<String> loaders = new ArrayList<>();

        /** The game versions to check. */
        @Default
        private List<String> gameVersions = new ArrayList<>();
    }

    /**
     * Creates a new instance of the endpoint.
     * 
     * @param client The client to use.
     * @param gson   The gson instance to use.
     */
    public GetProjectLatestVersionFromHash(HttpClient client, Gson gson) {
        super(client, gson);
    }

    /**
     * Returns the endpoint.
     */
    @Override
    public String getEndpoint() {
        // Why would you make a fetching endpoint a POST request with the end being
        // /update. God damn it Modrinth.
        return "/version_file/{hash}/update?algorithm={algorithm}";
    }

    /**
     * Returns the request class.
     *
     * @return - The {@link TypeToken} for the response.
     */
    @Override
    public TypeToken<GetProjectLatestVersionFromHashRequest> getRequestClass() {
        return TypeToken.get(GetProjectLatestVersionFromHashRequest.class);
    }

    /**
     * Returns the response class.
     *
     * @return - The {@link TypeToken} for the response.
     */
    @Override
    public TypeToken<ProjectVersion> getResponseClass() {
        return TypeToken.get(ProjectVersion.class);
    }

    /**
     * Returns the method.
     *
     * @return - The HTTP method being used
     */
    @Override
    public String getMethod() {
        return "POST";
    }
}
