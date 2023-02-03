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
import masecla.modrinth4j.client.instances.RatelimitedHttpClient;
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

/**
 * The main class for the Modrinth API.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ModrinthAPI {

    /** The HTTP Client to use */
    @NonNull
    private HttpClient client;

    /** The API key to use */
    @NonNull
    private String apiKey;

    /** The Gson instance to use */
    private Gson gson;

    /**
     * Returns a client which will send unlimited requests.
     * 
     * @deprecated - Use {@link #rateLimited(UserAgent, String)} instead.
     */
    @Deprecated
    public static ModrinthAPI unlimited(UserAgent agent, String apiKey) {
        HttpClient client = new UnlimitedHttpClient(agent, apiKey);
        ModrinthAPI result = new ModrinthAPI(client, apiKey);

        result.initializeGson();
        return result;
    }

    /**
     * Returns a client which will send unlimited requests.
     * 
     * @deprecated - Use {@link #rateLimited(UserAgent, String, String)} instead.
     */
    @Deprecated
    public static ModrinthAPI unlimited(UserAgent agent, String url, String apiKey) {
        HttpClient client = new UnlimitedHttpClient(agent, url, apiKey);
        ModrinthAPI result = new ModrinthAPI(client, apiKey);

        result.initializeGson();
        return result;
    }

    /**
     * Returns a client which will send requests and adjust speed based on rate limits
     * 
     * @param agent  - The user agent to use
     * @param apiKey - The API key to use
     * 
     * @return - A client which will send requests and adjust speed based on rate
     */
    public static ModrinthAPI rateLimited(UserAgent agent, String apiKey) {
        HttpClient client = new RatelimitedHttpClient(agent, apiKey);
        ModrinthAPI result = new ModrinthAPI(client, apiKey);

        result.initializeGson();
        return result;
    }

    /**
     * Returns a client which will send requests and adjust speed based on rate limits
     * 
     * @param agent  - The user agent to use
     * @param url    - The base URL to use
     * @param apiKey - The API key to use
     * 
     * @return - A client which will send requests and adjust speed based on rate
     */
    public static ModrinthAPI rateLimited(UserAgent agent, String url, String apiKey) {
        HttpClient client = new RatelimitedHttpClient(agent, url, apiKey);
        ModrinthAPI result = new ModrinthAPI(client, apiKey);

        result.initializeGson();
        return result;
    }

    /**
     * This method initializes the Gson instance.
     */
    private void initializeGson() {
        this.gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(FacetCollection.class, new FacetAdapter())
                .registerTypeAdapter(ModrinthPermissionMask.class, new ModrinthPermissionMaskAdapter())
                .create();
    }

    /** 
     * Executes a search request.
     * 
     * @param request - The request to execute
     * @return - A future which will return the response
     */
    public CompletableFuture<SearchResponse> search(SearchRequest request) {
        SearchEndpoint endpoint = new SearchEndpoint(client, gson);
        return endpoint.sendRequest(request);
    }

    /**
     * Returns the project endpoints.
     * @return - The project endpoints
     */
    public ProjectEndpoints projects() {
        return new ProjectEndpoints(gson, client);
    }

    /**
     * Returns the version endpoints.
     * @return - The version endpoints
     */
    public VersionEndpoints versions() {
        return new VersionEndpoints(gson, client);
    }

    /**
     * Returns the user endpoints.
     * @return - The user endpoints
     */
    public UserEndpoints users() {
        return new UserEndpoints(gson, client);
    }

    /**
     * Returns the team endpoints.
     * @return - The team endpoints
     */
    public TeamsEndpoints teams() {
        return new TeamsEndpoints(gson, client);
    }

    /**
     * Returns the tag endpoints.
     * @return - The tag endpoints
     */
    public TagsEndpoints tags() {
        return new TagsEndpoints(gson, client);
    }
}