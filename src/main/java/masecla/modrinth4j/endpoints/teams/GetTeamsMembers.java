package masecla.modrinth4j.endpoints.teams;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.teams.GetTeamsMembers.GetTeamsMembersRequest;
import masecla.modrinth4j.model.team.ModrinthTeamMember;

public class GetTeamsMembers extends Endpoint<List<List<ModrinthTeamMember>>, GetTeamsMembersRequest> {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetTeamsMembersRequest {
        private List<String> ids;
    }
    
    public GetTeamsMembers(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/teams";
    }

    @Override
    public TypeToken<GetTeamsMembersRequest> getRequestClass() {
        return TypeToken.get(GetTeamsMembersRequest.class);
    }

    @Override
    public TypeToken<List<List<ModrinthTeamMember>>> getResponseClass() {
        return new TypeToken<List<List<ModrinthTeamMember>>>() {
        };
    }

    @Override
    public boolean isJsonBody() {
        return false;
    }
}
