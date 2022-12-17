package masecla.modrinth4j.endpoints.tags;

import com.google.gson.Gson;

import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.model.tags.GameVersion;

public class GetGameVersions extends Endpoint<GameVersion[], EmptyRequest> {

    public GetGameVersions(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/tag/game_version";
    }

    @Override
    public Class<EmptyRequest> getRequestClass() {
        return EmptyRequest.class;
    }

    @Override
    public Class<GameVersion[]> getResponseClass() {
        return GameVersion[].class;
    }
}