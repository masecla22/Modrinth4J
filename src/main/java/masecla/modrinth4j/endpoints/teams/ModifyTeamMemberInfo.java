package masecla.modrinth4j.endpoints.teams;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;
import masecla.modrinth4j.endpoints.teams.ModifyTeamMemberInfo.ModifyTeamMemberInfoRequest;
import masecla.modrinth4j.model.team.ModrinthPermissionMask;

public class ModifyTeamMemberInfo extends Endpoint<EmptyResponse, ModifyTeamMemberInfoRequest> {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ModifyTeamMemberInfoRequest {
        private String role;
        private ModrinthPermissionMask permissions;
        private double payoutsSplit;
    }

    public ModifyTeamMemberInfo(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/team/{id}/members/{user}";
    }

    @Override
    public Class<ModifyTeamMemberInfoRequest> getRequestClass() {
        return ModifyTeamMemberInfoRequest.class;
    }

    @Override
    public Class<EmptyResponse> getResponseClass() {
        return EmptyResponse.class;
    }

    @Override
    public String getMethod() {
        return "PATCH";
    }
}
