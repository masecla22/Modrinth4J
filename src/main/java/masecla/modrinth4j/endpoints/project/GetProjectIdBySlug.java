package masecla.modrinth4j.endpoints.project;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.endpoints.project.GetProjectIdBySlug.GetProjectIdBySlugResponse;

/**
 * This endpoint is used to get a project's id by its slug.
 */
public class GetProjectIdBySlug extends Endpoint<GetProjectIdBySlugResponse, EmptyRequest> {

    /**
     * Represents the response from the endpoint.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetProjectIdBySlugResponse {
        private String id;
    }

    /**
     * Creates a new instance of the endpoint.
     * 
     * @param client The client to use.
     * @param gson   The gson instance to use.
     */
    public GetProjectIdBySlug(HttpClient client, Gson gson) {
        super(client, gson);
    }

    /**
     * Returns the endpoint.
     */
    public String getEndpoint() {
        return "/project/{id}/check";
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
    public TypeToken<GetProjectIdBySlugResponse> getResponseClass() {
        return TypeToken.get(GetProjectIdBySlugResponse.class);
    }

    /**
     * Whether or not the endpoint requires a body.
     */
    @Override
    public boolean requiresBody() {
        return false;
    }

}
