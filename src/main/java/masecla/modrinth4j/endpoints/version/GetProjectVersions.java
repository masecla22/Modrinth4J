package masecla.modrinth4j.endpoints.version;

import com.google.gson.Gson;

import lombok.Builder;
import lombok.Data;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.version.GetProjectVersions.GetProjectVersionsRequest;
import masecla.modrinth4j.model.version.ProjectVersion;

public class GetProjectVersions extends Endpoint<ProjectVersion[], GetProjectVersionsRequest> {
    public GetProjectVersions(HttpClient httpClient, Gson gson) {
        super(httpClient, gson);
    }

    @Data
    @Builder
    public static class GetProjectVersionsRequest {
        private String[] loaders;
        private String[] gameVersions;
        private boolean featured;
    }

    @Override
    public String getEndpoint() {
        return "/project/{id}/version";
    }

    @Override
    public Class<ProjectVersion[]> getResponseClass() {
        return ProjectVersion[].class;
    }

    @Override
    public Class<GetProjectVersionsRequest> getRequestClass() {
        return GetProjectVersionsRequest.class;
    }

    @Override
    public boolean isJsonBody() {
        return false;
    }
}
