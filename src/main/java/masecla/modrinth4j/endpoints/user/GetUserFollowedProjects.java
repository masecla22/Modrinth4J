package masecla.modrinth4j.endpoints.user;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.model.project.Project;

public class GetUserFollowedProjects extends Endpoint<List<Project>, EmptyRequest> {

    public GetUserFollowedProjects(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/user/{id}/follows";
    }

    @Override
    public TypeToken<EmptyRequest> getRequestClass() {
        return TypeToken.get(EmptyRequest.class);
    }

    @Override
    public TypeToken<List<Project>> getResponseClass() {
        return new TypeToken<List<Project>>() {
        };
    }
}
