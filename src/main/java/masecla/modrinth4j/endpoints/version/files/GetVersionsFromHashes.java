package masecla.modrinth4j.endpoints.version.files;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.version.files.GetVersionsFromHashes.GetVersionsFromHashesRequest;
import masecla.modrinth4j.model.version.FileHash;
import masecla.modrinth4j.model.version.files.HashProjectVersionMap;

/**
 * This endpoint is used to get the versions from a list of hashes.
 */
public class GetVersionsFromHashes extends Endpoint<HashProjectVersionMap, GetVersionsFromHashesRequest> {
    /**
     * This class is used to represent the request.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetVersionsFromHashesRequest {
        private FileHash algorithm;
        private List<String> hashes;
    }

    /**
     * Creates a new instance of the endpoint.
     * 
     * @param client The client to use.
     * @param gson   The gson instance to use.
     */
    public GetVersionsFromHashes(HttpClient client, Gson gson) {
        super(client, gson);
    }

    /**
     * Returns the endpoint.
     */
    @Override
    public String getEndpoint() {
        return "/version_files";
    }

    /**
     * Returns the request class.
     */
    @Override
    public TypeToken<GetVersionsFromHashesRequest> getRequestClass() {
        return TypeToken.get(GetVersionsFromHashesRequest.class);
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
