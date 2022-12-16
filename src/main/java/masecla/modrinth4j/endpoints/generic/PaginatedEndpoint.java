package masecla.modrinth4j.endpoints.generic;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.PaginatedEndpoint.PaginatedRequest;
import masecla.modrinth4j.endpoints.generic.PaginatedEndpoint.PaginatedResponse;

public abstract class PaginatedEndpoint<T, O extends PaginatedResponse<T>, I extends PaginatedRequest>
        extends Endpoint<O, I> {
    @Data
    public static class PaginatedRequest {
        private int offset;
        private int limit;
    }

    @Data
    public static class PaginatedResponse<T> {
        private int offset;
        private int limit;

        @SerializedName("total_hits")
        private int totalHits;

        private T[] hits;
    }

    public PaginatedEndpoint(HttpClient client, Gson gson) {
        super(client, gson);
    }
}
