package masecla.modrinth4j.endpoints.project.gallery;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;
import masecla.modrinth4j.endpoints.project.gallery.ModifyGalleryImage.ModifyGalleryImageRequest;

public class ModifyGalleryImage extends Endpoint<EmptyResponse, ModifyGalleryImageRequest> {
    @Data
    @Builder
    @AllArgsConstructor
    public static class ModifyGalleryImageRequest {
        private String url;
        private String title;
        private boolean featured;
        private String description;
    }

    public ModifyGalleryImage(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/project/{id}/gallery";
    }

    @Override
    protected String getReplacedUrl(ModifyGalleryImageRequest request, Map<String, String> parameters) {
        // This is a bit of a hack, but it works.
        // This is due to the fact that, for some reason the Modrinth API takes query
        // parameters instead of a body
        // for a PATCH request. (which is not what the HTTP spec says)

        String endpoint = super.getReplacedUrl(request, parameters);
        List<String> encoded = new ArrayList<>();
        JsonObject object = getGson().toJsonTree(request).getAsJsonObject();
        for (String key : object.keySet()) {
            try {
                encoded.add(key + "=" + URLEncoder.encode(object.get(key).getAsString(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
            }
        }

        return endpoint + "?" + String.join("&", encoded);

    }

    @Override
    public String getMethod() {
        return "PATCH";
    }

    @Override
    public TypeToken<ModifyGalleryImageRequest> getRequestClass() {
        return TypeToken.get(ModifyGalleryImageRequest.class);
    }

    @Override
    public TypeToken<EmptyResponse> getResponseClass() {
        return TypeToken.get(EmptyResponse.class);
    }

    @Override
    public boolean requiresBody() {
        return false;
    }

}
