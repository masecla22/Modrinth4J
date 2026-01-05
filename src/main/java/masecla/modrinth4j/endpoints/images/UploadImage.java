package masecla.modrinth4j.endpoints.images;

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
import masecla.modrinth4j.model.image.ModrinthImage;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Endpoint for uploading an image.
 */
public class UploadImage extends Endpoint<ModrinthImage, UploadImage.UploadImageRequest> {

    public UploadImage(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/v3/image";
    }

    @Override
    public String getMethod() {
        return "POST";
    }

    @Override
    public TypeToken<ModrinthImage> getResponseClass() {
        return TypeToken.get(ModrinthImage.class);
    }

    @Override
    public TypeToken<UploadImageRequest> getRequestClass() {
        return TypeToken.get(UploadImageRequest.class);
    }

    @Override
    protected String getReplacedUrl(UploadImageRequest request, Map<String, String> parameters) {
        String url = getEndpoint() + "?ext=" + request.getExt() + "&context=" + request.getContext();
        if (request.getContextId() != null && !request.getContextId().isEmpty()) {
            url += "&project_id=" + request.getContextId();
        }
        return url;
    }

    @Override
    public CompletableFuture<ModrinthImage> sendRequest(UploadImageRequest request,
            Map<String, String> parameters) {
        return getClient().connect(getReplacedUrl(request, parameters)).thenApply(c -> {
            MultipartBody.Builder bodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);

            bodyBuilder.addFormDataPart("image", request.getFileName(),
                    RequestBody.create(readStream(request.getImage())));

            c.method(getMethod(), bodyBuilder.build());

            Response response = executeRequest(c);

            return checkBodyForErrors(response.body());
        });
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UploadImageRequest {
        /** The image file stream */
        private InputStream image;
        
        /** The file name */
        private String fileName;
        
        /** The file extension */
        private String ext;
        
        /** The context (e.g., "project", "version", "thread_message", "report") */
        private String context;
        
        /** Optional context ID (project_id, version_id, etc.) */
        private String contextId;
    }
}
