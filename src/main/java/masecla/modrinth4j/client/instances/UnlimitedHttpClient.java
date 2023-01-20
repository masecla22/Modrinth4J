package masecla.modrinth4j.client.instances;

import java.util.concurrent.CompletableFuture;

import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.client.agent.UserAgent;

public class UnlimitedHttpClient extends HttpClient {

    public UnlimitedHttpClient(UserAgent userAgent, String apiKey) {
        super(userAgent, apiKey);
    }

    public UnlimitedHttpClient(UserAgent userAgent, String baseUrl, String apiKey) {
        super(userAgent, baseUrl, apiKey);
    }

    @Override
    public CompletableFuture<Void> nextRequest() {
        return CompletableFuture.completedFuture(null);
    }
}
