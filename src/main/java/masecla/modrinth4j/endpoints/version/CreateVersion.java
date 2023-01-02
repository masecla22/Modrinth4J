package masecla.modrinth4j.endpoints.version;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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
import lombok.NonNull;
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
        @NonNull
        private String name;

        @NonNull
        private String versionNumber;

        @NonNull
        private String changelog;

        @Default
        private ProjectDependency[] dependencies = new ProjectDependency[0];
        private String[] gameVersions;
        private VersionType versionType;

        @NonNull
        private String[] loaders;

        private boolean featured;

        @NonNull
        private String projectId;

        private String primaryFile;

        @NonNull
        private transient String[] fileNames;

        @NonNull
        private transient InputStream[] fileStreams;

        public static class CreateVersionRequestBuilder {
            public CreateVersionRequestBuilder files(File[] files) {
                try {
                    this.fileNames = new String[files.length];
                    this.fileStreams = new InputStream[files.length];
                    for (int i = 0; i < files.length; i++) {
                        this.fileNames[i] = files[i].getName();
                        this.fileStreams[i] = new FileInputStream(files[i]);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return this;
            }
        }
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

            if (request.getFileNames().length > 1) {
                String primaryFile = request.getPrimaryFile();
                if (primaryFile == null || primaryFile.isEmpty()) {
                    primaryFile = request.getFileNames()[0];
                }
                jsonObject.addProperty("primary_file", primaryFile);
            }

            JsonArray array = new JsonArray();
            for (String filename : request.getFileNames()) {
                array.add(filename);
            }
            jsonObject.add("file_parts", array);

            MultipartBody.Builder body = new MultipartBody.Builder()
                    .addFormDataPart("data", getGson().toJson(jsonObject));

            try {
                for (int i = 0; i < request.getFileNames().length; i++) {
                    body.addFormDataPart(request.getFileNames()[i], request.getFileNames()[i],
                            RequestBody.create(this.readStream(request.getFileStreams()[i])));
                }

                c.post(body.build());

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
