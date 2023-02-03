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

/**
 * This endpoint is used to get the latest versions from a list of hashes.
 */
public class GetProjectLatestVersionsFromHashes
        extends Endpoint<HashProjectVersionMap, GetProjectLatestVersionsFromHashesRequest> {
    /**
     * This class is used to represent the request.
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetProjectLatestVersionsFromHashesRequest {
        /** The hash algorithm to use. */
        private FileHash algorithm;

        /** The list of hashes to check. */
        @Default
        private List<String> hashes = new ArrayList<>();

        /** The list of loaders to check. */
        @Default
        private List<String> loaders = new ArrayList<>();

        /** The list of game versions to check. */
        @Default
        private List<String> gameVersions = new ArrayList<>();
    }

    /**
     * Creates a new instance of the endpoint.
     * 
     * @param client The client to use.
     * @param gson   The gson instance to use.
     */
    public GetProjectLatestVersionsFromHashes(HttpClient client, Gson gson) {
        super(client, gson);
    }

    /**
     * Returns the endpoint.
     */
    @Override
    public String getEndpoint() {
        // Once again, why you would make a POST request ending in /update for a
        // data-fetching endpoint is beyond me.
        return "/version_files/update";
    }

    /**
     * Returns the request class.
     */
    @Override
    public TypeToken<GetProjectLatestVersionsFromHashesRequest> getRequestClass() {
        return TypeToken.get(GetProjectLatestVersionsFromHashesRequest.class);
    }

    /**
     * Returns the response class.
     */
    @Override
    public TypeToken<HashProjectVersionMap> getResponseClass() {
        return TypeToken.get(HashProjectVersionMap.class);
    }

    /**
     * Returns the method.
     */
    @Override
    public String getMethod() {
        return "POST";
    }
}
