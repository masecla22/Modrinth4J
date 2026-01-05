package masecla.modrinth4j.endpoints.limits;

import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.model.limits.UserLimits;

/**
 * Endpoints for checking user resource limits.
 */
@AllArgsConstructor
public class LimitsEndpoints {
    /** The Gson instance to use */
    private Gson gson;
    /** The HTTP client to use */
    private HttpClient client;

    /**
     * Gets the current user's project creation limits.
     * 
     * @return A {@link CompletableFuture} that will return the project limits
     */
    public CompletableFuture<UserLimits> getProjectLimits() {
        return new GetProjectLimits(client, gson).sendRequest(new EmptyRequest());
    }

    /**
     * Gets the current user's organization creation limits.
     * 
     * @return A {@link CompletableFuture} that will return the organization limits
     */
    public CompletableFuture<UserLimits> getOrganizationLimits() {
        return new GetOrganizationLimits(client, gson).sendRequest(new EmptyRequest());
    }

    /**
     * Gets the current user's collection creation limits.
     * 
     * @return A {@link CompletableFuture} that will return the collection limits
     */
    public CompletableFuture<UserLimits> getCollectionLimits() {
        return new GetCollectionLimits(client, gson).sendRequest(new EmptyRequest());
    }
}
