package masecla.modrinth4j.endpoints.version;

import com.google.gson.Gson;

import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.model.version.ProjectVersion;

public class GetVersion extends Endpoint<ProjectVersion, EmptyRequest> {

    public GetVersion(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/version/{id}";
    }

    @Override
    public Class<EmptyRequest> getRequestClass() {
        return EmptyRequest.class;
    }

    @Override
    public Class<ProjectVersion> getResponseClass() {
        return ProjectVersion.class;
    }
}
