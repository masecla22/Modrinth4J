package masecla.modrinth4j.endpoints.tags;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.model.tags.Category;
import masecla.modrinth4j.model.tags.DonationPlatform;
import masecla.modrinth4j.model.tags.GameVersion;
import masecla.modrinth4j.model.tags.License;
import masecla.modrinth4j.model.tags.Loader;

/**
 * This class contains all the endpoints for the tags section of the API.
 */
@AllArgsConstructor
public class TagsEndpoints {
    /** The gson instance to use. */
    private Gson gson;
    /** The client to use. */
    private HttpClient client;

    /**
     * This endpoint is used to get a list of all the categories.
     * 
     * @return - A {@link CompletableFuture} that will return a {@link List} of
     *         {@link Category} objects.
     */
    public CompletableFuture<List<Category>> getCategories() {
        return new GetCategories(client, gson).sendRequest(new EmptyRequest());
    }

    /**
     * This endpoint is used to get a list of all the donation platforms.
     * 
     * @return - A {@link CompletableFuture} that will return a {@link List} of
     *         {@link DonationPlatform} objects.
     */
    public CompletableFuture<List<DonationPlatform>> getDonationPlatforms() {
        return new GetDonationPlatforms(client, gson).sendRequest(new EmptyRequest());
    }

    /**
     * This endpoint is used to get a list of all the loaders.
     * 
     * @return - A {@link CompletableFuture} that will return a {@link List} of
     *         {@link Loader} objects.
     */
    public CompletableFuture<List<Loader>> getLoaders() {
        return new GetLoaders(client, gson).sendRequest(new EmptyRequest());
    }

    /**
     * This endpoint is used to get a list of all the game versions.
     * 
     * @return - A {@link CompletableFuture} that will return a {@link List} of
     *         {@link GameVersion} objects.
     */
    public CompletableFuture<List<GameVersion>> getGameVersions() {
        return new GetGameVersions(client, gson).sendRequest(new EmptyRequest());
    }

    /**
     * This endpoint is used to get a list of all the licenses.
     * 
     * @return - A {@link CompletableFuture} that will return a {@link List} of
     *         {@link License} objects.
     */
    public CompletableFuture<List<License>> getLicenses() {
        return new GetLicenses(client, gson).sendRequest(new EmptyRequest());
    }

    /**
     * This endpoint is used to get a list of all the report types.
     * 
     * @return - A {@link CompletableFuture} that will return a {@link List} of
     *         {@link String} objects.
     */
    public CompletableFuture<List<String>> getReportTypes() {
        return new GetReportTypes(client, gson).sendRequest(new EmptyRequest());
    }
}
