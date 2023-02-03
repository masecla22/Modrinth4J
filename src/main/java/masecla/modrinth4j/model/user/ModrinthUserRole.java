package masecla.modrinth4j.model.user;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a user role on Modrinth.
 */
public enum ModrinthUserRole {
    /** Admin role */
    @SerializedName("admin")
    ADMIN,
    /** Moderator role */
    @SerializedName("moderator")
    MODERATOR,
    /** Developer role (everyone has this by default) */
    @SerializedName("developer")
    DEVELOPER;
}
