package masecla.modrinth4j.endpoints.tags;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;

public class GetReportTypes extends Endpoint<List<String>, EmptyRequest> {

    public GetReportTypes(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/tag/report_type";
    }

    @Override
    public TypeToken<EmptyRequest> getRequestClass() {
        return TypeToken.get(EmptyRequest.class);
    }

    @Override
    public TypeToken<List<String>> getResponseClass() {
        return new TypeToken<List<String>>() {
        };
    }
}
