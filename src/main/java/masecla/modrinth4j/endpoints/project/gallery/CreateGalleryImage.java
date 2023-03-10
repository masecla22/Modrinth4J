package masecla.modrinth4j.endpoints.project.gallery;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;
import masecla.modrinth4j.endpoints.project.gallery.CreateGalleryImage.CreateGalleryImageRequest;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * This endpoint is used to create a new image in a project's gallery.
 */
public class CreateGalleryImage extends Endpoint<EmptyResponse, CreateGalleryImageRequest> {
    /**
     * Represents the request data for this endpoint.
     */
    @Data
    @Builder
    public static class CreateGalleryImageRequest {
        /** The file name. */
        private String filename;
        /** The image. */
        private InputStream image;

        /** Whether the image is featured. */
        private boolean featured;
        /** The title of the image. */
        private String title;
        /** The description of the image. */
        private String description;

        /**
         * Represents the builder for this class.
         */
        public static class CreateGalleryImageRequestBuilder {
            /**
             * This will set the image to the specified file.
             * 
             * @param file - The file to use.
             * @return The builder.
             */
            @SneakyThrows
            public CreateGalleryImageRequestBuilder file(File file) {
                this.filename = file.getName();
                this.image = new FileInputStream(file);
                return this;
            }
        }
    }

    /**
     * Creates a new instance of the endpoint.
     * 
     * @param client - The client to use.
     * @param gson   - The gson instance to use.
     */
    public CreateGalleryImage(HttpClient client, Gson gson) {
        super(client, gson);
    }

    /**
     * Gets the endpoint of the request.
     * 
     * @return The endpoint of the request.
     */
    @Override
    public String getEndpoint() {
        return "/project/{id}/gallery";
    }

    /**
     * Gets the method of the request.
     * 
     * @return The method of the request.
     */
    @Override
    public String getMethod() {
        return "POST";
    }

    /**
     * Gets the type token of the response.
     * 
     * @return The type token of the response.
     */
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
            c.method(getMethod(), RequestBody.create(readStream(parameters.getImage())));
            c.header("Content-Type", "image/*");

            Response response = executeRequest(c);

            checkBodyForErrors(response.body());
            return new EmptyResponse();
        });
    }

    /**
     * Returns the class of the request.
     */
    @Override
    public TypeToken<CreateGalleryImageRequest> getRequestClass() {
        return TypeToken.get(CreateGalleryImageRequest.class);
    }

    /**
     * Returns the class of the response.
     */
    @Override
    public TypeToken<EmptyResponse> getResponseClass() {
        return TypeToken.get(EmptyResponse.class);
    }
}
