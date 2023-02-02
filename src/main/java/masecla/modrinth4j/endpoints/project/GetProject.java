package masecla.modrinth4j.endpoints.project;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.model.project.Project;

public class GetProject extends Endpoint<Project, EmptyRequest> {

    public GetProject(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/project/{id}";
    }

    @Override
    public TypeToken<EmptyRequest> getRequestClass() {
        return TypeToken.get(EmptyRequest.class);
    }

    @Override
    public TypeToken<Project> getResponseClass() {
        return TypeToken.get(Project.class);
    }

    @Override
    public boolean requiresBody() {
        return false;
    }

}
