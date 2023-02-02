package masecla.modrinth4j.endpoints.user;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
    public TypeToken<ModifyUserRequest> getRequestClass() {
        return TypeToken.get(ModifyUserRequest.class);
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
