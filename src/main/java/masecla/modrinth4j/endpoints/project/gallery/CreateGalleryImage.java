package masecla.modrinth4j.endpoints.project.gallery;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;

import lombok.Builder;
import lombok.Data;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;
import masecla.modrinth4j.endpoints.project.gallery.CreateGalleryImage.CreateGalleryImageRequest;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CreateGalleryImage extends Endpoint<EmptyResponse, CreateGalleryImageRequest> {
    @Data
    @Builder
    public static class CreateGalleryImageRequest {
        private String filename;
        private InputStream image;

        private boolean featured;
        private String title;
        private String description;

        public static class CreateGalleryImageRequestBuilder {
            public CreateGalleryImageRequestBuilder file(File file) {
                try {
                    this.filename = file.getName();
                    this.image = new FileInputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                return this;
            }
        }
    }

    public CreateGalleryImage(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/project/{id}/gallery";
    }

    @Override
    public String getMethod() {
        return "POST";
    }

    @Override
    public CompletableFuture<EmptyResponse> sendRequest(CreateGalleryImageRequest parameters,
            Map<String, String> urlParams) {
        String url = getReplacedUrl(parameters, urlParams);

        String extension = parameters.getFilename()
                .substring(parameters.getFilename().lastIndexOf(".") + 1);

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("ext", extension);
        queryParams.put("featured", parameters.isFeatured() + "");
        queryParams.put("title", parameters.getTitle());
        queryParams.put("description", parameters.getDescription());

        return getClient().connect(url, queryParams).thenApply(c -> {
            try {
                c.method(getMethod(), RequestBody.create(readStream(parameters.getImage())));
                c.header("Content-Type", "image/*");

                Response response = getClient().execute(c);

                checkBodyForErrors(response.body());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new EmptyResponse();
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
