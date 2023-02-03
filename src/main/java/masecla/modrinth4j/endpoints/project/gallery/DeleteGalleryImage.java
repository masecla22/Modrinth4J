package masecla.modrinth4j.endpoints.project.gallery;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;
import masecla.modrinth4j.endpoints.project.gallery.DeleteGalleryImage.DeleteGalleryImageRequest;

/**
 * This endpoint is used to delete a gallery image.
 */
public class DeleteGalleryImage extends Endpoint<EmptyResponse, DeleteGalleryImageRequest> {

    /**
     * Represents the request.
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DeleteGalleryImageRequest {
        private String url;
    }

    /**
     * Creates a new instance of the endpoint.
     * 
     * @param client The client to use.
     * @param gson   The gson instance to use.
     */
    public DeleteGalleryImage(HttpClient client, Gson gson) {
        super(client, gson);
    }

    /**
     * Returns the endpoint.
     */
    @Override
    public String getEndpoint() {
        return "/project/{id}/gallery";
    }

    /**
     * Returns the request class.
     *
     * @return - The {@link TypeToken} for the response.
     */
    @Override
    public TypeToken<DeleteGalleryImageRequest> getRequestClass() {
        return TypeToken.get(DeleteGalleryImageRequest.class);
    }

    /**
     * Returns the response class.
     *
     * @return - The {@link TypeToken} for the response.
     */
    @Override
    public TypeToken<EmptyResponse> getResponseClass() {
        return TypeToken.get(EmptyResponse.class);
    }

    /**
     * Whether or not the request is a json body.
     */
    @Override
    public boolean isJsonBody() {
        return false;
    }
    
    /**
     * Returns the method.
     *
     * @return - The HTTP method being used
     */
    @Override
    public String getMethod() {
        return "DELETE";
    }
}
