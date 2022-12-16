package masecla.modrinth4j.endpoints.project;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.endpoints.project.CheckSlugAvailability.CheckSlugAvailabilityResponse;

public class CheckSlugAvailability extends Endpoint<CheckSlugAvailabilityResponse, EmptyRequest> {

    public CheckSlugAvailability(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CheckSlugAvailabilityResponse {
        private String id;
    }

    public String getEndpoint() {
        return "/project/{id}/check";
    }

    @Override
    public Class<EmptyRequest> getRequestClass() {
        return EmptyRequest.class;
    }

    @Override
    public Class<CheckSlugAvailabilityResponse> getResponseClass() {
        return CheckSlugAvailabilityResponse.class;
    }

    @Override
    public boolean requiresBody() {
        return false;
    }

}
