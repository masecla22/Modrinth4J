package masecla.modrinth4j.endpoints.version;

import java.io.File;
import java.io.FileInputStream;
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
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.version.CreateVersion.CreateVersionRequest;
import masecla.modrinth4j.exception.EndpointError;
import masecla.modrinth4j.model.version.ProjectVersion;
import masecla.modrinth4j.model.version.ProjectVersion.ProjectDependency;
import masecla.modrinth4j.model.version.ProjectVersion.VersionType;

public class CreateVersion extends Endpoint<ProjectVersion, CreateVersionRequest> {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateVersionRequest {
        private String name;
        private String versionNumber;
        private String changelog;

        @Default
        private ProjectDependency[] dependencies = new ProjectDependency[0];
        private String[] gameVersions;
        private VersionType versionType;
        private String[] loaders;
        private boolean featured;

        private String projectId;

        private String primaryFile;

        private transient File[] files;
    }

    public CreateVersion(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/version";
    }

    @Override
    public CompletableFuture<ProjectVersion> sendRequest(CreateVersionRequest request, Map<String, String> urlParams) {
        String url = getReplacedUrl(request, urlParams);
        return getClient().connect(url).thenApply(c -> {
            c.method(getMethod());

            JsonObject jsonObject = getGson().toJsonTree(request).getAsJsonObject();

            if (request.getFiles().length > 1) {
                String primaryFile = request.getPrimaryFile();
                if (primaryFile == null || primaryFile.isEmpty()) {
                    primaryFile = request.getFiles()[0].getName();
                }
                jsonObject.addProperty("primary_file", primaryFile);
            }

            JsonArray array = new JsonArray();
            for (File file : request.getFiles()) {
                array.add(file.getName());
            }
            jsonObject.add("file_parts", array);

            c.data("data", getGson().toJson(jsonObject));

            try {
                for (File file : request.getFiles()) {
                    c.data(file.getName(), file.getName(), new FileInputStream(file));
                }

                Response response = c.ignoreContentType(true)
                        .ignoreHttpErrors(true)
                        .execute();

                if (response.body() != null) {
                    JsonElement unparsedObject = null;
                    try {
                        unparsedObject = getGson().fromJson(response.body(), JsonElement.class);
                    } catch (Exception e) {
                        throw new EndpointError("invalid-json",
                                "Expected JSON response from endpoint, received: " + response.body() + "");
                    }
                    if (unparsedObject != null) {
                        if (unparsedObject.isJsonObject())
                            if (unparsedObject.getAsJsonObject().has("error")) {
                                String error = unparsedObject.getAsJsonObject().get("error").getAsString();
                                String description = unparsedObject.getAsJsonObject().get("description").getAsString();

                                throw new EndpointError(error, description);
                            }
                        ProjectVersion object = getGson().fromJson(unparsedObject, getResponseClass());
                        return object;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            return null;
        });
    }

    @Override
    public Class<CreateVersionRequest> getRequestClass() {
        return CreateVersionRequest.class;
    }

    @Override
    public Class<ProjectVersion> getResponseClass() {
        return ProjectVersion.class;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }
}
