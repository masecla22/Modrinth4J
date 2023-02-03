package masecla.modrinth4j.endpoints.version;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.Builder;
import lombok.Data;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.version.GetProjectVersions.GetProjectVersionsRequest;
import masecla.modrinth4j.model.version.ProjectVersion;

/**
 * This endpoint is used to get a list of versions with certain filters.
 */
public class GetProjectVersions extends Endpoint<List<ProjectVersion>, GetProjectVersionsRequest> {
    public GetProjectVersions(HttpClient httpClient, Gson gson) {
        super(httpClient, gson);
    }

    /**
     * This class is used to represent the request.
     */
    @Data
    @Builder
    public static class GetProjectVersionsRequest {
        /** The loaders to filter by. */
        private List<String> loaders;

        /** The game versions to filter by. */
        private List<String> gameVersions;

        /** If the version is featured. */
        private boolean featured;
    }
    
    /**
     * Returns the endpoint.
     */
    @Override
    public String getEndpoint() {
        return "/project/{id}/version";
    }

    /**
     * Returns the response class.
     *
     * @return - The {@link TypeToken} for the response.
     */
    @Override
    public TypeToken<List<ProjectVersion>> getResponseClass() {
        return new TypeToken<List<ProjectVersion>>() {
        };
    }

    /**
     * Returns the request class.
     *
     * @return - The {@link TypeToken} for the response.
     */
    @Override
    public TypeToken<GetProjectVersionsRequest> getRequestClass() {
        return TypeToken.get(GetProjectVersionsRequest.class);
    }

    /**
     * Returns if the request body is JSON.
     */
    @Override
    public boolean isJsonBody() {
        return false;
    }
}
