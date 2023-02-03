package masecla.modrinth4j.endpoints.user;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;
import masecla.modrinth4j.endpoints.user.ChangeUserIcon.ChangeUserIconRequest;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * This endpoint is used to change the icon of a user.
 */
public class ChangeUserIcon extends Endpoint<EmptyResponse, ChangeUserIconRequest> {

    /**
     * This class is used to represent the request.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChangeUserIconRequest {
        /** The filename of the icon */
        private String filename;
        /** The icon data */
        private InputStream iconData;
    }

    /**
     * Creates a new instance of the endpoint.
     * 
     * @param client The client to use.
     * @param gson   The gson instance to use.
     */
    public ChangeUserIcon(HttpClient client, Gson gson) {
        super(client, gson);
    }

    /**
     * Returns the endpoint.
     */
    @Override
    public String getEndpoint() {
        return "/user/{id}/icon";
    }

    /**
     * Returns the request class.
     *
     * @return - The {@link TypeToken} for the response.
     */
    @Override
    public TypeToken<ChangeUserIconRequest> getRequestClass() {
        return TypeToken.get(ChangeUserIconRequest.class);
    }

    /**
     * Returns the response class.
     *
     * @return - The {@link TypeToken} for the response.
     */
    @Override
    public TypeToken<EmptyResponse> getResponseClass() {
        return TypeToken.get(EmptyResponse.class);
    }

    /**
     * Injects the file extension into the URL.
     */
    @Override
    protected String getReplacedUrl(ChangeUserIconRequest request, Map<String, String> parameters) {
        // Once again, Modrinth is a bit weird with their API. They use query parameters
        // to determine the file extension of the icon in a PATCH request.
        // Also, this behaviour is completely undocumented.

        String ext = request.getFilename().substring(request.getFilename().lastIndexOf(".") + 1);
        return super.getReplacedUrl(request, parameters) + "?ext=" + ext;
    }

    /**
     * Sends the request.
     */
    @Override
    public CompletableFuture<EmptyResponse> sendRequest(ChangeUserIconRequest request, Map<String, String> urlParams) {
        String url = getReplacedUrl(request, urlParams);
        return getClient().connect(url).thenApply(c -> {
            try {
                InputStream stream = request.getIconData();
                c.method(getMethod(), RequestBody.create(readStream(stream)));

                Response response = getClient().execute(c);
                checkBodyForErrors(response.body());

                return new EmptyResponse();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    /**
     * Returns the method.
     *
     * @return - The HTTP method being used
     */
    @Override
    public String getMethod() {
        return "PATCH";
    }
}
