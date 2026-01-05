package masecla.modrinth4j.endpoints.threads;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.model.thread.ModrinthThread;

/**
 * Endpoint for getting all threads for the current user.
 */
public class GetThreads extends Endpoint<List<ModrinthThread>, EmptyRequest> {

    public GetThreads(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/v3/threads";
    }

    @Override
    public String getMethod() {
        return "GET";
    }

    @Override
    public TypeToken<List<ModrinthThread>> getResponseClass() {
        return new TypeToken<List<ModrinthThread>>() {};
    }

    @Override
    public TypeToken<EmptyRequest> getRequestClass() {
        return TypeToken.get(EmptyRequest.class);
    }
}
