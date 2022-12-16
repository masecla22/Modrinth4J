package masecla.modrinth4j.endpoints;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import masecla.modrinth4j.endpoints.generic.PaginatedEndpoint.PaginatedRequest;
import masecla.modrinth4j.model.project.ProjectType;
import masecla.modrinth4j.model.project.SupportStatus;

public class SearchEndpoint {
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

        private String[] categories;

        private SupportStatus clientSide;

        private SupportStatus serverSide;

        private ProjectType projectType;

        private int downloads;

        private String iconUrl;

        private String projectId;

        private String author;

        private String[] displayCategories;

        private String[] versions;

        private int follows;

        /** The date the project was created. ISO-8601 time. */
        private String dateCreated;

        /** The date the project was last modified. ISO-8601 time. */
        private String dateModified;

        private String latestVersion;

        private String license;

        /** This is a list of URL's */
        private String[] gallery;
    }

    public static class SearchRequest extends PaginatedRequest {
        private String query;
        
    }
}
