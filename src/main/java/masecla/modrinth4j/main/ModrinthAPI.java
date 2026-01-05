package masecla.modrinth4j.main;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.NonNull;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.client.agent.UserAgent;
import masecla.modrinth4j.client.instances.RatelimitedHttpClient;
import masecla.modrinth4j.client.instances.UnlimitedHttpClient;
import masecla.modrinth4j.endpoints.SearchEndpoint;
import masecla.modrinth4j.endpoints.SearchEndpoint.SearchRequest;
import masecla.modrinth4j.endpoints.SearchEndpoint.SearchResponse;
import masecla.modrinth4j.endpoints.collection.CollectionEndpoints;
import masecla.modrinth4j.endpoints.friends.FriendsEndpoints;
import masecla.modrinth4j.endpoints.images.ImagesEndpoints;
import masecla.modrinth4j.endpoints.limits.LimitsEndpoints;
import masecla.modrinth4j.endpoints.organization.OrganizationEndpoints;
import masecla.modrinth4j.endpoints.project.ProjectEndpoints;
import masecla.modrinth4j.endpoints.tags.TagsEndpoints;
import masecla.modrinth4j.endpoints.teams.TeamsEndpoints;
import masecla.modrinth4j.endpoints.threads.ThreadsEndpoints;
import masecla.modrinth4j.endpoints.user.UserEndpoints;
import masecla.modrinth4j.endpoints.version.VersionEndpoints;
import masecla.modrinth4j.model.adapters.ISOTimeAdapter;
import masecla.modrinth4j.model.search.FacetCollection;
import masecla.modrinth4j.model.search.FacetCollection.FacetAdapter;
import masecla.modrinth4j.model.team.ModrinthPermissionMask;
import masecla.modrinth4j.model.team.ModrinthPermissionMask.ModrinthPermissionMaskAdapter;

/**
 * The main class for the Modrinth API.
 */
@SuppressWarnings("deprecation")
public class ModrinthAPI {

    /** The HTTP Client to use */
    private HttpClient client;

    private ModrinthAPI(@NonNull HttpClient client) {
        this.client = client;
    }

    /** The Gson instance to use */
    private Gson gson;

    /**
     * Returns a client which will send unlimited requests.
     * 
     * @param agent  - The user agent to use
     * @param apiKey - The API key to use
     * @return - A client which will send unlimited requests.
     * 
     * @deprecated - Use {@link #rateLimited(UserAgent, String)} instead.
     */
    @Deprecated
    public static ModrinthAPI unlimited(UserAgent agent, String apiKey) {
        HttpClient client = new UnlimitedHttpClient(agent, apiKey);
        ModrinthAPI result = new ModrinthAPI(client);

        result.initializeGson();
        return result;
    }

    /**
     * Returns a client which will send unlimited requests.
     * 
     * @param agent  - The user agent to use
     * @param url    - The URL to use
     * @param apiKey - The API key to use
     * @return - A client which will send unlimited requests.
     * 
     * @deprecated - Use {@link #rateLimited(UserAgent, String, String)} instead.
     */
    @Deprecated
    public static ModrinthAPI unlimited(UserAgent agent, String url, String apiKey) {
        HttpClient client = new UnlimitedHttpClient(agent, url, apiKey);
        ModrinthAPI result = new ModrinthAPI(client);

        result.initializeGson();
        return result;
    }

    /**
     * Returns a client which will send unlimited requests.
     * 
     * @param agent   - The user agent to use
     * @param url     - The URL to use
     * @param apiKey  - The API key to use
     * @param timeout - The timeout to use in milliseconds
     * @return - A client which will send unlimited requests.
     * 
     * @deprecated - Use {@link #rateLimited(UserAgent, String, String, long)}
     *             instead.
     */
    @Deprecated
    public static ModrinthAPI unlimited(UserAgent agent, String url, String apiKey, long timeout) {
        HttpClient client = new UnlimitedHttpClient(agent, url, apiKey, timeout);
        ModrinthAPI result = new ModrinthAPI(client);

        result.initializeGson();
        return result;
    }

