package masecla.modrinth4j.endpoints.version.files;

import org.jsoup.Connection.Method;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.version.files.GetProjectLatestVersionsFromHashes.GetProjectLatestVersionsFromHashesRequest;
import masecla.modrinth4j.model.version.FileHash;
import masecla.modrinth4j.model.version.files.HashProjectVersionMap;

public class GetProjectLatestVersionsFromHashes
        extends Endpoint<HashProjectVersionMap, GetProjectLatestVersionsFromHashesRequest> {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetProjectLatestVersionsFromHashesRequest {
        private FileHash algorithm;
        private String[] hashes;

        @Default
        private String[] loaders = new String[0];

        @Default
        private String[] gameVersions = new String[0];
    }

    public GetProjectLatestVersionsFromHashes(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        // Once again, why you would make a POST request ending in /update for a
        // data-fetching endpoint is beyond me.
        return "/version_files/update";
    }

    @Override
    public Class<GetProjectLatestVersionsFromHashesRequest> getRequestClass() {
        return GetProjectLatestVersionsFromHashesRequest.class;
    }

    @Override
    public Class<HashProjectVersionMap> getResponseClass() {
        return HashProjectVersionMap.class;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

}
