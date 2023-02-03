package masecla.modrinth4j.endpoints;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.SearchEndpoint.SearchRequest;
import masecla.modrinth4j.endpoints.SearchEndpoint.SearchResponse;
import masecla.modrinth4j.endpoints.SearchEndpoint.SearchResult;
import masecla.modrinth4j.endpoints.generic.PaginatedEndpoint;
import masecla.modrinth4j.endpoints.generic.PaginatedEndpoint.PaginatedRequest;
import masecla.modrinth4j.endpoints.generic.PaginatedEndpoint.PaginatedResponse;
import masecla.modrinth4j.model.project.ProjectType;
import masecla.modrinth4j.model.project.SupportStatus;
import masecla.modrinth4j.model.search.FacetCollection;

/** 
 * Represents the search endpoint.
 */
public class SearchEndpoint extends PaginatedEndpoint<SearchResult, SearchResponse, SearchRequest> {
    /** 
     * The IndexType to sort the results by.
     */
    public static enum IndexType {
        /** Sort by relevance */
        @SerializedName("relevance")
        RELEVANCE,
        /** Sort by downloads */
        @SerializedName("downloads")
        DOWNLOADS,
        /** Sort by follows */
        @SerializedName("follows")
        FOLLOWS,
        /** Sort by newest */
        @SerializedName("newest")
        NEWEST,
        /** Sort by updated */
        @SerializedName("updated")
        UPDATED;
    }

    /**
     * Represents a search result.
     */
    @Data
    public static class SearchResult {
        /** The slug of the search result */
        private String slug;
        /** The title of the search result */
        private String title;
        /** The description of the search result */
        private String description;

        /** The categories of the search result */
        private List<String> categories;

        /** The support status on the client */
        private SupportStatus clientSide;

        /** The support status on the server */
        private SupportStatus serverSide;

        /** The type of the search result */
        private ProjectType projectType;

        /** The download count of the search result */
        private int downloads;

        /** The icon URL of the search result */
        private String iconUrl;

        /** The ID of the search result */
        private String projectId;

        /** The author of the search result */
        private String author;

        /** The categories to display */
        private List<String> displayCategories;

        /** The versions of the search result */
        private List<String> versions;

        /** The number of followers */
        private int follows;

        /** The date the project was created. ISO-8601 time. */
        private String dateCreated;

        /** The date the project was last modified. ISO-8601 time. */
        private String dateModified;

        /** The latest version of the project */
        private String latestVersion;

        /** The license of the project */
        private String license;

        /** This is a list of URL's */
        private List<String> gallery;
    }

    /**
     * Represents a search request.
     */
    @Data
    @SuperBuilder
    @EqualsAndHashCode(callSuper = true)
    public static class SearchRequest extends PaginatedRequest {
        /** The query to search by */
        private String query;
        /** The index to sort by */
        private IndexType index;
        /** The facets used for filtering */
        private FacetCollection facets;
    }


    /**
     * Represents a search response.
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class SearchResponse extends PaginatedResponse<SearchResult> {
    }

    /**
     * Creates a new search endpoint.
     * 
     * @param client - The client to use
     * @param gson - The gson instance to use
     */
    public SearchEndpoint(HttpClient client, Gson gson) {
        super(client, gson);
    }

    /**
     * Returns the endpoint to use.
     */
    @Override
    public String getEndpoint() {
        return "/search";
    }

    /**
     * Returns the request class to use.
     */
    @Override
    public TypeToken<SearchRequest> getRequestClass() {
        return TypeToken.get(SearchRequest.class);
    }

    /**
     * Returns the response class to use.
     */
    @Override
    public TypeToken<SearchResponse> getResponseClass() {
        return TypeToken.get(SearchResponse.class);
    }

    /**
     * If this endpoint uses a JSON body.
     */
    @Override
    public boolean isJsonBody() {
        return false;
    }

}
