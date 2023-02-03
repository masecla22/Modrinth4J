package masecla.modrinth4j.client;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import lombok.NonNull;
import masecla.modrinth4j.client.agent.UserAgent;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * This class is used to connect to the Modrinth API.
 */
public abstract class HttpClient {
    /** The base URL of the API. */
    private String baseUrl = "https://api.modrinth.com/v2";

    /** The user agent to use. */
    @NonNull
    private UserAgent userAgent;

    /** The API key to use. */
    private String apiKey;

    /** The client to use. */
    private OkHttpClient client;

    /**
     * Creates a new instance of the client.
     * 
     * @param userAgent The user agent to use.
     * @param apiKey    The API key to use.
     */
    public HttpClient(UserAgent userAgent, String apiKey) {
        this.userAgent = userAgent;
        this.apiKey = apiKey;
        this.client = new OkHttpClient();
    }

    /**
     * Creates a new instance of the client.
     * 
     * @param userAgent The user agent to use.
     * @param baseUrl   The base URL to use.
     * @param apiKey    The API key to use.
     */
    public HttpClient(UserAgent userAgent, String baseUrl, String apiKey) {
        this(userAgent, apiKey);
        this.baseUrl = baseUrl;
    }

    /**
     * Creates a new connection to the API.
     * 
     * @param url - The URL to connect to.
     * @return The connection.
     */
    public CompletableFuture<Request.Builder> connect(String url) {
        return connect(url, null);
    }

    /**
     * Creates a new connection to the API.
     * 
     * @param url         - The URL to connect to.
     * @param queryParams - The query parameters to use.
     * @return The connection.
     */
    public CompletableFuture<Request.Builder> connect(String url, Map<String, String> queryParams) {
        return nextRequest().thenApply(v -> {
            HttpUrl parsedUrl = null;
            if (queryParams != null && !queryParams.isEmpty()) {
                HttpUrl.Builder builder = HttpUrl.parse(baseUrl + url).newBuilder();
                for (Map.Entry<String, String> entry : queryParams.entrySet()) {
                    builder.addQueryParameter(entry.getKey(), entry.getValue());
                }
                parsedUrl = builder.build();
            } else {
                parsedUrl = HttpUrl.parse(baseUrl + url);
            }

            Request.Builder connection = new Request.Builder().url(parsedUrl);
            if (userAgent != null)
                connection.header("User-Agent", userAgent.toString());
            if (apiKey != null)
                connection.header("Authorization", apiKey);
            return connection;
        });
    }

    /**
     * Executes a request.
     * 
     * @param connection - The connection to execute.
     * @return The response.
     * @throws IOException If an error occurs.
     */
    public Response execute(Request.Builder connection) throws IOException {
        nextRequest().join();
        Response response = client.newCall(connection.build()).execute();
        interceptResponse(response);
        return response;
    }

    /**
     * Waits for the next request to be allowed.
     * 
     * @return A future that completes when the next request is allowed.
     */
    public abstract CompletableFuture<Void> nextRequest();

    /**
     * Intercepts a response.
     * 
     * @param response - The response to intercept.
     */
    public void interceptResponse(Response response) {
    }
}
