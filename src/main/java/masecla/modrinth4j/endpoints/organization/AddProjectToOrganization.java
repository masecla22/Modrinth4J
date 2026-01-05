package masecla.modrinth4j.endpoints.organization;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;

/**
 * Endpoint for adding a project to an organization.
 */
public class AddProjectToOrganization extends Endpoint<EmptyResponse, AddProjectToOrganization.AddProjectRequest> {

    public AddProjectToOrganization(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/v3/organization/{id}/projects";
    }

    @Override
    public String getMethod() {
        return "POST";
    }

    @Override
    public TypeToken<EmptyResponse> getResponseClass() {
        return TypeToken.get(EmptyResponse.class);
    }

    @Override
    public TypeToken<AddProjectRequest> getRequestClass() {
        return TypeToken.get(AddProjectRequest.class);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddProjectRequest {
        /** The ID of the project to add */
        private String projectId;
    }
}
