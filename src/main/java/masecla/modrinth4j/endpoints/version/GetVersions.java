package masecla.modrinth4j.endpoints.version;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.version.GetVersions.GetVersionsRequest;
import masecla.modrinth4j.model.version.ProjectVersion;

public class GetVersions extends Endpoint<List<ProjectVersion>, GetVersionsRequest> {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetVersionsRequest {
        private List<String> ids;
    }

    public GetVersions(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/versions";
    }

    @Override
    public TypeToken<GetVersionsRequest> getRequestClass() {
        return TypeToken.get(GetVersionsRequest.class);
    }

    @Override
    public TypeToken<List<ProjectVersion>> getResponseClass() {
        return new TypeToken<List<ProjectVersion>>() {
        };
    }

    @Override
    public boolean isJsonBody() {
        return false;
    }
}
