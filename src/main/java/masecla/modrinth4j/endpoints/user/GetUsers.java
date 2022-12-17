package masecla.modrinth4j.endpoints.user;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.user.GetUsers.GetUsersRequest;
import masecla.modrinth4j.model.user.ModrinthUser;

public class GetUsers extends Endpoint<ModrinthUser[], GetUsersRequest> {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetUsersRequest {
        public String[] ids;
    }

    public GetUsers(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/users";
    }

    @Override
    public Class<GetUsersRequest> getRequestClass() {
        return GetUsersRequest.class;
    }

    @Override
    public Class<ModrinthUser[]> getResponseClass() {
        return ModrinthUser[].class;
    }

    @Override
    public boolean isJsonBody() {
        return false;
    }

}
