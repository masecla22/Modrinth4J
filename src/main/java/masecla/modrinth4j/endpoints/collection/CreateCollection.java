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
 * Endpoint for creating a new collection.
 */
public class CreateCollection extends Endpoint<ModrinthCollection, CreateCollection.CreateCollectionRequest> {

    /**
     * Creates a new instance of this endpoint.
     * 
     * @param client - The client to use for requests
     * @param gson   - The gson instance to use for serialization
     */
    public CreateCollection(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/v3/collection";
    }

    @Override
    public String getMethod() {
        return "POST";
    }

    @Override
    public TypeToken<ModrinthCollection> getResponseClass() {
        return TypeToken.get(ModrinthCollection.class);
    }

    @Override
    public TypeToken<CreateCollectionRequest> getRequestClass() {
        return TypeToken.get(CreateCollectionRequest.class);
    }

    /**
     * Request parameters for creating a collection.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateCollectionRequest {
        /** The title or name of the collection (3-64 characters) */
        private String name;
        
        /** A short description of the collection (3-255 characters) */
        private String description;
        
        /** A list of initial projects to add to the collection (max 1024) */
        private List<String> projects;
    }
}
