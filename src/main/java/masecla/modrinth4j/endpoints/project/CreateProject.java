package masecla.modrinth4j.endpoints.project;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.project.CreateProject.CreateProjectRequest;
import masecla.modrinth4j.model.project.Project;
import masecla.modrinth4j.model.project.ProjectDonationPlatform;
import masecla.modrinth4j.model.project.ProjectType;
import masecla.modrinth4j.model.project.SupportStatus;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;

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

        private String iconFilename;
        private InputStream iconData;
    }

    @Override
    public String getEndpoint() {
        return "/project";
    }

    @Override
    public String getMethod() {
        return "POST";
    }

    @Override
    public TypeToken<CreateProjectRequest> getRequestClass() {
        return TypeToken.get(CreateProjectRequest.class);
    }

    @Override
    public TypeToken<Project> getResponseClass() {
        return TypeToken.get(Project.class);
    }

    @Override
    public CompletableFuture<Project> sendRequest(CreateProjectRequest parameters, Map<String, String> urlParams) {
        return getClient().connect(getEndpoint()).thenApply(c -> {
            try {
                MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();

                JsonObject obj = getGson().toJsonTree(parameters.getData()).getAsJsonObject();
                obj.add("initial_versions", new JsonArray());
                obj.addProperty("is_draft", true);

                bodyBuilder.addFormDataPart("data", getGson().toJson(obj));
                if (parameters.getIconData() != null)
                    bodyBuilder.addFormDataPart("icon", parameters.getIconFilename(),
                            RequestBody.create(readStream(parameters.getIconData())));

                c.method(getMethod(), bodyBuilder.build());

                Response response = getClient().execute(c);
                
                return checkBodyForErrors(response.body());
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
