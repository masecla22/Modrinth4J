package masecla.modrinth4j.endpoints.version;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.version.GetVersions.GetVersionsRequest;
import masecla.modrinth4j.model.version.ProjectVersion;

/**
 * This endpoint is used to get a list of versions.
 */
public class GetVersions extends Endpoint<List<ProjectVersion>, GetVersionsRequest> {

    /**
     * This class is used to represent the request body.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetVersionsRequest {
        private List<String> ids;
    }

    /**
     * Creates a new instance of the endpoint.
     *
     * @param client The HTTP client to use.
     * @param gson   The GSON instance to use.
     */
    public GetVersions(HttpClient client, Gson gson) {
        super(client, gson);
    }

    /**
     * Returns the endpoint.
     */
    @Override
    public String getEndpoint() {
        return "/versions";
    }

    /**
     * Returns the request class.
     *
     * @return - The {@link TypeToken} for the response.
     */
    @Override
    public TypeToken<GetVersionsRequest> getRequestClass() {
        return TypeToken.get(GetVersionsRequest.class);
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
     * Returns whether the request body is JSON.
     */
    @Override
    public boolean isJsonBody() {
        return false;
    }
}
