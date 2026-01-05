package masecla.modrinth4j.endpoints.collection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.collection.CreateCollection.CreateCollectionRequest;
import masecla.modrinth4j.endpoints.collection.GetCollections.GetCollectionsRequest;
import masecla.modrinth4j.endpoints.collection.ModifyCollection.ModifyCollectionRequest;
import masecla.modrinth4j.endpoints.collection.ChangeCollectionIcon.ChangeCollectionIconRequest;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;
import masecla.modrinth4j.model.collection.ModrinthCollection;

/**
 * Endpoints for managing collections.
 */
@AllArgsConstructor
public class CollectionEndpoints {
    /** The Gson instance to use */
    private Gson gson;
    /** The HTTP client to use */
    private HttpClient client;

    /**
     * Creates a new collection.
     * 
     * @param request - The request containing collection details
     * @return A {@link CompletableFuture} that will return the created collection
     */
    public CompletableFuture<ModrinthCollection> create(CreateCollectionRequest request) {
        return new CreateCollection(client, gson).sendRequest(request);
    }

    /**
     * Gets a single collection by its ID.
     * 
     * @param collectionId - The ID of the collection to fetch
     * @return A {@link CompletableFuture} that will return the collection
     */
    public CompletableFuture<ModrinthCollection> get(String collectionId) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", collectionId);
        return new GetCollection(client, gson).sendRequest(new EmptyRequest(), parameters);
    }

    /**
     * Gets multiple collections by their IDs.
     * 
     * @param collectionIds - The IDs of the collections to fetch
     * @return A {@link CompletableFuture} that will return the collections
     */
    public CompletableFuture<List<ModrinthCollection>> get(List<String> collectionIds) {
        return new GetCollections(client, gson)
                .sendRequest(new GetCollectionsRequest(collectionIds));
    }

    /**
     * Gets multiple collections by their IDs.
     * 
     * @param collectionIds - The IDs of the collections to fetch
     * @return A {@link CompletableFuture} that will return the collections
     */
    public CompletableFuture<List<ModrinthCollection>> get(String... collectionIds) {
        return this.get(Arrays.asList(collectionIds));
    }

    /**
     * Modifies an existing collection.
     * 
     * @param collectionId - The ID of the collection to modify
     * @param request      - The modifications to apply
     * @return A {@link CompletableFuture} that will return an empty response
     */
    public CompletableFuture<EmptyResponse> modify(String collectionId, ModifyCollectionRequest request) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", collectionId);
        return new ModifyCollection(client, gson).sendRequest(request, parameters);
    }

    /**
     * Deletes a collection.
     * 
     * @param collectionId - The ID of the collection to delete
     * @return A {@link CompletableFuture} that will return an empty response
     */
    public CompletableFuture<EmptyResponse> delete(String collectionId) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", collectionId);
        return new DeleteCollection(client, gson).sendRequest(new EmptyRequest(), parameters);
    }

    /**
     * Changes the icon of a collection.
     * 
     * @param collectionId - The ID of the collection to change the icon for
     * @param file         - The file to use as the icon
     * @return A {@link CompletableFuture} that will return an empty response
     * @throws FileNotFoundException - If the file is not found
     */
    public CompletableFuture<EmptyResponse> changeIcon(String collectionId, File file)
            throws FileNotFoundException {
        return changeIcon(collectionId, new FileInputStream(file), file.getName());
    }

    /**
     * Changes the icon of a collection.
     * 
     * @param collectionId - The ID of the collection to change the icon for
     * @param stream       - The stream to use as the icon
     * @param fileName     - The name of the file
     * @return A {@link CompletableFuture} that will return an empty response
     */
    public CompletableFuture<EmptyResponse> changeIcon(String collectionId, InputStream stream, String fileName) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", collectionId);

        return new ChangeCollectionIcon(client, gson)
                .sendRequest(new ChangeCollectionIconRequest(stream, fileName), parameters);
    }

    /**
     * Deletes the icon of a collection.
     * 
     * @param collectionId - The ID of the collection to delete the icon for
     * @return A {@link CompletableFuture} that will return an empty response
     */
    public CompletableFuture<EmptyResponse> deleteIcon(String collectionId) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", collectionId);

        return new DeleteCollectionIcon(client, gson).sendRequest(new EmptyRequest(), parameters);
    }
}
