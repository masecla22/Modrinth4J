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
        @Default
        private List<String> hashes = new ArrayList<>();

        @Default
        private List<String> loaders = new ArrayList<>();

        @Default
        private List<String> gameVersions = new ArrayList<>();
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
    public TypeToken<GetProjectLatestVersionsFromHashesRequest> getRequestClass() {
        return TypeToken.get(GetProjectLatestVersionsFromHashesRequest.class);
    }

    @Override
    public TypeToken<HashProjectVersionMap> getResponseClass() {
        return TypeToken.get(HashProjectVersionMap.class);
    }

    @Override
    public String getMethod() {
        return "POST";
    }
}
