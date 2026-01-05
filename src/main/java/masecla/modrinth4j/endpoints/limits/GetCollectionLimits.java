package masecla.modrinth4j.endpoints.limits;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.model.limits.UserLimits;

/**
 * Endpoint for getting collection creation limits.
 */
public class GetCollectionLimits extends Endpoint<UserLimits, EmptyRequest> {

    public GetCollectionLimits(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/v3/limits/collections";
    }

    @Override
    public String getMethod() {
        return "GET";
    }

    @Override
    public TypeToken<UserLimits> getResponseClass() {
        return TypeToken.get(UserLimits.class);
    }

    @Override
    public TypeToken<EmptyRequest> getRequestClass() {
        return TypeToken.get(EmptyRequest.class);
    }
}
