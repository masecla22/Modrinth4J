package masecla.modrinth4j.endpoints.tags;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.model.tags.Category;

public class GetCategories extends Endpoint<List<Category>, EmptyRequest> {

    public GetCategories(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/tag/category";
    }

    @Override
    public TypeToken<EmptyRequest> getRequestClass() {
        return TypeToken.get(EmptyRequest.class);
    }

    @Override
    public TypeToken<List<Category>> getResponseClass() {
        return new TypeToken<List<Category>>() {
        };
    }
}
