package masecla.modrinth4j.endpoints.collection;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;

/**
 * Endpoint for deleting a collection's icon.
 */
public class DeleteCollectionIcon extends Endpoint<EmptyResponse, EmptyRequest> {

    /**
     * Creates a new instance of this endpoint.
     * 
     * @param client - The client to use for requests
     * @param gson   - The gson instance to use for serialization
     */
    public DeleteCollectionIcon(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/v3/collection/{id}/icon";
    }

    @Override
    public String getMethod() {
        return "DELETE";
    }

    @Override
    public boolean requiresBody() {
        return false;
    }

    @Override
    public TypeToken<EmptyResponse> getResponseClass() {
        return TypeToken.get(EmptyResponse.class);
    }

    @Override
    public TypeToken<EmptyRequest> getRequestClass() {
        return TypeToken.get(EmptyRequest.class);
    }

    @Override
    protected String getReplacedUrl(EmptyRequest request, Map<String, String> parameters) {
        String url = getEndpoint();
        if (parameters != null && parameters.containsKey("id")) {
            url = url.replace("{id}", parameters.get("id"));
        }
        return url;
    }
}
