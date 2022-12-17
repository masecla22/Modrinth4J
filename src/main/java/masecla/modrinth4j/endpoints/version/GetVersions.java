package masecla.modrinth4j.endpoints.version;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.version.GetVersions.GetVersionsRequest;
import masecla.modrinth4j.model.version.ProjectVersion;

public class GetVersions extends Endpoint<ProjectVersion[], GetVersionsRequest> {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetVersionsRequest {
        private String[] ids;
    }

    public GetVersions(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/versions";
    }

    @Override
    public Class<GetVersionsRequest> getRequestClass() {
        return GetVersionsRequest.class;
    }

    @Override
    public Class<ProjectVersion[]> getResponseClass() {
        return ProjectVersion[].class;
    }

    @Override
    public boolean isJsonBody() {
        return false;
    }
}
