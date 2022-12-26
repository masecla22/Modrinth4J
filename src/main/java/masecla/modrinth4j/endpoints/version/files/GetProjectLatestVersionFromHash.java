package masecla.modrinth4j.endpoints.version.files;

import com.google.gson.Gson;

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
        private String[] loaders = new String[0];

        @Default
        private String[] gameVersions = new String[0];
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
    public Class<GetProjectLatestVersionFromHashRequest> getRequestClass() {
        return GetProjectLatestVersionFromHashRequest.class;
    }

    @Override
    public Class<ProjectVersion> getResponseClass() {
        return ProjectVersion.class;
    }

    @Override
    public String getMethod() {
        return "POST";
    }
}
