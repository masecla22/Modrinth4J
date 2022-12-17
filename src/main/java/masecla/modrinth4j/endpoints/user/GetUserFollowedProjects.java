package masecla.modrinth4j.endpoints.user;

import com.google.gson.Gson;

import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.model.project.Project;

public class GetUserFollowedProjects extends Endpoint<Project[], EmptyRequest> {

    public GetUserFollowedProjects(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/user/{id}/follows";
    }

    @Override
    public Class<EmptyRequest> getRequestClass() {
        return EmptyRequest.class;
    }

    @Override
    public Class<Project[]> getResponseClass() {
        return Project[].class;
    }
}
