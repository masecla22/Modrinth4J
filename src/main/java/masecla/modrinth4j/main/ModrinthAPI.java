package masecla.modrinth4j.main;

import java.util.concurrent.CompletableFuture;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.client.agent.UserAgent;
import masecla.modrinth4j.client.instances.UnlimitedHttpClient;
import masecla.modrinth4j.endpoints.SearchEndpoint;
import masecla.modrinth4j.endpoints.SearchEndpoint.SearchRequest;
import masecla.modrinth4j.endpoints.SearchEndpoint.SearchResponse;
import masecla.modrinth4j.endpoints.project.ProjectEndpoints;
import masecla.modrinth4j.endpoints.tags.TagsEndpoints;
import masecla.modrinth4j.endpoints.teams.TeamsEndpoints;
import masecla.modrinth4j.endpoints.user.UserEndpoints;
import masecla.modrinth4j.endpoints.version.VersionEndpoints;
import masecla.modrinth4j.model.search.FacetCollection;
import masecla.modrinth4j.model.search.FacetCollection.FacetAdapter;
import masecla.modrinth4j.model.team.ModrinthPermissionMask;
import masecla.modrinth4j.model.team.ModrinthPermissionMask.ModrinthPermissionMaskAdapter;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ModrinthAPI {

    @NonNull
    private HttpClient client;
    @NonNull
    private String apiKey;

    private Gson gson;

    public static ModrinthAPI unlimited(UserAgent agent, String apiKey) {
        HttpClient client = new UnlimitedHttpClient(agent, apiKey);
        ModrinthAPI result = new ModrinthAPI(client, apiKey);

        result.initializeGson();
        return result;
    }

    public static ModrinthAPI unlimited(UserAgent agent, String url, String apiKey) {
        HttpClient client = new UnlimitedHttpClient(agent, url, apiKey);
        ModrinthAPI result = new ModrinthAPI(client, apiKey);

        result.initializeGson();
        return result;
    }

    private void initializeGson() {
        this.gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(FacetCollection.class, new FacetAdapter())
                .registerTypeAdapter(ModrinthPermissionMask.class, new ModrinthPermissionMaskAdapter())
                .create();
    }

    public CompletableFuture<SearchResponse> search(SearchRequest request) {
        SearchEndpoint endpoint = new SearchEndpoint(client, gson);
        return endpoint.sendRequest(request);
    }

    public ProjectEndpoints projects() {
        return new ProjectEndpoints(gson, client);
    }

    public VersionEndpoints versions() {
        return new VersionEndpoints(gson, client);
    }

    public UserEndpoints users() {
        return new UserEndpoints(gson, client);
    }

    public TeamsEndpoints teams() {
        return new TeamsEndpoints(gson, client);
    }

    public TagsEndpoints tags() {
        return new TagsEndpoints(gson, client);
    }
}