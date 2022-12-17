package masecla.modrinth4j.endpoints.tags;

import com.google.gson.Gson;

import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;

public class GetReportTypes extends Endpoint<String[], EmptyRequest> {

    public GetReportTypes(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/tag/report_type";
    }

    @Override
    public Class<EmptyRequest> getRequestClass() {
        return EmptyRequest.class;
    }

    @Override
    public Class<String[]> getResponseClass() {
        return String[].class;
    }
}
