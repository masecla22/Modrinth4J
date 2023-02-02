package masecla.modrinth4j.endpoints.version;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddFilesToVersion extends Endpoint<EmptyResponse, Map<String, InputStream>> {

    public AddFilesToVersion(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/version/{id}/file";
    }

    @Override
    public CompletableFuture<EmptyResponse> sendRequest(Map<String, InputStream> request,
            Map<String, String> urlParams) {
        String url = getReplacedUrl(request, urlParams);
        return getClient().connect(url).thenApply(c -> {
            MultipartBody.Builder builder = new MultipartBody.Builder();

            builder.addFormDataPart("data", "{}"); // Not sure what this is for, but it's required

            try {
                for (String key : request.keySet())
                    builder.addFormDataPart(key, key, RequestBody.create(this.readStream(request.get(key))));

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
    public TypeToken<Map<String, InputStream>> getRequestClass() {
        return new TypeToken<Map<String, InputStream>>() {
        };
    }

    @Override
    public TypeToken<EmptyResponse> getResponseClass() {
        return TypeToken.get(EmptyResponse.class);
    }

    @Override
    public String getMethod() {
        return "POST";
    }
}
