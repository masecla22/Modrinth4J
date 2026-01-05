package masecla.modrinth4j.endpoints.organization;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.model.organization.ModrinthOrganization;

/**
 * Endpoint for creating a new organization.
 */
public class CreateOrganization extends Endpoint<ModrinthOrganization, CreateOrganization.CreateOrganizationRequest> {

    public CreateOrganization(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/v3/organization";
    }

    @Override
    public String getMethod() {
        return "POST";
    }

    @Override
    public TypeToken<ModrinthOrganization> getResponseClass() {
        return TypeToken.get(ModrinthOrganization.class);
    }

    @Override
    public TypeToken<CreateOrganizationRequest> getRequestClass() {
        return TypeToken.get(CreateOrganizationRequest.class);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateOrganizationRequest {
        /** The URL slug for the organization */
        private String slug;
        
        /** The name of the organization */
        private String name;
        
        /** The description of the organization */
        private String description;
    }
}
