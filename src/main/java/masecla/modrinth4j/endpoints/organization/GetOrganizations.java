package masecla.modrinth4j.endpoints.organization;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.model.organization.ModrinthOrganization;

/**
 * Endpoint for getting multiple organizations.
 */
public class GetOrganizations extends Endpoint<List<ModrinthOrganization>, GetOrganizations.GetOrganizationsRequest> {

    public GetOrganizations(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/v3/organizations";
    }

    @Override
    public String getMethod() {
        return "GET";
    }

    @Override
    public TypeToken<List<ModrinthOrganization>> getResponseClass() {
        return new TypeToken<List<ModrinthOrganization>>() {};
    }

    @Override
    public TypeToken<GetOrganizationsRequest> getRequestClass() {
        return TypeToken.get(GetOrganizationsRequest.class);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetOrganizationsRequest {
        /** List of organization IDs to fetch */
        private List<String> ids;
    }
}
