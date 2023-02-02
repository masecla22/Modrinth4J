package masecla.modrinth4j.endpoints.user;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.user.GetUsers.GetUsersRequest;
import masecla.modrinth4j.model.user.ModrinthUser;

public class GetUsers extends Endpoint<List<ModrinthUser>, GetUsersRequest> {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetUsersRequest {
        public List<String> ids;
    }

    public GetUsers(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/users";
    }

    @Override
    public TypeToken<GetUsersRequest> getRequestClass() {
        return TypeToken.get(GetUsersRequest.class);
    }

    @Override
    public TypeToken<List<ModrinthUser>> getResponseClass() {
        return new TypeToken<List<ModrinthUser>>() {
        };
    }

    @Override
    public boolean isJsonBody() {
        return false;
    }

}
