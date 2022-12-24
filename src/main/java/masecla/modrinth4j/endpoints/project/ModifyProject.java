package masecla.modrinth4j.endpoints.project;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.project.ModifyProject.ModifyProjectRequest;
import masecla.modrinth4j.model.project.ProjectStatus;
import masecla.modrinth4j.model.project.SupportStatus;

public class ModifyProject extends Endpoint<ModifyProjectRequest, ModifyProjectRequest> {

    @Data
    @AllArgsConstructor
    @SuperBuilder
    public static class ModifyProjectRequest {
        private String slug;
        private String title;
        private String description;

        private String[] categories;

        private SupportStatus clientSide;
        private SupportStatus serverSide;

        private String body;

        private String[] additionalCategories;

        private String issuesUrl;
        private String sourceUrl;
        private String wikiUrl;
        private String discordUrl;
        private String[] donationUrls;

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
    public Class<ModifyProjectRequest> getRequestClass() {
        return ModifyProjectRequest.class;
    }

    @Override
    public Class<ModifyProjectRequest> getResponseClass() {
        return ModifyProjectRequest.class;
    }
}
