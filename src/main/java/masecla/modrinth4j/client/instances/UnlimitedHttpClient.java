package masecla.modrinth4j.client.instances;

import java.util.concurrent.CompletableFuture;

import masecla.modrinth4j.client.HttpClient;

public class UnlimitedHttpClient extends HttpClient {

    public UnlimitedHttpClient(String apiKey) {
        super(apiKey);
    }

    public UnlimitedHttpClient(String baseUrl, String apiKey) {
        super(baseUrl, apiKey);
    }

    @Override
    public CompletableFuture<Void> nextRequest() {
        return CompletableFuture.completedFuture(null);
    }
}
