package masecla.modrinth4j.endpoints.version.files;

import java.util.Map;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.version.files.GetProjectLatestVersionFromHash.GetProjectLatestVersionFromHashRequest;
import masecla.modrinth4j.model.version.FileHash;
import masecla.modrinth4j.model.version.ProjectVersion;

public class GetProjectLatestVersionFromHash
        extends Endpoint<ProjectVersion, GetProjectLatestVersionFromHashRequest> {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetProjectLatestVersionFromHashRequest {
        // Marked as transient to exclude it from the JSON body. It will be injected
        // into the URL instead later.
        private transient FileHash algorithm;

        @Default
        private String[] loaders = new String[0];

        @Default
        private String[] gameVersions = new String[0];
    }

    public GetProjectLatestVersionFromHash(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    protected String getReplacedUrl(GetProjectLatestVersionFromHashRequest request, Map<String, String> parameters) {
        // We can hook into this method to inject the query parameters.
        // This is a bit of a hack, but it works.
        // Why on earth this is not a part of the request class is beyond me.

        String url = super.getReplacedUrl(request, parameters);
        url += "?algorithm=" + request.algorithm.name().toLowerCase();
        return url;
    }

    @Override
    public String getEndpoint() {
        // Why would you make a fetching endpoint a POST request with the end being
        // /update. God damn it Modrinth.
        return "/version_file/{hash}/update";
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
