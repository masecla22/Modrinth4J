package masecla.modrinth4j.endpoints.version.files;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;
import masecla.modrinth4j.endpoints.version.files.DeleteFileByHash.DeleteFileByHashRequest;
import masecla.modrinth4j.model.version.FileHash;

/**
 * This endpoint is used to delete a file from a version.
 */
public class DeleteFileByHash extends Endpoint<EmptyResponse, DeleteFileByHashRequest> {
    /**
     * This class is used to represent the request.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteFileByHashRequest {
        /** The hash type to use */
        private FileHash algorithm;
    }

    /**
     * Creates a new instance of the endpoint.
     * 
     * @param client The client to use.
     * @param gson   The gson instance to use.
     */
    public DeleteFileByHash(HttpClient client, Gson gson) {
        super(client, gson);
    }

    /**
     * Returns the endpoint.
     */
    @Override
    public String getEndpoint() {
        return "/version_file/{hash}";
    }

    /**
     * Returns the request class.
     */
    @Override
    public TypeToken<DeleteFileByHashRequest> getRequestClass() {
        return TypeToken.get(DeleteFileByHashRequest.class);
    }

    /**
     * Returns the response class.
     */
    @Override
    public TypeToken<EmptyResponse> getResponseClass() {
        return TypeToken.get(EmptyResponse.class);
    }

    /**
     * Returns the method.
     *
     * @return - The HTTP method being used
     */
    @Override
    public String getMethod() {
        return "DELETE";
    }

    /**
     * Returns if the body is JSON.
     */
    @Override
    public boolean isJsonBody() {
        return false;
    }
}
