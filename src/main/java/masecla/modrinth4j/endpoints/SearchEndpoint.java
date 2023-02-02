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

public class SearchEndpoint extends PaginatedEndpoint<SearchResult, SearchResponse, SearchRequest> {
    public static enum IndexType {
        @SerializedName("relevance")
        RELEVANCE,
        @SerializedName("downloads")
        DOWNLOADS,
        @SerializedName("follows")
        FOLLOWS,
        @SerializedName("newest")
        NEWEST,
        @SerializedName("updated")
        UPDATED;
    }

    @Data
    public static class SearchResult {
        private String slug;
        private String title;
        private String description;

        private List<String> categories;

        private SupportStatus clientSide;

        private SupportStatus serverSide;

        private ProjectType projectType;

        private int downloads;

        private String iconUrl;

        private String projectId;

        private String author;

        private List<String> displayCategories;

        private List<String> versions;

        private int follows;

        /** The date the project was created. ISO-8601 time. */
        private String dateCreated;

        /** The date the project was last modified. ISO-8601 time. */
        private String dateModified;

        private String latestVersion;

        private String license;

        /** This is a list of URL's */
        private List<String> gallery;
    }

    @Data
    @SuperBuilder
    @EqualsAndHashCode(callSuper = true)
    public static class SearchRequest extends PaginatedRequest {
        private String query;
        private IndexType index;
        private FacetCollection facets;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class SearchResponse extends PaginatedResponse<SearchResult> {
    }

    public SearchEndpoint(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/search";
    }

    @Override
    public TypeToken<SearchRequest> getRequestClass() {
        return TypeToken.get(SearchRequest.class);
    }

    @Override
    public TypeToken<SearchResponse> getResponseClass() {
        return TypeToken.get(SearchResponse.class);
    }

    @Override
    public boolean isJsonBody() {
        return false;
    }

}
