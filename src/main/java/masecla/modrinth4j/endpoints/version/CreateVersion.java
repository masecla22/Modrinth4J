package masecla.modrinth4j.endpoints.version;

import java.io.File;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.version.CreateVersion.CreateVersionRequest;
import masecla.modrinth4j.model.version.ProjectVersion;
import masecla.modrinth4j.model.version.ProjectVersion.ProjectDependency;
import masecla.modrinth4j.model.version.ProjectVersion.VersionType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;

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

            MultipartBody.Builder body = new MultipartBody.Builder()
                    .addFormDataPart("data", getGson().toJson(jsonObject));

            try {
                for (File file : request.getFiles()) 
                    body.addFormDataPart(file.getName(), file.getName(), RequestBody.create(this.readFile(file)));

                Response response = this.getClient().execute(c);

                ProjectVersion version = this.checkBodyForErrors(response.body());
                return version;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
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
    public String getMethod() {
        return "POST";
    }
}
