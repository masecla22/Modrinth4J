package masecla.modrinth4j.endpoints.teams;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;
import masecla.modrinth4j.endpoints.teams.AddMemberToTeam.AddMemberToTeamRequest;

public class AddMemberToTeam extends Endpoint<EmptyResponse, AddMemberToTeamRequest> {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddMemberToTeamRequest {
        private String userId;
    }

    public AddMemberToTeam(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/team/{id}/members";
    }

    @Override
    public Class<AddMemberToTeamRequest> getRequestClass() {
        return AddMemberToTeamRequest.class;
    }

    @Override
    public Class<EmptyResponse> getResponseClass() {
        return EmptyResponse.class;
    }

    @Override
    public String getMethod() {
        return "POST";
    }
}
