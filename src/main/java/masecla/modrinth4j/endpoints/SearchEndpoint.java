package masecla.modrinth4j.endpoints;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import masecla.modrinth4j.endpoints.generic.PaginatedEndpoint.PaginatedRequest;
import masecla.modrinth4j.model.project.ProjectType;
import masecla.modrinth4j.model.project.SupportStatus;

public class SearchEndpoint {
    public static enum IndexType {
        RELEVANCE, DOWNLOADS, FOLLOWS, NEWEST, UPDATED;
    }

    @Data
    public static class SearchResult {
        private String slug;
        private String title;
        private String description;

        private String[] categories;

        @SerializedName("client_side")
        private SupportStatus clientSide;

        @SerializedName("server_side")
        private SupportStatus serverSide;

        @SerializedName("project_type")
        private ProjectType projectType;

        private int downloads;

        @SerializedName("icon_url")
        private String iconUrl;

        @SerializedName("project_id")
        private String projectId;

        private String author;

        @SerializedName("display_categories")
        private String[] displayCategories;

        private String[] versions;

        private int follows;

        /** The date the project was created. ISO-8601 time. */
        @SerializedName("date_created")
        private String dateCreated;

        /** The date the project was last modified. ISO-8601 time. */
        @SerializedName("date_modified")
        private String dateModified;

        @SerializedName("latest_version")
        private String latestVersion;

        private String license;

        /** This is a list of URL's */
        private String[] gallery;
    }

    public static class SearchRequest extends PaginatedRequest {
        private String query;
        
    }
}
