package masecla.modrinth4j.endpoints.project;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;

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

public class ChangeProjectIcon extends Endpoint<EmptyResponse, ChangeProjectIconRequest> {

    public ChangeProjectIcon(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChangeProjectIconRequest {
        public File icon;
    }

    @Override
    public String getEndpoint() {
        return "/project/{id}/icon";
    }

    @Override
    public Class<ChangeProjectIconRequest> getRequestClass() {
        return ChangeProjectIconRequest.class;
    }

    @Override
    public Class<EmptyResponse> getResponseClass() {
        return EmptyResponse.class;
    }

    @Override
    protected String getReplacedUrl(ChangeProjectIconRequest request, Map<String, String> parameters) {
        // Once again, Modrinth is a bit weird with their API. They use query parameters
        // to determine the file extension of the icon in a PATCH request.
        // Also, this whole endpoint is undocumented

        String ext = request.getIcon().getName().substring(request.getIcon().getName().lastIndexOf(".") + 1);
        return super.getReplacedUrl(request, parameters) + "?ext=" + ext;
    }

    @Override
    public CompletableFuture<EmptyResponse> sendRequest(ChangeProjectIconRequest request, Map<String, String> urlParams) {
        String url = getReplacedUrl(request, urlParams);
        return getClient().connect(url).thenApply(c -> {
            try {
                File f = request.getIcon();

                c.method(getMethod(), RequestBody.create(readFile(f)));
                
                Response response = getClient().execute(c);
                checkBodyForErrors(response.body());

                return new EmptyResponse();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    @Override
    public String getMethod() {
        return "PATCH";
    }
}
