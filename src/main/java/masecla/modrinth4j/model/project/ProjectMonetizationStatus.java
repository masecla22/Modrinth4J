package masecla.modrinth4j.model.project;

import com.google.gson.annotations.SerializedName;

public enum ProjectMonetizationStatus {
    @SerializedName("monetized")
    MONETIZED,

    @SerializedName("demonetized")
    DEMONETIZED,

    @SerializedName("force-demonetized")
    FORCE_DEMONETIZED,
}
