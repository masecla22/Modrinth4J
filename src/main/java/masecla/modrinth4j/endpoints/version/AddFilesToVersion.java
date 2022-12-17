package masecla.modrinth4j.endpoints.version;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;
import masecla.modrinth4j.endpoints.version.AddFilesToVersion.AddFilesToVersionRequest;
import masecla.modrinth4j.exception.EndpointError;

public class AddFilesToVersion extends Endpoint<EmptyResponse, AddFilesToVersionRequest> {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddFilesToVersionRequest {
        public File[] files;
    }

    public AddFilesToVersion(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/version/{id}/file";
    }

    @Override
    public CompletableFuture<EmptyResponse> sendRequest(AddFilesToVersionRequest request,
            Map<String, String> urlParams) {
        String url = getReplacedUrl(request, urlParams);
        return getClient().connect(url).thenApply(c -> {
            c.method(getMethod());
            c.data("data", "{}"); // Not sure what this is for, but it's required

            try {
                for (File f : request.getFiles())
                    c.data(f.getName(), f.getName(), new FileInputStream(f));

                Response response = c.ignoreContentType(true)
                        .ignoreHttpErrors(true)
                        .execute();

                if (response.body() != null) {
                    JsonElement unparsedObject = null;
                    try {
                        unparsedObject = getGson().fromJson(response.body(), JsonElement.class);
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
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new EmptyResponse();
        });
    }

    @Override
    public Class<AddFilesToVersionRequest> getRequestClass() {
        return AddFilesToVersionRequest.class;
    }

    @Override
    public Class<EmptyResponse> getResponseClass() {
        return EmptyResponse.class;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }
}
