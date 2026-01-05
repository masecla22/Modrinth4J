package masecla.modrinth4j.endpoints.organization;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.model.organization.ModrinthOrganization;

/**
 * Endpoint for getting a single organization.
 */
public class GetOrganization extends Endpoint<ModrinthOrganization, EmptyRequest> {

    public GetOrganization(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/v3/organization/{id}";
    }

    @Override
    public String getMethod() {
        return "GET";
    }

    @Override
    public TypeToken<ModrinthOrganization> getResponseClass() {
        return TypeToken.get(ModrinthOrganization.class);
    }

    @Override
    public TypeToken<EmptyRequest> getRequestClass() {
        return TypeToken.get(EmptyRequest.class);
    }
}
