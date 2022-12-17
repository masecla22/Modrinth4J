package masecla.modrinth4j.endpoints.user;

import org.jsoup.Connection.Method;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;
import masecla.modrinth4j.endpoints.user.ModifyUser.ModifyUserRequest;

public class ModifyUser extends Endpoint<EmptyResponse, ModifyUserRequest> {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ModifyUserRequest {
        private String username;
        private String name;
        private String email;
        private String bio;
    }

    public ModifyUser(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/user/{id}";
    }

    @Override
    public Class<ModifyUserRequest> getRequestClass() {
        return ModifyUserRequest.class;
    }

    @Override
    public Class<EmptyResponse> getResponseClass() {
        return EmptyResponse.class;
    }

    @Override
    public Method getMethod() {
        return Method.PATCH;
    }

}
