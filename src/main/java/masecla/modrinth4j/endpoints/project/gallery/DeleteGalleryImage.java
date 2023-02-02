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

public class DeleteGalleryImage extends Endpoint<EmptyResponse, DeleteGalleryImageRequest> {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DeleteGalleryImageRequest {
        private String url;
    }

    public DeleteGalleryImage(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/project/{id}/gallery";
    }

    @Override
    public TypeToken<DeleteGalleryImageRequest> getRequestClass() {
        return TypeToken.get(DeleteGalleryImageRequest.class);
    }

    @Override
    public TypeToken<EmptyResponse> getResponseClass() {
        return TypeToken.get(EmptyResponse.class);
    }

    @Override
    public boolean isJsonBody() {
        return false;
    }

    @Override
    public String getMethod() {
        return "DELETE";
    }
}
