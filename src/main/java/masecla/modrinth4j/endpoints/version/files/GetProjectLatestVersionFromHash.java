package masecla.modrinth4j.endpoints.version.files;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.version.files.GetProjectLatestVersionFromHash.GetProjectLatestVersionFromHashRequest;
import masecla.modrinth4j.model.version.ProjectVersion;

public class GetProjectLatestVersionFromHash
        extends Endpoint<ProjectVersion, GetProjectLatestVersionFromHashRequest> {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetProjectLatestVersionFromHashRequest {
        @Default
        private List<String> loaders = new ArrayList<>();

        @Default
        private List<String> gameVersions = new ArrayList<>();
    }

    public GetProjectLatestVersionFromHash(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        // Why would you make a fetching endpoint a POST request with the end being
        // /update. God damn it Modrinth.
        return "/version_file/{hash}/update?algorithm={algorithm}";
    }

    @Override
    public TypeToken<GetProjectLatestVersionFromHashRequest> getRequestClass() {
        return TypeToken.get(GetProjectLatestVersionFromHashRequest.class);
    }

    @Override
    public TypeToken<ProjectVersion> getResponseClass() {
        return TypeToken.get(ProjectVersion.class);
    }

    @Override
    public String getMethod() {
        return "POST";
    }
}
