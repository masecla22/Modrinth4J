package masecla.modrinth4j.endpoints.collection;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;
import masecla.modrinth4j.model.collection.CollectionStatus;

/**
 * Endpoint for modifying a collection.
 */
public class ModifyCollection extends Endpoint<EmptyResponse, ModifyCollection.ModifyCollectionRequest> {

    /**
     * Creates a new instance of this endpoint.
     * 
     * @param client - The client to use for requests
     * @param gson   - The gson instance to use for serialization
     */
    public ModifyCollection(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/v3/collection/{id}";
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
    public TypeToken<ModifyCollectionRequest> getRequestClass() {
        return TypeToken.get(ModifyCollectionRequest.class);
    }

    @Override
    protected String getReplacedUrl(ModifyCollectionRequest request, Map<String, String> parameters) {
        String url = getEndpoint();
        if (parameters != null && parameters.containsKey("id")) {
            url = url.replace("{id}", parameters.get("id"));
        }
        return url;
    }

    /**
     * Request for modifying a collection.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ModifyCollectionRequest {
        /** The new name for the collection (3-64 characters) */
        private String name;
        
        /** The new description for the collection (3-256 characters) */
        private String description;
        
        /** The new status for the collection */
        private CollectionStatus status;
        
        /** New projects to add to the collection (max 1024) */
        private List<String> newProjects;
    }
}
