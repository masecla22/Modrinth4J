package masecla.modrinth4j.endpoints.user;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;
import masecla.modrinth4j.endpoints.user.ChangeUserIcon.ChangeUserIconRequest;

public class ChangeUserIcon extends Endpoint<EmptyResponse, ChangeUserIconRequest> {

    public ChangeUserIcon(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChangeUserIconRequest {
        public File icon;
    }

    @Override
    public String getEndpoint() {
        return "/user/{id}/icon";
    }

    @Override
    public Class<ChangeUserIconRequest> getRequestClass() {
        return ChangeUserIconRequest.class;
    }

    @Override
    public Class<EmptyResponse> getResponseClass() {
        return EmptyResponse.class;
    }

    @Override
    protected String getReplacedUrl(ChangeUserIconRequest request, Map<String, String> parameters) {
        // Once again, Modrinth is a bit weird with their API. They use query parameters
        // to determine the file extension of the icon in a PATCH request.
        // Also, this behaviour is completely undocumented.

        String ext = request.getIcon().getName().substring(request.getIcon().getName().lastIndexOf(".") + 1);
        return super.getReplacedUrl(request, parameters) + "?ext=" + ext;
    }

    @Override
    public CompletableFuture<EmptyResponse> sendRequest(ChangeUserIconRequest request, Map<String, String> urlParams) {
        String url = getReplacedUrl(request, urlParams);
        return getClient().connect(url).thenApply(c -> {
            c.method(getMethod());
            try {
                File f = request.getIcon();

                c.requestBody(readFile(f));

                Response r = c.ignoreContentType(true)
                        .ignoreHttpErrors(true)
                        .execute();

                System.out.println(r.body());

                return new EmptyResponse();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    @Override
    public Method getMethod() {
        return Method.PATCH;
    }
}
