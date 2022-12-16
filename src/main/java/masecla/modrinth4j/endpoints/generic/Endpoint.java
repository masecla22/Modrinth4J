package masecla.modrinth4j.endpoints.generic;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.jsoup.Connection.Response;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

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

    /**
     * Whether or not the request body should be JSON.
     * If this is false, the request will embed the parameters in the URL.
     */
    public boolean isJsonBody() {
        return true;
    }

    public CompletableFuture<O> sendRequest(I parameters) {
        return client.connect(getEndpoint()).thenApply(c -> {
            try {
                JsonElement jsonBody = gson.toJsonTree(parameters, getRequestClass());
                // Response response = c.requestBody(jsonBody).execute();
                if (isJsonBody()) {
                    c.requestBody(jsonBody.toString());
                } else {
                    Map<String, String> data = new HashMap<>();
                    for (Map.Entry<String, JsonElement> entry : jsonBody.getAsJsonObject().entrySet()) {
                        if (entry.getValue().isJsonPrimitive()) {
                            data.put(entry.getKey(), entry.getValue().getAsString());
                        } else {
                            data.put(entry.getKey(), entry.getValue().toString());
                        }
                    }
                    c.data(data);
                }

                Response response = c.ignoreContentType(true).execute();

                O object = this.gson.fromJson(response.body(), getResponseClass());
                return object;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }).exceptionally(c -> {
            c.printStackTrace();
            return null;
        });
    }

    public abstract Class<O> getResponseClass();

    public abstract Class<I> getRequestClass();

}
