package masecla.modrinth4j.endpoints.user;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.model.user.ModrinthUserNotification;

public class GetUserNotifications extends Endpoint<List<ModrinthUserNotification>, EmptyRequest> {

    public GetUserNotifications(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/user/{id}/notifications";
    }

    @Override
    public TypeToken<EmptyRequest> getRequestClass() {
        return TypeToken.get(EmptyRequest.class);
    }

    @Override
    public TypeToken<List<ModrinthUserNotification>> getResponseClass() {
        return new TypeToken<List<ModrinthUserNotification>>() {
        };
    }
}
