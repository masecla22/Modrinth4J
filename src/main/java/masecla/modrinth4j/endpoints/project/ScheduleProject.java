package masecla.modrinth4j.endpoints.project;

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
import masecla.modrinth4j.endpoints.project.ScheduleProject.ScheduleProjectRequest;
import masecla.modrinth4j.model.project.ProjectStatus;

public class ScheduleProject extends Endpoint<EmptyResponse, ScheduleProjectRequest> {

    /**
     * Represents a request to schedule a project.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScheduleProjectRequest {
        /** The time at which the project should be scheduled. */
        private Instant time;

        /** The requested status of the project */
        private ProjectStatus requestedStatus;
    }

    /**
     * Creates a new instance of the endpoint.
     * 
     * @param client - the client to use
     * @param gson   - the gson instance to use
     */
    public ScheduleProject(HttpClient client, Gson gson) {
        super(client, gson);
    }

    /**
     * Returns the endpoint
     * 
     * @return - The endpoint
     */
    @Override
    public String getEndpoint() {
        return "/projects/{slug}/schedule";
    }

    /**
     * Returns the request class.
     *
     * @return - The {@link TypeToken} for the request.
     */
    @Override
    public TypeToken<ScheduleProjectRequest> getRequestClass() {
        return TypeToken.get(ScheduleProjectRequest.class);
    }

    /**
     * Returns the response class.
     *
     * @return - The {@link TypeToken} for the response.
     */
    @Override
    public TypeToken<EmptyResponse> getResponseClass() {
        return TypeToken.get(EmptyResponse.class);
    }

    @Override
    public String getMethod() {
        return "POST";
    }
}
