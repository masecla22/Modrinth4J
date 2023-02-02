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

public class ModifyVersion extends Endpoint<ProjectVersion, ModifyVersionRequest> {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ModifyVersionRequest {
        private String name;
        private String versionNumber;
        private String changelog;
        private List<ProjectDependency> dependencies;

        private List<String> gameVersions;
        private VersionType versionType;

        private List<String> loaders;
        private boolean featured;

        /**
         * This is expected to be a length 2 array, where the first one is expected to
         * contains a hash type (such as SHA1) and the second one is meant to contain
         * the hash itself. This should be refactored into a class in the future.
         */
        private String[] primaryFile;
    }

    public ModifyVersion(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/version/{id}";
    }

    @Override
    public TypeToken<ModifyVersionRequest> getRequestClass() {
        return TypeToken.get(ModifyVersionRequest.class);
    }

    @Override
    public TypeToken<ProjectVersion> getResponseClass() {
        return TypeToken.get(ProjectVersion.class);
    }

    @Override
    public String getMethod() {
        return "PATCH";
    }
}
