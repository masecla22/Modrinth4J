package masecla.modrinth4j.endpoints.generic;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import lombok.AllArgsConstructor;
import lombok.Getter;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.exception.EndpointException;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpMethod;

/**
 * Represents a generic endpoint.
 * 
 * @param <O> The output type.
 * @param <I> The input type.
 */
@AllArgsConstructor
public abstract class Endpoint<O, I> {
    /** The HTTP Client to use. */
    @Getter
    private HttpClient client;

    /** The Gson instance to use. */
    @Getter
    private Gson gson;

    /**
     * Returns the endpoint of the request.
     * 
     * @return - The endpoint of the request.
     */
    public abstract String getEndpoint();

    /**
     * Whether or not the request body should be JSON.
     * If this is false, the request will embed the parameters in the URL.
     * 
     * @return - Whether or not the request body should be JSON.
     */
    public boolean isJsonBody() {
        return true;
    }

    /**
     * If this is false, the request will not have a body.
     * 
     * @return - Whether or not the request requires a body.
     */
    public boolean requiresBody() {
        return true;
    }

    /**
     * This will replace the URL based on a certain request.
     * 
     * @param request    - The request to use.
     * @param parameters - The parameters to use.
     * @return - The replaced URL.
     */
    protected String getReplacedUrl(I request, Map<String, String> parameters) {
        String url = getEndpoint();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            url = url.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return url;
    }

    /**
     * Sends the request.
     * 
     * @param request - The request to use
     * @return - A {@link CompletableFuture} that will return the response.
     * @see #sendRequest(Object, Map)
     */
    public CompletableFuture<O> sendRequest(I request) {
        return sendRequest(request, new HashMap<>());
    }

    /**
     * Sends the request.
     * 
     * @param request    - The request to use.
     * @param urlParams  - The URL parameters to use.
     * @return - A {@link CompletableFuture} that will return the response.
     */
    public CompletableFuture<O> sendRequest(I request, Map<String, String> urlParams) {
        String url = getReplacedUrl(request, urlParams);

        Map<String, String> queryParameters = new HashMap<>();

        if (this.requiresBody() && !getRequestClass().getType().equals(EmptyRequest.class) && !isJsonBody()) {
            JsonElement jsonBody = gson.toJsonTree(request, getRequestClass().getType());
            for (Map.Entry<String, JsonElement> entry : jsonBody.getAsJsonObject().entrySet()) {
                if (entry.getValue().isJsonPrimitive()) {
                    queryParameters.put(entry.getKey(), entry.getValue().getAsString());
                } else {
                    queryParameters.put(entry.getKey(), entry.getValue().toString());
                }
            }
        }

        return client.connect(url, queryParameters).thenApply(c -> {
            try {
                if (HttpMethod.permitsRequestBody(getMethod()))
                    c.method(getMethod(), RequestBody.create("", MediaType.parse("application/json; charset=utf-8")));
                else
                    c.method(getMethod(), null);

                if (this.requiresBody() && !getRequestClass().getType().equals(EmptyRequest.class)) {
                    JsonElement jsonBody = gson.toJsonTree(request, getRequestClass().getType());
                    if (isJsonBody()) {
                        c.method(getMethod(), RequestBody.create(gson.toJson(jsonBody),
                                MediaType.parse("application/json; charset=utf-8")));
                    }
                }

                Response response = this.client.execute(c);
                ResponseBody body = response.body();

                return checkBodyForErrors(body);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    /**
     * Checks a response body for errors.
     * 
     * @param body - The body to check.
     * @return - The parsed response.
     */
    protected O checkBodyForErrors(ResponseBody body) {
        if (body.contentLength() != 0) {
            String bodySrc = "UNAVAILABLE";
            try {
                bodySrc = body.string();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            JsonElement unparsedObject = null;
            try {
                unparsedObject = this.gson.fromJson(bodySrc, JsonElement.class);
            } catch (Exception e) {
                throw new EndpointException("invalid-json",
                        "Expected JSON response from endpoint, received: " + bodySrc + "");
            }
            if (unparsedObject != null) {
                if (unparsedObject.isJsonObject())
                    if (unparsedObject.getAsJsonObject().has("error")) {
                        String error = unparsedObject.getAsJsonObject().get("error").getAsString();
                        String description = unparsedObject.getAsJsonObject().get("description").getAsString();

                        throw new EndpointException(error, description);
                    }
                O object = this.gson.fromJson(unparsedObject, getResponseClass());
                return object;
            }
            return null;
        } else {
            return null;
        }
    }

    /**
     * Reads a stream into a byte array.
     * 
     * @param stream - The stream to read.
     * @return - The byte array.
     */
    protected byte[] readStream(InputStream stream) {
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[16384];
            while ((nRead = stream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Reads a file into a byte array.
     * 
     * @param file - The file to read.
     * @return - The byte array.
     */
    protected byte[] readFile(File file) {
        try {
            return readStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns the method to use.
     * 
     * @return - The method to use.
     */
    public String getMethod() {
        return "GET";
    }

    /**
     * Returns the {@link TypeToken} of the response;
     * 
     * @return - The {@link TypeToken} of the response.
     */
    public abstract TypeToken<O> getResponseClass();

    /**
     * Returns the {@link TypeToken} of the request.
     * 
     * @return - The {@link TypeToken} of the request.
     */
    public abstract TypeToken<I> getRequestClass();

}
