package masecla.modrinth4j.model.project;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a project status.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ProjectStatus {
    /** The project is approved and public. */
    @SerializedName("approved")
    APPROVED(true),
    /** The project is rejected. */
    @SerializedName("rejected")
    REJECTED,
    /** The project is a draft. */
    @SerializedName("draft")
    DRAFT(true),
    /** The project is unlisted. */
    @SerializedName("unlisted")
    UNLISTED(true),
    /** The project is archived. */
    @SerializedName("archived")
    ARCHIVED(true),
    /** The project is processing. */
    @SerializedName("processing")
    PROCESSING,

    /** The project is withheld */
    @SerializedName("withheld")
    WITHHELD,

    /** The project is scheduled */
    @SerializedName("scheduled")
    SCHEDULED,

    /** The project is private */
    @SerializedName("private")
    PRIVATE(true),

    /** The project is unknown. */
    @SerializedName("unknown")
    UNKNOWN;

    private boolean canBeRequested = false;
}
