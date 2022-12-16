package masecla.modrinth4j.endpoints.project;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.project.CreateProject.CreateProjectRequest;
import masecla.modrinth4j.exception.EndpointError;
import masecla.modrinth4j.model.project.Project;
import masecla.modrinth4j.model.project.ProjectDonationPlatform;
import masecla.modrinth4j.model.project.ProjectType;
import masecla.modrinth4j.model.project.SupportStatus;

public class CreateProject extends Endpoint<Project, CreateProjectRequest> {

    public CreateProject(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectData {
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
        private ProjectDonationPlatform[] donationUrls;

        private String licenseId;
        private String licenseUrl;

        private ProjectType projectType;

        @Default
        private boolean isDraft = true;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateProjectRequest {
        private ProjectData data;

        private File icon;
    }

    @Override
    public String getEndpoint() {
        return "/project";
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public Class<CreateProjectRequest> getRequestClass() {
        return CreateProjectRequest.class;
    }

    @Override
    public Class<Project> getResponseClass() {
        return Project.class;
    }

    @Override
    public CompletableFuture<Project> sendRequest(CreateProjectRequest parameters, Map<String, String> urlParams) {
        return getClient().connect(getEndpoint()).thenApply(c -> {
            try {
                c.method(getMethod());

                JsonObject obj = getGson().toJsonTree(parameters.getData()).getAsJsonObject();
                obj.add("initial_versions", new JsonArray());
                obj.addProperty("is_draft", true);

                c.data("data", getGson().toJson(obj));
                if (parameters.getIcon() != null)
                    c.data("icon", parameters.getIcon().getName(), new FileInputStream(parameters.getIcon()));

                Response response = c.ignoreContentType(true)
                        .ignoreHttpErrors(true)
                        .execute();

                if (response.body() != null) {
                    JsonElement unparsedObject = getGson().fromJson(response.body(), JsonElement.class);
                    if (unparsedObject.isJsonObject())
                        if (unparsedObject.getAsJsonObject().has("error")) {
                            String error = unparsedObject.getAsJsonObject().get("error").getAsString();
                            String description = unparsedObject.getAsJsonObject().get("description").getAsString();

                            throw new EndpointError(error, description);
                        }
                    Project object = getGson().fromJson(unparsedObject, Project.class);
                    return object;
                } else {
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }).exceptionally(c -> {
            c.printStackTrace();
            return null;
        });
    }

}
