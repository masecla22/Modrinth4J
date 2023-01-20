package masecla.modrinth4j.endpoints.project;

import com.google.gson.Gson;

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
    public Class<EmptyRequest> getRequestClass() {
        return EmptyRequest.class;
    }

    @Override
    public Class<GetProjectIdBySlugResponse> getResponseClass() {
        return GetProjectIdBySlugResponse.class;
    }

    @Override
    public boolean requiresBody() {
        return false;
    }

}
