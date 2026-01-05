package masecla.modrinth4j.endpoints.organization;

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
 * Endpoint for changing an organization's icon.
 */
public class ChangeOrganizationIcon extends Endpoint<EmptyResponse, ChangeOrganizationIcon.ChangeOrganizationIconRequest> {

    public ChangeOrganizationIcon(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/v3/organization/{id}/icon";
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
    public TypeToken<ChangeOrganizationIconRequest> getRequestClass() {
        return TypeToken.get(ChangeOrganizationIconRequest.class);
    }

    @Override
    public CompletableFuture<EmptyResponse> sendRequest(ChangeOrganizationIconRequest request,
            Map<String, String> parameters) {
        return getClient().connect(getReplacedUrl(request, parameters)).thenApply(c -> {
            MultipartBody.Builder bodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);

            bodyBuilder.addFormDataPart("icon", request.getFileName(),
                    RequestBody.create(readStream(request.getIcon())));

            c.method(getMethod(), bodyBuilder.build());

            Response response = executeRequest(c);

            return checkBodyForErrors(response.body());
        });
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChangeOrganizationIconRequest {
        /** The icon file stream */
        private InputStream icon;
        
        /** The file name */
        private String fileName;
    }
}
