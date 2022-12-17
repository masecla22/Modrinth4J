package masecla.modrinth4j.endpoints.version.files;

import java.util.HashMap;

import org.jsoup.Connection.Method;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.version.files.GetVersionsFromHashes.GetVersionsFromHashesRequest;
import masecla.modrinth4j.endpoints.version.files.GetVersionsFromHashes.HashProjectVersionMap;
import masecla.modrinth4j.model.version.FileHash;
import masecla.modrinth4j.model.version.ProjectVersion;

public class GetVersionsFromHashes extends Endpoint<HashProjectVersionMap, GetVersionsFromHashesRequest> {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetVersionsFromHashesRequest {
        private FileHash algorithm;
        private String[] hashes;
    }

    public static class HashProjectVersionMap extends HashMap<String, ProjectVersion> {
    }

    public GetVersionsFromHashes(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/version_files";
    }

    @Override
    public Class<GetVersionsFromHashesRequest> getRequestClass() {
        return GetVersionsFromHashesRequest.class;
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
