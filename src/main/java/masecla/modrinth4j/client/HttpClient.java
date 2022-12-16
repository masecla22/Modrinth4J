package masecla.modrinth4j.client;

import java.util.concurrent.CompletableFuture;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class HttpClient {
    private static final String BASE_URL = "https://api.modrinth.com/v2";

    private String apiKey;

    public CompletableFuture<Connection> connect(String url) {
        return nextRequest().thenApply(v -> {
            Connection connection = Jsoup.connect(BASE_URL + url);
            if (apiKey != null)
                connection.header("Authorization", apiKey);
            return connection;
        });
    }

    public abstract CompletableFuture<Void> nextRequest();
}
