package masecla.modrinth4j.endpoints.user;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.model.user.ModrinthUser;

public class GetSelf extends Endpoint<ModrinthUser, EmptyRequest> {

    public GetSelf(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/user";
    }

    @Override
    public TypeToken<EmptyRequest> getRequestClass() {
        return TypeToken.get(EmptyRequest.class);
    }

    @Override
    public TypeToken<ModrinthUser> getResponseClass() {
        return TypeToken.get(ModrinthUser.class);
    }

}
