package masecla.modrinth4j.model.project;

import com.google.gson.annotations.SerializedName;

public enum SupportStatus {
    @SerializedName("required")
    REQUIRED, 
    @SerializedName("optional")
    OPTIONAL,
    @SerializedName("unsupported")
    UNSUPPORTED;
}
