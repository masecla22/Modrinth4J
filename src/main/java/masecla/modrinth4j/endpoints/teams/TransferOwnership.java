package masecla.modrinth4j.endpoints.teams;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;
import masecla.modrinth4j.endpoints.teams.TransferOwnership.TransferOwnershipRequest;

public class TransferOwnership extends Endpoint<EmptyResponse, TransferOwnershipRequest> {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TransferOwnershipRequest {
        public String userId;
    }

    public TransferOwnership(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/team/{id}/owner";
    }

    @Override
    public TypeToken<TransferOwnershipRequest> getRequestClass() {
        return TypeToken.get(TransferOwnershipRequest.class);
    }

    @Override
    public TypeToken<EmptyResponse> getResponseClass() {
        return TypeToken.get(EmptyResponse.class);
    }

    @Override
    public String getMethod() {
        return "PATCH";
    }
}
