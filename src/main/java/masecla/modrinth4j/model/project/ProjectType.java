package masecla.modrinth4j.model.project;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a project type.
 */
public enum ProjectType {
    /** A mod */
    @SerializedName("mod")
    MOD,
    /** A modpack */
    @SerializedName("resourcepack")
    MODPACK,
    /** A resourcepack */
    @SerializedName("modpack")
    RESOURCEPACK;
}
