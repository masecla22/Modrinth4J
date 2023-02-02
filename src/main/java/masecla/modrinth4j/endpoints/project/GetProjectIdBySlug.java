package masecla.modrinth4j.endpoints.project;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.endpoints.project.GetProjectIdBySlug.GetProjectIdBySlugResponse;

public class GetProjectIdBySlug extends Endpoint<GetProjectIdBySlugResponse, EmptyRequest> {

    public GetProjectIdBySlug(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetProjectIdBySlugResponse {
        private String id;
    }

    public String getEndpoint() {
        return "/project/{id}/check";
    }

    @Override
    public TypeToken<EmptyRequest> getRequestClass() {
        return TypeToken.get(EmptyRequest.class);
    }

    @Override
    public TypeToken<GetProjectIdBySlugResponse> getResponseClass() {
        return TypeToken.get(GetProjectIdBySlugResponse.class);
    }

    @Override
    public boolean requiresBody() {
        return false;
    }

}
