package masecla.modrinth4j.main;

import java.util.concurrent.CompletableFuture;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.client.instances.UnlimitedHttpClient;
import masecla.modrinth4j.endpoints.SearchEndpoint;
import masecla.modrinth4j.endpoints.SearchEndpoint.SearchRequest;
import masecla.modrinth4j.endpoints.SearchEndpoint.SearchResponse;
import masecla.modrinth4j.endpoints.project.ProjectEndpoints;
import masecla.modrinth4j.model.search.FacetCollection;
import masecla.modrinth4j.model.search.FacetCollection.FacetAdapter;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ModrinthAPI {
    @NonNull
    private HttpClient client;
    @NonNull
    private String apiKey;

    private Gson gson;

    public static ModrinthAPI unlimited(String apiKey) {
        HttpClient client = new UnlimitedHttpClient(apiKey);
        ModrinthAPI result = new ModrinthAPI(client, apiKey);

        result.initializeGson();
        return result;
    }

    private void initializeGson() {
        this.gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(FacetCollection.class, new FacetAdapter())
                .create();
    }

    public CompletableFuture<SearchResponse> search(SearchRequest request) {
        SearchEndpoint endpoint = new SearchEndpoint(client, gson);
        return endpoint.sendRequest(request);
    }

    public ProjectEndpoints projects() {
        return new ProjectEndpoints(gson, client);
    }
}