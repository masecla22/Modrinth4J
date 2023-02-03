package masecla.modrinth4j.model.project;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a project status.
 */
public enum ProjectStatus {
    /** The project is approved and public. */
    @SerializedName("approved")
    APPROVED,
    /** The project is rejected. */
    @SerializedName("rejected")
    REJECTED,
    /** The project is a draft. */
    @SerializedName("draft")
    DRAFT,
    /** The project is unlisted. */
    @SerializedName("unlisted")
    UNLISTED,
    /** The project is archived. */
    @SerializedName("archived")
    ARCHIVED,
    /** The project is processing. */
    @SerializedName("processing")
    PROCESSING,
    /** The project is unknown. */
    @SerializedName("unknown")
    UNKNOWN;
}
