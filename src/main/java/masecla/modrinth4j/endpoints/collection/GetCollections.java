package masecla.modrinth4j.endpoints.collection;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.model.collection.ModrinthCollection;

/**
 * Endpoint for getting multiple collections.
 */
public class GetCollections extends Endpoint<List<ModrinthCollection>, GetCollections.GetCollectionsRequest> {

    /**
     * Creates a new instance of this endpoint.
     * 
     * @param client - The client to use for requests
     * @param gson   - The gson instance to use for serialization
     */
    public GetCollections(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/v3/collections";
    }

    @Override
    public String getMethod() {
        return "GET";
    }

    @Override
    public boolean requiresBody() {
        return true;
    }

    @Override
    public boolean isJsonBody() {
        return false;
    }

    @Override
    public TypeToken<List<ModrinthCollection>> getResponseClass() {
        return new TypeToken<List<ModrinthCollection>>() {};
    }

    @Override
    public TypeToken<GetCollectionsRequest> getRequestClass() {
        return TypeToken.get(GetCollectionsRequest.class);
    }

    /**
     * Request for getting multiple collections.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetCollectionsRequest {
        /** The IDs of the collections to fetch, as a JSON array string */
        private List<String> ids;
    }
}
