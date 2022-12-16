package masecla.modrinth4j.endpoints.version;

import com.google.gson.Gson;
import lombok.experimental.SuperBuilder;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.version.GetProjectVersions.GetProjectVersionsRequest;
import masecla.modrinth4j.model.version.ProjectVersion;

@SuperBuilder
public class GetProjectVersions extends Endpoint<ProjectVersion[], GetProjectVersionsRequest> {
    public GetProjectVersions(HttpClient httpClient, Gson gson) {
        super(httpClient, gson);
    }

    public static class GetProjectVersionsRequest {
        private String loaders;
        private String gameVersions;
        private String featured;
    }

    @Override
    public String getEndpoint() {
        return "project/{id}/version";
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
