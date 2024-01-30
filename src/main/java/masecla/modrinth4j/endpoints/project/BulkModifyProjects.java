package masecla.modrinth4j.endpoints.project;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;
import masecla.modrinth4j.endpoints.project.BulkModifyProjects.BulkModifyProjectsRequest;
import masecla.modrinth4j.model.project.ProjectDonationPlatform;

public class BulkModifyProjects extends Endpoint<EmptyResponse, BulkModifyProjectsRequest> {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BulkModifyProjectsRequest {
        /**
         * A list of IDs and/or slugs of the projects. Marked as transient as it will
         * not show up in the request itself.
         */
        private transient List<String> ids;

        /** Set all of the categories to the categories specified here */
        private List<String> categories;

        /** Add all of the categories specified here */
        private List<String> addCategories;

        /** Remove all of the categories specified here */
        private List<String> removeCategories;

        /** Set all of the additional categories to the categories specified here */
        private List<String> additionalCategories;

        /** Add all of the additional categories specified here */
        private List<String> addAdditionalCategories;

        /** Remove all of the additional categories specified here */
        private List<String> removeAdditionalCategories;

        /** Set all of the donation links to the donation links specified here */
        private List<ProjectDonationPlatform> donationUrls;

        /** Add all of the donation links specified here */
        private List<ProjectDonationPlatform> addDonationUrls;

        /** Remove all of the donation links specified here */
        private List<ProjectDonationPlatform> removeDonationUrls;

        /** An optional link to where to submit bugs or issues with the projects */
        private String issuesUrl;

        /** An optional link to the source code of the projects */
        private String sourceUrl;

        /** An optional link to the projects' wiki page or other relevant information */
        private String wikiUrl;

        /** An optional invite link to the projects' discord */
        private String discordUrl;
    }

    /**
     * Creates a new instance of the endpoint.
     * 
     * @param client - The client to use.
     * @param gson   - The gson instance to use.
     */
    public BulkModifyProjects(HttpClient client, Gson gson) {
        super(client, gson);
    }

    /**
     * Gets the endpoint of the request.
     * 
     * @return The endpoint of the request.
     */
    @Override
    public String getEndpoint() {
        return "/projects";
    }

    /**
     * Returns the request class.
     *
     * @return - The {@link TypeToken} for the response.
     */
    @Override
    public TypeToken<BulkModifyProjectsRequest> getRequestClass() {
        return TypeToken.get(BulkModifyProjectsRequest.class);
    }

    /**
     * Returns the response class.
     *
     * @return - The {@link TypeToken} for the response.
     */
    @Override
    public TypeToken<EmptyResponse> getResponseClass() {
        return TypeToken.get(EmptyResponse.class);
    }

    /**
     * Injects the project ID into the URL.
     */
    @Override
    protected String getReplacedUrl(BulkModifyProjectsRequest request, Map<String, String> parameters) {
        // We know there are no params, so we can just return the original URL
        String originalUrl = super.getReplacedUrl(request, parameters);

        // This will handle the conversion and serialization for us.
        // We don't want to do it manually!
        String convertedJson = this.getGson().toJson(request.getIds());

        return originalUrl + "?ids=" + convertedJson;
    }

    /**
     * Returns the method of the request.
     */
    @Override
    public String getMethod() {
        return "PATCH";
    }
}
