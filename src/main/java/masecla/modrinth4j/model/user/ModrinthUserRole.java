package masecla.modrinth4j.model.user;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a user role on Modrinth.
 */
public enum ModrinthUserRole {
    @SerializedName("admin")
    ADMIN,
    @SerializedName("moderator")
    MODERATOR,
    @SerializedName("developer")
    DEVELOPER;
}
