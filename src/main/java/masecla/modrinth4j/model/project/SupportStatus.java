package masecla.modrinth4j.model.project;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a support status. This is used to determine if a mod supports a
 * specific environment, such as CLIENT or SERVER.
 */
public enum SupportStatus {
    /** The project is required to run under this. */
    @SerializedName("required")
    REQUIRED,
    /** The project is optional to run under this. */
    @SerializedName("optional")
    OPTIONAL,
    /** The project does not support this. */
    @SerializedName("unsupported")
    UNSUPPORTED;
}
