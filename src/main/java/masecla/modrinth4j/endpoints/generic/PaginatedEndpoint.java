package masecla.modrinth4j.endpoints.generic;

import com.google.gson.Gson;

import lombok.Builder.Default;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.PaginatedEndpoint.PaginatedRequest;
import masecla.modrinth4j.endpoints.generic.PaginatedEndpoint.PaginatedResponse;

/**
 * This class is used to represent a paginated endpoint.
 * 
 * @param <T> The type of the hits.
 * @param <O> The type of the response.
 * @param <I> The type of the request.
 */
public abstract class PaginatedEndpoint<T, O extends PaginatedResponse<T>, I extends PaginatedRequest>
        extends Endpoint<O, I> {
    /**
     * This class is used to represent a paginated request.
     */
    @Data
    @SuperBuilder
    public static class PaginatedRequest {
        /** The offset */
        @Default
        private int offset = 0;
        /** The limit */
        @Default
        private int limit = 10;
    }

    /**
     * This class is used to represent a paginated response.
     * 
     * @param <T> The type of the hits.
     */
    @Data
    public static class PaginatedResponse<T> {
        /** The offset */
        private int offset;
        /** The limit */
        private int limit;

        /** The total hits */
        private int totalHits;

        /** The hits */
        private T[] hits;
    }

    /**
     * Creates a new instance of the endpoint.
     * 
     * @param client The client to use.
     * @param gson   The gson instance to use.
     */
    public PaginatedEndpoint(HttpClient client, Gson gson) {
        super(client, gson);
    }
}
