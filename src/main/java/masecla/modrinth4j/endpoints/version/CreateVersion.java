package masecla.modrinth4j.endpoints.version;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
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
import lombok.NonNull;
import lombok.SneakyThrows;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.version.CreateVersion.CreateVersionRequest;
import masecla.modrinth4j.model.version.ProjectVersion;
import masecla.modrinth4j.model.version.ProjectVersion.ProjectDependency;
import masecla.modrinth4j.model.version.ProjectVersion.VersionType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * This endpoint is used to create a new version.
 */
public class CreateVersion extends Endpoint<ProjectVersion, CreateVersionRequest> {
    /**
     * This class is used to represent the request.
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateVersionRequest {
        /** The name of the version */
        @NonNull
        private String name;

        /** The version number of the version */
        @NonNull
        private String versionNumber;

        /** The changelog of the version */
        @NonNull
        private String changelog;

        /** The dependencies of the version */
        @Default
        private List<ProjectDependency> dependencies = new ArrayList<>();
        /** The game versions of the version */
        private List<String> gameVersions;
        /** The type of the version */
        private VersionType versionType;

        /** The loaders of the version */
        @NonNull
        private List<String> loaders;

        /** If the version is featured */
        private boolean featured;

        /** The project ID of the version */
        @NonNull
        private String projectId;

        /** The primary file of the version */
        private String primaryFile;

        /** The files of the version */
        @NonNull
        private transient List<String> fileNames;

        /** The file streams of the version */
        @NonNull
        private transient List<InputStream> fileStreams;

        /**
         * This class is used to build the request.
         */
        public static class CreateVersionRequestBuilder {

            /**
             * This method is used to add files to the request.
             * 
             * @param files - The files to add.
             * @return - The builder.
             */
            public CreateVersionRequestBuilder files(File... files) {
                try {
                    this.fileNames = new ArrayList<>();
                    this.fileStreams = new ArrayList<>();

                    for (int i = 0; i < files.length; i++) {
                        this.fileNames.add(files[i].getName());
                        this.fileStreams.add(new FileInputStream(files[i]));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return this;
            }

            /**
             * This method is used to add files to the request.
             * 
             * @param files - The files to add.
             * @return - The builder.
             */
            public CreateVersionRequestBuilder files(List<File> files) {
                return this.files(files.toArray(new File[files.size()]));
            }
        }
    }

    /**
     * This constructor is used to create a new instance of the endpoint.
     * 
     * @param client - The client to use.
     * @param gson   - The gson instance to use.
     */
    public CreateVersion(HttpClient client, Gson gson) {
        super(client, gson);
    }

    /**
     * Returns the endpoint.
     */
    @Override
    public String getEndpoint() {
        return "/version";
    }

    /**
     * This method will send the request.
     */
    @Override
    public CompletableFuture<ProjectVersion> sendRequest(CreateVersionRequest request, Map<String, String> urlParams) {
        String url = getReplacedUrl(request, urlParams);
        return getClient().connect(url).thenApply(c -> prepareRequest(request, c));
    }

    /**
     * This will prepare and send the request.
     * 
     * @param request - The request to send.
     * @param c - The Request Builder with all authentication.
     * @return - The response.
     */
    @SneakyThrows
    private ProjectVersion prepareRequest(CreateVersionRequest request, Request.Builder c) {
        JsonObject jsonObject = getGson().toJsonTree(request).getAsJsonObject();

        if (request.getFileNames().size() > 1) {
            String primaryFile = request.getPrimaryFile();
            if (primaryFile == null || primaryFile.isEmpty()) {
                primaryFile = request.getFileNames().get(0);
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

        for (int i = 0; i < request.getFileNames().size(); i++) {
            body.addFormDataPart(request.getFileNames().get(i), request.getFileNames().get(i),
                    RequestBody.create(this.readStream(request.getFileStreams().get(i))));
        }

        c.post(body.build());

        Response response = this.getClient().execute(c);
        ProjectVersion version = this.checkBodyForErrors(response.body());
        return version;
    }

    /**
     * Returns the request class to use.
     */
    @Override
    public TypeToken<CreateVersionRequest> getRequestClass() {
        return TypeToken.get(CreateVersionRequest.class);
    }

    /**
     * Returns the response class to use.
     */
    @Override
    public TypeToken<ProjectVersion> getResponseClass() {
        return TypeToken.get(ProjectVersion.class);
    }

    /**
     * Returns the method to use.
     */
    @Override
    public String getMethod() {
        return "POST";
    }
}
