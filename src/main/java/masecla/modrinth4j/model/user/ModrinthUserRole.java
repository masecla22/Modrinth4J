package masecla.modrinth4j.model.user;

import com.google.gson.annotations.SerializedName;

public enum ModrinthUserRole {
    @SerializedName("admin")
    ADMIN,
    @SerializedName("moderator")
    MODERATOR,
    @SerializedName("developer")
    DEVELOPER;
}
