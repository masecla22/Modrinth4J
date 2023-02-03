package masecla.modrinth4j.endpoints.project;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.project.ModifyProject.ProjectModifications;
import masecla.modrinth4j.model.project.ProjectDonationPlatform;
import masecla.modrinth4j.model.project.ProjectStatus;
import masecla.modrinth4j.model.project.SupportStatus;

/**
 * This endpoint is used to modify a project.
 */
public class ModifyProject extends Endpoint<ProjectModifications, ProjectModifications> {

    /**
     * Represents the request (and response) to this endpoint
     */
    @Data
    @AllArgsConstructor
    @SuperBuilder
    public static class ProjectModifications {
        /** The slug of the project */
        private String slug;

        /** The title of the project */
        private String title;

        /** The description of the project */
        private String description;

        /** The categories of the project */
        private List<String> categories;

        /** The client side support status of the project */
        private SupportStatus clientSide;

        /** The server side support status of the project */
        private SupportStatus serverSide;

        /** The body of the project */
        private String body;

        /** Additional categories of the project */
        private List<String> additionalCategories;

        /** The issues url of the project */
        private String issuesUrl;
        /** The source url of the project */
        private String sourceUrl;
        /** The wiki url of the project */
        private String wikiUrl;
        /** The discord url of the project */
        private String discordUrl;

        /** The donation urls of the project */
        private List<ProjectDonationPlatform> donationUrls;

        /** The license id of the project */
        private String licenseId;
        /** The license url of the project */
        private String licenseUrl;

        /** The project status */
        private ProjectStatus status;

        /** The moderation message */
        private String moderationMessage;

        /** The moderation message body */
        private String moderationMessageBody;
    }

    /**
     * Creates a new instance of the endpoint.
     * 
     * @param client The client to use.
     * @param gson   The gson instance to use.
     */
    public ModifyProject(HttpClient client, Gson gson) {
        super(client, gson);
    }

    /**
     * Returns the method used.
     * 
     * @return The method used.
     */
    @Override
    public String getMethod() {
        return "PATCH";
    }

    /**
     * Returns the endpoint used.
     * 
     * @return The endpoint used.
     */
    @Override
    public String getEndpoint() {
        return "/project/{id}";
    }

    /**
     * Returns the request class.
     * 
     * @return The request class.
     */
    @Override
    public TypeToken<ProjectModifications> getRequestClass() {
        return TypeToken.get(ProjectModifications.class);
    }

    /**
     * Returns the response class.
     * 
     * @return The response class.
     */
    @Override
    public TypeToken<ProjectModifications> getResponseClass() {
        return TypeToken.get(ProjectModifications.class);
    }
}
