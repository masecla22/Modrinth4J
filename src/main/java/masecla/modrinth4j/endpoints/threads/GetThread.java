package masecla.modrinth4j.endpoints.threads;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.model.thread.ModrinthThread;

/**
 * Endpoint for getting a single thread.
 */
public class GetThread extends Endpoint<ModrinthThread, EmptyRequest> {

    public GetThread(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/v3/thread/{id}";
    }

    @Override
    public String getMethod() {
        return "GET";
    }

    @Override
    public TypeToken<ModrinthThread> getResponseClass() {
        return TypeToken.get(ModrinthThread.class);
    }

    @Override
    public TypeToken<EmptyRequest> getRequestClass() {
        return TypeToken.get(EmptyRequest.class);
    }
}
