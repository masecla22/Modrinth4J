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

/**
 * This endpoint is used to create a new project.
 */
public class CreateProject extends Endpoint<Project, CreateProjectRequest> {

    /**
     * Creates a new instance of the endpoint.
     * 
     * @param client The client to use.
     * @param gson   The gson instance to use.
     */
    public CreateProject(HttpClient client, Gson gson) {
        super(client, gson);
    }

    /**
     * Represents the data for a project.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectData {
        /** The slug of the project. */
        private String slug;
        /** The title of the project. */
        private String title;
        /** The description of the project. */
        private String description;

        /** The categories of the project. */
        private List<String> categories;

        /** The client-side support status of the project. */
        private SupportStatus clientSide;
        /** The server-side support status of the project. */
        private SupportStatus serverSide;

        /** The body of the project. */
        private String body;

        /** The additional categories of the project. */
        private List<String> additionalCategories;

        /** The issues URL */
        private String issuesUrl;
        /** The source URL */
        private String sourceUrl;
        /** The wiki URL */
        private String wikiUrl;
        /** The discord URL */
        private String discordUrl;
        /** The donation URLs */
        private List<ProjectDonationPlatform> donationUrls;

        /** The license ID */
        private String licenseId;
        /** The license URL */
        private String licenseUrl;

        /** The project type */
        private ProjectType projectType;

        /** If the project is a draft */
        @Default
        private boolean isDraft = true;
    }

    /**
     * Represents the request to create a project.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateProjectRequest {
        /** The data for the project. */
        private ProjectData data;

        /** The icon filename. */
        private String iconFilename;
        /** The icon data. */
        private InputStream iconData;
    }

    /**
     * Returns the endpoint.
     */
    @Override
    public String getEndpoint() {
        return "/project";
    }

    /**
     * Returns the method.
     *
     * @return - The HTTP method being used
     */
    @Override
    public String getMethod() {
        return "POST";
    }

    /**
     * Returns the request class.
     *
     * @return - The {@link TypeToken} for the response.
     */
    @Override
    public TypeToken<CreateProjectRequest> getRequestClass() {
        return TypeToken.get(CreateProjectRequest.class);
    }

    /**
     * Returns the response class.
     *
     * @return - The {@link TypeToken} for the response.
     */
    @Override
    public TypeToken<Project> getResponseClass() {
        return TypeToken.get(Project.class);
    }

    /**
     * Sends the request.
     */
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
