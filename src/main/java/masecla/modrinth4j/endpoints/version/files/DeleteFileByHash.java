package masecla.modrinth4j.endpoints.version.files;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;
import masecla.modrinth4j.endpoints.version.files.DeleteFileByHash.DeleteFileByHashRequest;
import masecla.modrinth4j.model.version.FileHash;

public class DeleteFileByHash extends Endpoint<EmptyResponse, DeleteFileByHashRequest> {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteFileByHashRequest {
        private FileHash algorithm;
    }

    public DeleteFileByHash(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/version_file/{hash}";
    }

    @Override
    public Class<DeleteFileByHashRequest> getRequestClass() {
        return DeleteFileByHashRequest.class;
    }

    @Override
    public Class<EmptyResponse> getResponseClass() {
        return EmptyResponse.class;
    }

    @Override
    public String getMethod() {
        return "DELETE";
    }

    @Override
    public boolean isJsonBody() {
        return false;
    }
}
