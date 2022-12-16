package masecla.modrinth4j.endpoints.project.gallery;

import org.jsoup.Connection.Method;

import com.google.gson.Gson;

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
    public Class<DeleteGalleryImageRequest> getRequestClass() {
        return DeleteGalleryImageRequest.class;
    }

    @Override
    public Class<EmptyResponse> getResponseClass() {
        return EmptyResponse.class;
    }

    @Override
    public boolean isJsonBody() {
        return false;
    }

    @Override
    public Method getMethod() {
        return Method.DELETE;
    }
}
