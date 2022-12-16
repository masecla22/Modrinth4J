package masecla.modrinth4j.model.version;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectVersion {
    private String name;
    private String versionNumber;
    private String changelog;

    private ProjectDependency[] dependencies;

    private String[] gameVersions;

    private String versionType;
    private String[] loaders;

    private boolean featured;

    private String id;
    private String projectId;
    private String authorId;
    private String datePublished;

    private int downloads;

    private ProjectFile[] files;

    public static enum ProjectDependencyType {
        @SerializedName("required")
        REQUIRED,
        @SerializedName("optional")
        OPTIONAL,
        @SerializedName("incompatible")
        INCOMPATIBLE,
        @SerializedName("embedded")
        EMBEDDED;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectDependency {
        private String versionId;
        private String projectId;
        private String fileName;
        private ProjectDependencyType dependencyType;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectFile {
        private ProjectFileHashes hashes;
        private String url;
        private String filename;
        private boolean primary;
        private int size;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectFileHashes {
        private String sha512;
        private String sha1;
    }
}
