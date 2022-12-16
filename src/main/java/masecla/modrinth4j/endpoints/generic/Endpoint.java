package masecla.modrinth4j.endpoints.generic;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import org.jsoup.Connection.Response;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Getter;
import masecla.modrinth4j.client.HttpClient;

@AllArgsConstructor
public abstract class Endpoint<O, I> {
    @Getter
    private HttpClient client;

    @Getter
    private Gson gson;

    public abstract String getEndpoint();

    public CompletableFuture<O> sendRequest(I parameters) {
        return client.connect(getEndpoint()).thenApply(c -> {
            try {
                String jsonBody = this.gson.toJson(parameters, getRequestClass());
                Response response = c.requestBody(jsonBody).execute();

                O object = this.gson.fromJson(response.body(), getResponseClass());
                return object;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    public abstract Class<O> getResponseClass();

    public abstract Class<I> getRequestClass();
}
