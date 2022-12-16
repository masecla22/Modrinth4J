package masecla.modrinth4j.model.project;

import com.google.gson.annotations.SerializedName;

public enum ProjectStatus {
    @SerializedName("approved")
    APPROVED,
    @SerializedName("rejected")
    REJECTED,
    @SerializedName("draft")
    DRAFT,
    @SerializedName("unlisted")
    UNLISTED,
    @SerializedName("archived")
    ARCHIVED,
    @SerializedName("processing")
    PROCESSING,
    @SerializedName("unknown")
    UNKNOWN;
}
