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

/**
 * This endpoint is used to transfer ownership of a team.
 */
public class TransferOwnership extends Endpoint<EmptyResponse, TransferOwnershipRequest> {
    /**
     * This class is used to represent the request.
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TransferOwnershipRequest {
        public String userId;
    }

    /**
     * Creates a new instance of the endpoint.
     * 
     * @param client The client to use.
     * @param gson   The gson instance to use.
     */
    public TransferOwnership(HttpClient client, Gson gson) {
        super(client, gson);
    }

    /**
     * Returns the endpoint.
     */
    @Override
    public String getEndpoint() {
        return "/team/{id}/owner";
    }

    /**
     * Returns the request class.
     */
    @Override
    public TypeToken<TransferOwnershipRequest> getRequestClass() {
        return TypeToken.get(TransferOwnershipRequest.class);
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
        return "PATCH";
    }
}
