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
import masecla.modrinth4j.endpoints.teams.ModifyTeamMemberInfo.ModifyTeamMemberInfoRequest;
import masecla.modrinth4j.model.team.ModrinthPermissionMask;

/**
 * This endpoint is used to modify a team member's info.
 */
public class ModifyTeamMemberInfo extends Endpoint<EmptyResponse, ModifyTeamMemberInfoRequest> {

    /**
     * This class is used to represent the request.
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ModifyTeamMemberInfoRequest {
        /** The user's role. */
        private String role;
        /** The user's permissions. */
        private ModrinthPermissionMask permissions;
        /** The user's payout split. */
        private double payoutsSplit;
    }

    /**
     * Creates a new instance of the endpoint.
     * 
     * @param client The client to use.
     * @param gson   The gson instance to use.
     */
    public ModifyTeamMemberInfo(HttpClient client, Gson gson) {
        super(client, gson);
    }

    /**
     * Returns the endpoint.
     */
    @Override
    public String getEndpoint() {
        return "/team/{id}/members/{user}";
    }

    /**
     * Returns the request class.
     */
    @Override
    public TypeToken<ModifyTeamMemberInfoRequest> getRequestClass() {
        return TypeToken.get(ModifyTeamMemberInfoRequest.class);
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
