package masecla.modrinth4j.model.version;

import java.time.Instant;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a version of a project.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectVersion {
    /** The name of the version */
    private String name;
    /** The version number of the version */
    private String versionNumber;
    /** The changelog of the version */
    private String changelog;

    /** The dependencies of the version */
    private List<ProjectDependency> dependencies;

    /** The game versions this version supports */
    private List<String> gameVersions;

    /** The version type */
    private VersionType versionType;

    /** The loaders for this version */
    private List<String> loaders;

    /** If this version is featured */
    private boolean featured;

    /** The id of this version */
    private String id;

    /** The id of the project this version belongs to */
    private String projectId;

    /** The id of the author of this version */
    private String authorId;

    /** The date this version was published. */
    private Instant datePublished;

    /** The amount of downloads this version has */
    private int downloads;

    /** The files for this version */
    private List<ProjectFile> files;

    /**
     * The type of the version
     */
    public static enum VersionType {
        /** If this version was released on the "release" channel */
        @SerializedName("release")
        RELEASE,
        /** If this version was released on the "beta" channel */
        @SerializedName("beta")
        BETA,
        /** If this version was released on the "alpha" channel */
        @SerializedName("alpha")
        ALPHA;
    }

    /**
     * The type of the dependency
     */
    public static enum ProjectDependencyType {
        /** If this dependency is required */
        @SerializedName("required")
        REQUIRED,
        /** If this dependency is optional */
        @SerializedName("optional")
        OPTIONAL,
        /** If this dependency is incompatible */
        @SerializedName("incompatible")
        INCOMPATIBLE,
        /** If this dependency is embedded */
        @SerializedName("embedded")
        EMBEDDED;
    }

    /**
     * Represents a dependency of a version
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectDependency {
        /** The version id this dependency belongs to */
        private String versionId;

        /** The projetc id this dependency belongs to */
        private String projectId;

        /** The file name */
        private String fileName;

        /** The type of the dependency */
        private ProjectDependencyType dependencyType;
    }

    /**
     * Represents a file of a version
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectFile {
        /** The hashes of the file */
        private ProjectFileHashes hashes;

        /** The url for this file */
        private String url;

        /** The filename for this file */
        private String filename;

        /** Whether this is the primary file */
        private boolean primary;

        /** The size of the file */
        private int size;
    }

    /**
     * Represents the hashes of a file
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectFileHashes {
        /** The SHA512 for the file */
        private String sha512;
        /** The SHA1 for the file */
        private String sha1;
    }
}
