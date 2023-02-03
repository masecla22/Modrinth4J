package masecla.modrinth4j.endpoints.project;

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
import masecla.modrinth4j.endpoints.project.ChangeProjectIcon.ChangeProjectIconRequest;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * This endpoint is used to change the icon of a project.
 */
public class ChangeProjectIcon extends Endpoint<EmptyResponse, ChangeProjectIconRequest> {

    /**
     * Creates a new instance of the endpoint.
     * 
     * @param client - The client to use.
     * @param gson - The gson instance to use.
     */
    public ChangeProjectIcon(HttpClient client, Gson gson) {
        super(client, gson);
    }

    /**
     * Represents the data for a project.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChangeProjectIconRequest {
        /** The input stream of the icon. */
        public InputStream icon;
        /** The name of the icon. */
        public String iconName;
    }

    /**
     * Gets the endpoint of the request.
     * 
     * @return The endpoint of the request.
     */
    @Override
    public String getEndpoint() {
        return "/project/{id}/icon";
    }

    /**
     * Returns the request class.
     *
     * @return - The {@link TypeToken} for the response.
     */
    @Override
    public TypeToken<ChangeProjectIconRequest> getRequestClass() {
        return TypeToken.get(ChangeProjectIconRequest.class);
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
    protected String getReplacedUrl(ChangeProjectIconRequest request, Map<String, String> parameters) {
        // Once again, Modrinth is a bit weird with their API. They use query parameters
        // to determine the file extension of the icon in a PATCH request.
        // Also, this whole endpoint is undocumented
        String ext = request.getIconName().substring(request.getIconName().lastIndexOf(".") + 1);
        return super.getReplacedUrl(request, parameters) + "?ext=" + ext;
    }

    /**
     * Sends the request.
     */
    @Override
    public CompletableFuture<EmptyResponse> sendRequest(ChangeProjectIconRequest request, Map<String, String> urlParams) {
        String url = getReplacedUrl(request, urlParams);
        return getClient().connect(url).thenApply(c -> {
            try {
                InputStream stream = request.getIcon();
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
     * Returns the method of the request.
     */
    @Override
    public String getMethod() {
        return "PATCH";
    }
}
