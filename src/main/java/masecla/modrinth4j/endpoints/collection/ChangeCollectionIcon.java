package masecla.modrinth4j.endpoints.collection;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Endpoint for changing a collection's icon.
 */
public class ChangeCollectionIcon extends Endpoint<EmptyResponse, ChangeCollectionIcon.ChangeCollectionIconRequest> {

    /**
     * Creates a new instance of this endpoint.
     * 
     * @param client - The client to use for requests
     * @param gson   - The gson instance to use for serialization
     */
    public ChangeCollectionIcon(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/v3/collection/{id}/icon";
    }

    @Override
    public String getMethod() {
        return "PATCH";
    }

    @Override
    public TypeToken<EmptyResponse> getResponseClass() {
        return TypeToken.get(EmptyResponse.class);
    }

    @Override
    public TypeToken<ChangeCollectionIconRequest> getRequestClass() {
        return TypeToken.get(ChangeCollectionIconRequest.class);
    }

    @Override
    protected String getReplacedUrl(ChangeCollectionIconRequest request, Map<String, String> parameters) {
        String url = getEndpoint();
        if (parameters != null && parameters.containsKey("id")) {
            url = url.replace("{id}", parameters.get("id"));
        }
        return url;
    }

    @Override
    public CompletableFuture<EmptyResponse> sendRequest(ChangeCollectionIconRequest parameters,
            Map<String, String> urlParams) {
        return getClient().connect(getReplacedUrl(parameters, urlParams)).thenApply(c -> {
            MultipartBody.Builder bodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);

            bodyBuilder.addFormDataPart("icon", parameters.getFileName(),
                    RequestBody.create(readStream(parameters.getIconData())));

            c.method(getMethod(), bodyBuilder.build());

            Response response = executeRequest(c);

            return checkBodyForErrors(response.body());
        });
    }

    /**
     * Request for changing a collection icon.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChangeCollectionIconRequest {
        /** The icon data */
        private InputStream iconData;
        
        /** The filename of the icon */
        private String fileName;
    }
}
