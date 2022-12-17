package masecla.modrinth4j.endpoints.tags;

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

@AllArgsConstructor
public class TagsEndpoints {
    private Gson gson;
    private HttpClient client;

    public CompletableFuture<Category[]> getCategories() {
        return new GetCategories(client, gson).sendRequest(new EmptyRequest());
    }

    public CompletableFuture<DonationPlatform[]> getDonationPlatforms() {
        return new GetDonationPlatforms(client, gson).sendRequest(new EmptyRequest());
    }

    public CompletableFuture<Loader[]> getLoaders() {
        return new GetLoaders(client, gson).sendRequest(new EmptyRequest());
    }

    public CompletableFuture<GameVersion[]> getGameVersions() {
        return new GetGameVersions(client, gson).sendRequest(new EmptyRequest());
    }

    public CompletableFuture<License[]> getLicenses() {
        return new GetLicenses(client, gson).sendRequest(new EmptyRequest());
    }

    public CompletableFuture<String[]> getReportTypes() {
        return new GetReportTypes(client, gson).sendRequest(new EmptyRequest());
    }
}
