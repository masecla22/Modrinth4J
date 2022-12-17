package masecla.modrinth4j.endpoints.teams;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.teams.GetTeamsMembers.GetTeamsMembersRequest;
import masecla.modrinth4j.model.team.ModrinthTeamMember;

public class GetTeamsMembers extends Endpoint<ModrinthTeamMember[][], GetTeamsMembersRequest> {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetTeamsMembersRequest {
        private String[] ids;
    }

    public GetTeamsMembers(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/teams";
    }

    @Override
    public Class<GetTeamsMembersRequest> getRequestClass() {
        return GetTeamsMembersRequest.class;
    }

    @Override
    public Class<ModrinthTeamMember[][]> getResponseClass() {
        return ModrinthTeamMember[][].class;
    }
}
