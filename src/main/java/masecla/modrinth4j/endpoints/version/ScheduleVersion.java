package masecla.modrinth4j.endpoints.version;

import java.time.Instant;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;
import masecla.modrinth4j.endpoints.version.ScheduleVersion.ScheduleVersionRequest;
import masecla.modrinth4j.model.project.ProjectStatus;

/**
 * This endpoint is used to schedule a version.
 * 
 * Should be used after creating a version.
 */
public class ScheduleVersion extends Endpoint<EmptyResponse, ScheduleVersionRequest> {

    /**
     * Represents a request to schedule a version.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScheduleVersionRequest {
        /** The time at which the version should be scheduled. */
        private Instant time;

        /** The requested status of the project */
        private ProjectStatus requestedStatus;
    }

    /**
     * Creates a new instance of the {@link ScheduleVersion} endpoint.
     * 
     * @param client The {@link HttpClient} to use.
     * @param gson   The {@link Gson} instance to use.
     */
    public ScheduleVersion(HttpClient client, Gson gson) {
        super(client, gson);
    }

    /**
     * This URL of the endpoint.
     */
    @Override
    public String getEndpoint() {
        return "/version/{id}/schedule";
    }

    /**
     * Returns the request class to use.
     */
    @Override
    public TypeToken<ScheduleVersionRequest> getRequestClass() {
        return TypeToken.get(ScheduleVersionRequest.class);
    }

    /**
     * Returns the response class to use.
     */
    @Override
    public TypeToken<EmptyResponse> getResponseClass() {
        return TypeToken.get(EmptyResponse.class);
    }

    /**
     * Returns the method of the endpoint.
     */
    @Override
    public String getMethod() {
        return "POST";
    }
}
