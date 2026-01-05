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
 * Endpoint for modifying an organization.
 */
public class ModifyOrganization extends Endpoint<EmptyResponse, ModifyOrganization.ModifyOrganizationRequest> {

    public ModifyOrganization(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/v3/organization/{id}";
    }

    @Override
    public String getMethod() {
        return "PATCH";
    }

    @Override
    public TypeToken<EmptyResponse> getResponseClass() {
        return TypeToken.get(EmptyResponse.class);
    }

    @Override
    public TypeToken<ModifyOrganizationRequest> getRequestClass() {
        return TypeToken.get(ModifyOrganizationRequest.class);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ModifyOrganizationRequest {
        /** The new slug for the organization (optional) */
        private String slug;
        
        /** The new name for the organization (optional) */
        private String name;
        
        /** The new description for the organization (optional) */
        private String description;
    }
}
