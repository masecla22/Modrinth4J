package masecla.modrinth4j.endpoints.user;

import org.jsoup.Connection.Method;

import com.google.gson.Gson;

import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;

public class DeleteUser extends Endpoint<EmptyResponse, EmptyRequest> {

    public DeleteUser(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/user/{id}";
    }

    @Override
    public Class<EmptyRequest> getRequestClass() {
        return EmptyRequest.class;
    }

    @Override
    public Class<EmptyResponse> getResponseClass() {
        return EmptyResponse.class;
    }

    @Override
    public Method getMethod() {
        return Method.DELETE;
    }

}
