package masecla.modrinth4j.endpoints.user;

import com.google.gson.Gson;

import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.model.user.ModrinthUserNotification;

public class GetUserNotifications extends Endpoint<ModrinthUserNotification[], EmptyRequest> {

    public GetUserNotifications(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/user/{id}/notifications";
    }

    @Override
    public Class<EmptyRequest> getRequestClass() {
        return EmptyRequest.class;
    }

    @Override
    public Class<ModrinthUserNotification[]> getResponseClass() {
        return ModrinthUserNotification[].class;
    }
}
