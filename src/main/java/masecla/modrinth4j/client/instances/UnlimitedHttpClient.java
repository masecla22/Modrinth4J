package masecla.modrinth4j.client.instances;

import java.util.concurrent.CompletableFuture;

import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.client.agent.UserAgent;

/**
 * This class is used to connect to the Modrinth API without any rate limiting.
 * 
 * @deprecated - Use {@link RatelimitedHttpClient} instead.
 */
@Deprecated
public class UnlimitedHttpClient extends HttpClient {

    /**
     * Creates a new instance of the client.
     * 
     * @param userAgent The user agent to use.
     * @param apiKey    The API key to use.
     */
    @Deprecated
    public UnlimitedHttpClient(UserAgent userAgent, String apiKey) {
        super(userAgent, apiKey);
    }
    
    /**
     * Creates a new instance of the client.
     * 
     * @param userAgent The user agent to use.
     * @param baseUrl   The base URL to use.
     * @param apiKey    The API key to use.
    */
    @Deprecated
    public UnlimitedHttpClient(UserAgent userAgent, String baseUrl, String apiKey) {
        super(userAgent, baseUrl, apiKey);
    }

    /**
     * Creates a new instance of the client.
     * 
     * @param userAgent The user agent to use.
     * @param baseUrl   The base URL to use.
     * @param apiKey    The API key to use.
     * @param timeout   The timeout to use in milliseconds.
     */
    @Deprecated

    public UnlimitedHttpClient(UserAgent userAgent, String baseUrl, String apiKey, long timeout) {
        super(userAgent, baseUrl, apiKey, timeout);
    }

    /**
     * Returns the next request.
     */
    @Override
    @Deprecated
    public CompletableFuture<Void> nextRequest() {
        return CompletableFuture.completedFuture(null);
    }
}
