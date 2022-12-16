package masecla.modrinth4j.endpoints.generic;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.exception.EndpointError;

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

    /**
     * If this is false, the request will not have a body.
     */
    public boolean requiresBody() {
        return true;
    }

    protected String getReplacedUrl(I request, Map<String, String> parameters) {
        String url = getEndpoint();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            url = url.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return url;
    }

    public CompletableFuture<O> sendRequest(I parameters) {
        return sendRequest(parameters, new HashMap<>());
    }

    public CompletableFuture<O> sendRequest(I request, Map<String, String> urlParams) {
        String url = getReplacedUrl(request, urlParams);
        return client.connect(url).thenApply(c -> {
            try {
                c.method(getMethod());
                if (this.requiresBody() && !getRequestClass().equals(EmptyRequest.class)) {
                    JsonElement jsonBody = gson.toJsonTree(request, getRequestClass());
                    if (isJsonBody()) {
                        c.requestBody(jsonBody.toString());
                        c.header("Content-Type", "application/json");
                    } else {
                        for (Map.Entry<String, JsonElement> entry : jsonBody.getAsJsonObject().entrySet()) {
                            if (entry.getValue().isJsonPrimitive()) {
                                c.data(entry.getKey(), entry.getValue().getAsString());
                            } else {
                                c.data(entry.getKey(), entry.getValue().toString());
                            }
                        }
                    }
                }

                Response response = c.ignoreContentType(true)
                        .ignoreHttpErrors(true)
                        .execute();

                if (response.body() != null) {
                    JsonElement unparsedObject = null;
                    try {
                        unparsedObject = this.gson.fromJson(response.body(), JsonElement.class);
                    } catch (Exception e) {
                        throw new EndpointError("invalid-json",
                                "Expected JSON response from endpoint, received: " + response.body() + "");
                    }
                    if (unparsedObject != null) {
                        if (unparsedObject.isJsonObject())
                            if (unparsedObject.getAsJsonObject().has("error")) {
                                String error = unparsedObject.getAsJsonObject().get("error").getAsString();
                                String description = unparsedObject.getAsJsonObject().get("description").getAsString();

                                throw new EndpointError(error, description);
                            }
                        O object = this.gson.fromJson(unparsedObject, getResponseClass());
                        return object;
                    }
                    return null;
                } else {
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }).exceptionally(c -> {
            c.printStackTrace();
            return null;
        });
    }

    public Method getMethod() {
        return Method.GET;
    }

    public abstract Class<O> getResponseClass();

    public abstract Class<I> getRequestClass();

}
