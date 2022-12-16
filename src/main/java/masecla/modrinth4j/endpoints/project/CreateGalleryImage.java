package masecla.modrinth4j.endpoints.project;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import lombok.Builder;
import lombok.Data;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;
import masecla.modrinth4j.endpoints.project.CreateGalleryImage.CreateGalleryImageRequest;
import masecla.modrinth4j.exception.EndpointError;

public class CreateGalleryImage extends Endpoint<EmptyResponse, CreateGalleryImageRequest> {
    @Data
    @Builder
    public static class CreateGalleryImageRequest {
        private File file;

        private boolean featured;
        private String title;
        private String description;
    }

    public CreateGalleryImage(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/project/{id}/gallery";
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    private String readFile(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();
            return new String(data, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public CompletableFuture<EmptyResponse> sendRequest(CreateGalleryImageRequest parameters,
            Map<String, String> urlParams) {
        String url = getReplacedUrl(parameters, urlParams);
        return getClient().connect(url).thenApply(c -> {
            try {
                c.method(getMethod());

                String extension = parameters.getFile().getName()
                        .substring(parameters.getFile().getName().lastIndexOf(".") + 1);
                c.data("ext", extension);
                c.data("featured", parameters.isFeatured() + "");
                c.data("title", parameters.getTitle());
                c.data("description", parameters.getDescription());

                c.requestBody(readFile(parameters.getFile()));

                c.header("Content-Type", "image/*");

                Response response = c.ignoreContentType(true)
                        .ignoreHttpErrors(true)
                        .execute();

                if (response.body() != null) {
                    JsonElement unparsedObject = getGson().fromJson(response.body(), JsonElement.class);
                    if (unparsedObject != null) {
                        if (unparsedObject.isJsonObject())
                            if (unparsedObject.getAsJsonObject().has("error")) {
                                String error = unparsedObject.getAsJsonObject().get("error").getAsString();
                                String description = unparsedObject.getAsJsonObject().get("description").getAsString();
                                throw new EndpointError(error, description);
                            }
                    }
                }

                return new EmptyResponse();
            } catch (IOException e) {
                e.printStackTrace();
                return new EmptyResponse();
            }
        }).exceptionally(c -> {
            c.printStackTrace();
            return null;
        });
    }

    @Override
    public Class<CreateGalleryImageRequest> getRequestClass() {
        return CreateGalleryImageRequest.class;
    }

    @Override
    public Class<EmptyResponse> getResponseClass() {
        return EmptyResponse.class;
    }
}