    /**
     * Returns a client which will send requests and adjust speed based on rate
     * limits
     * 
     * @param agent  - The user agent to use
     * @param apiKey - The API key to use
     * 
     * @return - A client which will send requests and adjust speed based on rate
     */
    public static ModrinthAPI rateLimited(UserAgent agent, String apiKey) {
        HttpClient client = new RatelimitedHttpClient(agent, apiKey);
        ModrinthAPI result = new ModrinthAPI(client);

        result.initializeGson();
        return result;
    }

    /**
     * Returns a client which will send requests and adjust speed based on rate
     * limits
     * 
     * @param agent  - The user agent to use
     * @param url    - The base URL to use
     * @param apiKey - The API key to use
     * 
     * @return - A client which will send requests and adjust speed based on rate
     */
    public static ModrinthAPI rateLimited(UserAgent agent, String url, String apiKey) {
        HttpClient client = new RatelimitedHttpClient(agent, url, apiKey);
        ModrinthAPI result = new ModrinthAPI(client);

        result.initializeGson();
        return result;
    }

    /**
     * Returns a client which will send requests and adjust speed based on rate
     * limits
     * 
     * @param agent   - The user agent to use
     * @param url     - The base URL to use
     * @param apiKey  - The API key to use
     * @param timeout - The timeout to use in milliseconds
     * 
     * @return - A client which will send requests and adjust speed based on rate
     */
    public static ModrinthAPI rateLimited(UserAgent agent, String url, String apiKey, long timeout) {
        HttpClient client = new RatelimitedHttpClient(agent, url, apiKey, timeout);
        ModrinthAPI result = new ModrinthAPI(client);

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
                .registerTypeAdapter(Instant.class, new ISOTimeAdapter())
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
     * 
     * @return - The project endpoints
     */
    public ProjectEndpoints projects() {
        return new ProjectEndpoints(gson, client);
    }

    /**
     * Returns the version endpoints.
     * 
     * @return - The version endpoints
     */
    public VersionEndpoints versions() {
        return new VersionEndpoints(gson, client);
    }

    /**
     * Returns the user endpoints.
     * 
     * @return - The user endpoints
     */
    public UserEndpoints users() {
        return new UserEndpoints(gson, client);
    }

    /**
     * Returns the team endpoints.
     * 
     * @return - The team endpoints
     */
    public TeamsEndpoints teams() {
        return new TeamsEndpoints(gson, client);
    }

    /**
     * Returns the tag endpoints.
     * 
     * @return - The tag endpoints
     */
    public TagsEndpoints tags() {
        return new TagsEndpoints(gson, client);
    }

    /**
     * Returns the collection endpoints.
     * 
     * @return - The collection endpoints
     */
    public CollectionEndpoints collections() {
        return new CollectionEndpoints(gson, client);
    }

    /**
     * Returns the organization endpoints.
     * 
     * @return - The organization endpoints
     */
    public OrganizationEndpoints organizations() {
        return new OrganizationEndpoints(gson, client);
    }

    /**
     * Returns the friends endpoints.
     * 
     * @return - The friends endpoints
     */
    public FriendsEndpoints friends() {
        return new FriendsEndpoints(gson, client);
    }

    /**
     * Returns the threads endpoints.
     * 
     * @return - The threads endpoints
     */
    public ThreadsEndpoints threads() {
        return new ThreadsEndpoints(gson, client);
    }

    /**
     * Returns the images endpoints.
     * 
     * @return - The images endpoints
     */
    public ImagesEndpoints images() {
        return new ImagesEndpoints(gson, client);
    }

    /**
     * Returns the limits endpoints.
     * 
     * @return - The limits endpoints
     */
    public LimitsEndpoints limits() {
        return new LimitsEndpoints(gson, client);
    }
}