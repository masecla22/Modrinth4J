package masecla.modrinth4j.endpoints.tags;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.model.tags.Loader;

public class GetLoaders extends Endpoint<List<Loader>, EmptyRequest> {

    public GetLoaders(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/tag/loader";
    }

    @Override
    public TypeToken<EmptyRequest> getRequestClass() {
        return TypeToken.get(EmptyRequest.class);
    }

    @Override
    public TypeToken<List<Loader>> getResponseClass() {
        return new TypeToken<List<Loader>>() {
        };
    }
}
