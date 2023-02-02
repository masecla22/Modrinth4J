package masecla.modrinth4j.endpoints.version;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.Builder;
import lombok.Data;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.version.GetProjectVersions.GetProjectVersionsRequest;
import masecla.modrinth4j.model.version.ProjectVersion;

public class GetProjectVersions extends Endpoint<List<ProjectVersion>, GetProjectVersionsRequest> {
    public GetProjectVersions(HttpClient httpClient, Gson gson) {
        super(httpClient, gson);
    }

    @Data
    @Builder
    public static class GetProjectVersionsRequest {
        private List<String> loaders;
        private List<String> gameVersions;
        private boolean featured;
    }

    @Override
    public String getEndpoint() {
        return "/project/{id}/version";
    }

    @Override
    public TypeToken<List<ProjectVersion>> getResponseClass() {
        return new TypeToken<List<ProjectVersion>>() {
        };
    }

    @Override
    public TypeToken<GetProjectVersionsRequest> getRequestClass() {
        return TypeToken.get(GetProjectVersionsRequest.class);
    }

    @Override
    public boolean isJsonBody() {
        return false;
    }
}
