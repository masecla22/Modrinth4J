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

public class ModifyProject extends Endpoint<ProjectModifications, ProjectModifications> {

    @Data
    @AllArgsConstructor
    @SuperBuilder
    public static class ProjectModifications {
        private String slug;
        private String title;
        private String description;

        private List<String> categories;

        private SupportStatus clientSide;
        private SupportStatus serverSide;

        private String body;

        private List<String> additionalCategories;

        private String issuesUrl;
        private String sourceUrl;
        private String wikiUrl;
        private String discordUrl;
        private List<ProjectDonationPlatform> donationUrls;

        private String licenseId;
        private String licenseUrl;

        /** I'm not really sure we can change this */
        private ProjectStatus status;

        /** I'm not really sure we can change this */
        private String moderationMessage;

        /** I'm not really sure we can change this */
        private String moderationMessageBody;
    }

    public ModifyProject(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getMethod() {
        return "PATCH";
    }

    @Override
    public String getEndpoint() {
        return "/project/{id}";
    }

    @Override
    public TypeToken<ProjectModifications> getRequestClass() {
        return TypeToken.get(ProjectModifications.class);
    }

    @Override
    public TypeToken<ProjectModifications> getResponseClass() {
        return TypeToken.get(ProjectModifications.class);
    }
}
