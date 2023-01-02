package masecla.modrinth4j.endpoints.version;

import java.io.InputStream;
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
import masecla.modrinth4j.endpoints.version.AddFilesToVersion.AddFilesToVersionRequest;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddFilesToVersion extends Endpoint<EmptyResponse, AddFilesToVersionRequest> {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddFilesToVersionRequest {
        private String[] fileNames;
        private InputStream[] fileStreams;
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
            MultipartBody.Builder builder = new MultipartBody.Builder();

            builder.addFormDataPart("data", "{}"); // Not sure what this is for, but it's required

            try {
                for(int i = 0; i < request.getFileNames().length; i++) {
                    builder.addFormDataPart(request.getFileNames()[i], request.getFileNames()[i], 
                        RequestBody.create(this.readStream(request.getFileStreams()[i])));
                }

                c.post(builder.build());

                Response response = getClient().execute(c);
                checkBodyForErrors(response.body());
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
    public String getMethod() {
        return "POST";
    }
}
