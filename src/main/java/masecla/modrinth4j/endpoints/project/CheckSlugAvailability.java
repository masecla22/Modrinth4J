package masecla.modrinth4j.endpoints.project;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.endpoints.project.CheckSlugAvailability.CheckSlugAvailabilityResponse;

/**
 * This endpoint is used to check if a slug is available.
 */
public class CheckSlugAvailability extends Endpoint<CheckSlugAvailabilityResponse, EmptyRequest> {

    /**
     * Creates a new instance of the endpoint.
     * 
     * @param client The client to use.
     * @param gson   The gson instance to use.
     */
    public CheckSlugAvailability(HttpClient client, Gson gson) {
        super(client, gson);
    }

    /**
     * This class is used to represent the response.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CheckSlugAvailabilityResponse {
        private String id;
    }

    /**
     * Returns the endpoint.
     */
    public String getEndpoint() {
        return "/project/{id}/check";
    }

    /**
     * Returns the request class.
     */
    @Override
    public TypeToken<EmptyRequest> getRequestClass() {
        return TypeToken.get(EmptyRequest.class);
    }

    /**
     * Returns the response class.
     */
    @Override
    public TypeToken<CheckSlugAvailabilityResponse> getResponseClass() {
        return TypeToken.get(CheckSlugAvailabilityResponse.class);
    }

    /**
     * Returns if the endpoint requires a body.
     */
    @Override
    public boolean requiresBody() {
        return false;
    }
}
