package masecla.modrinth4j.model.project;

import com.google.gson.annotations.SerializedName;

public enum ProjectType {
    @SerializedName("mod")
    MOD, 
    @SerializedName("resourcepack")
    MODPACK, 
    @SerializedName("modpack")
    RESOURCEPACK;
}
