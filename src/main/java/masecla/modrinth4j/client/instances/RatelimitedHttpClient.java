package masecla.modrinth4j.client.instances;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.SneakyThrows;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.client.agent.UserAgent;
import okhttp3.Response;

/**
 * This class is used to connect to the Modrinth API with rate limiting.
 */
public class RatelimitedHttpClient extends HttpClient {
    /** The request limit. */
    private AtomicInteger requestLimit = new AtomicInteger(0);
    /** The request count. */
    private AtomicInteger requestCount = new AtomicInteger(0);
    /** The time left. */
    private AtomicInteger timeLeft = new AtomicInteger(0);

    /**
     * Creates a new instance of the client.
     * 
     * @param userAgent The user agent to use.
     * @param apiKey    The API key to use.
     */
    public RatelimitedHttpClient(UserAgent userAgent, String apiKey) {
        super(userAgent, apiKey);
    }

    /**
     * Creates a new instance of the client.
     * 
     * @param userAgent The user agent to use.
     * @param baseUrl   The base URL to use.
     * @param apiKey    The API key to use.
     */
    public RatelimitedHttpClient(UserAgent userAgent, String baseUrl, String apiKey) {
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
    public RatelimitedHttpClient(UserAgent userAgent, String baseUrl, String apiKey, long timeout) {
        super(userAgent, baseUrl, apiKey, timeout);
    }

    /**
     * Returns the next request.
     */
    @Override
    public CompletableFuture<Void> nextRequest() {
        // Check if we still have requests remaining
        if (requestCount.get() <= 0) {
            // If not, wait until we have more
            return CompletableFuture.runAsync(this::waitLeft);
        } else {
            // If we do, decrement the request count
            requestCount.decrementAndGet();
            return CompletableFuture.completedFuture(null);
        }
    }

    /**
     * This will wait until we have more requests.
     */
    @SneakyThrows
    private void waitLeft() {
        Thread.sleep(timeLeft.get() * 1100 + 1000);
    }

    /**
     * Intercepts the response.
     */
    @Override
    public void interceptResponse(Response response) {
        requestLimit.set(Integer.parseInt(response.header("X-Ratelimit-Limit")));
        requestCount.set(Integer.parseInt(response.header("X-Ratelimit-Remaining")));
        timeLeft.set(Integer.parseInt(response.header("X-Ratelimit-Reset")));
    }
}
