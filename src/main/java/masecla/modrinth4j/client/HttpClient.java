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

public abstract class HttpClient {
    private String baseUrl = "https://api.modrinth.com/v2";

    @NonNull
    private UserAgent userAgent;

    private String apiKey;

    private OkHttpClient client;

    public HttpClient(UserAgent userAgent, String apiKey) {
        this.userAgent = userAgent;
        this.apiKey = apiKey;
        this.client = new OkHttpClient();
    }

    public HttpClient(UserAgent userAgent, String baseUrl, String apiKey) {
        this(userAgent, apiKey);
        this.baseUrl = baseUrl;
    }

    public CompletableFuture<Request.Builder> connect(String url) {
        return connect(url, null);
    }

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

    public Response execute(Request.Builder connection) throws IOException {
        nextRequest().join();
        Response response = client.newCall(connection.build()).execute();
        interceptResponse(response);
        return response;
    }

    public abstract CompletableFuture<Void> nextRequest();

    public void interceptResponse(Response response) {
    }
}
