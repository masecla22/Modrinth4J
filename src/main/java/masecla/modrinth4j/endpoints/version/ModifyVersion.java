package masecla.modrinth4j.endpoints.version;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.version.ModifyVersion.ModifyVersionRequest;
import masecla.modrinth4j.model.version.ProjectVersion;
import masecla.modrinth4j.model.version.ProjectVersion.ProjectDependency;
import masecla.modrinth4j.model.version.ProjectVersion.VersionType;

/**
 * This endpoint is used to modify a version.
 */
public class ModifyVersion extends Endpoint<ProjectVersion, ModifyVersionRequest> {
    /**
     * Represents a request to modify a version.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ModifyVersionRequest {
        /** The name of the version */
        private String name;
        /** The version number */
        private String versionNumber;
        /** The changelog of the version */
        private String changelog;
        /** The dependencies of the version */
        private List<ProjectDependency> dependencies;

        /** The game versions of the version */
        private List<String> gameVersions;
        /** The type of the version */
        private VersionType versionType;

        /** The loaders of the version */
        private List<String> loaders;
        /** If the version is featured */
        private boolean featured;

        /**
         * This is expected to be a length 2 array, where the first one is expected to
         * contains a hash type (such as SHA1) and the second one is meant to contain
         * the hash itself. This should be refactored into a class in the future.
         */
        private String[] primaryFile;
    }

    /**
     * Creates a new instance of the {@link ModifyVersion} endpoint.
     * 
     * @param client The {@link HttpClient} to use.
     * @param gson   The {@link Gson} instance to use.
     */
    public ModifyVersion(HttpClient client, Gson gson) {
        super(client, gson);
    }

    /**
     * This URL of the endpoint.
     */
    @Override
    public String getEndpoint() {
        return "/version/{id}";
    }

    /**
     * Returns the request class to use.
     */
    @Override
    public TypeToken<ModifyVersionRequest> getRequestClass() {
        return TypeToken.get(ModifyVersionRequest.class);
    }

    /**
     * Returns the response class to use.
     */
    @Override
    public TypeToken<ProjectVersion> getResponseClass() {
        return TypeToken.get(ProjectVersion.class);
    }

    /**
     * Returns the method of the endpoint.
     */
    @Override
    public String getMethod() {
        return "PATCH";
    }
}
