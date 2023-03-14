package masecla.modrinth4j.endpoints.version;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.model.version.ProjectVersion;

/**
 * This endpoint will get a version for a project by the version number.
 */
public class GetVersionByVersionNumber extends Endpoint<ProjectVersion, EmptyRequest> {

    /**
     * Creates a new instance of the endpoint.
     * 
     * @param client The HTTP client to use.
     * @param gson   The GSON instance to use.
     */
    public GetVersionByVersionNumber(HttpClient client, Gson gson) {
        super(client, gson);
    }

    /**
     * Returns the endpoint.
     */
    @Override
    public String getEndpoint() {
        return "/project/{slug}/version/{versionNumber}";
    }

    /**
     * Returns the request class.
     * 
     * @return - The {@link TypeToken} for the response.
     */
    @Override
    public TypeToken<EmptyRequest> getRequestClass() {
        return TypeToken.get(EmptyRequest.class);
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
}
