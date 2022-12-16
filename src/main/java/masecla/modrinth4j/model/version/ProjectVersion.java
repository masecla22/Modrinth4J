package masecla.modrinth4j.model.version;

public class ProjectVersion {
    private String name;
    private String versionNumber;
    private String changelog;

    private ProjectDependencies[] dependencies;

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

    private static class ProjectDependencies {
        private String versionId;
        private String projectId;
        private String fileName;
        private String dependencyType;
    }

    private static class ProjectFile {
        private ProjectFileHashes hashes;
        private String url;
        private String filename;
        private boolean primary;
        private int size;
    }

    private static class ProjectFileHashes {
        private String sha512;
        private String sha1;
    }
}
