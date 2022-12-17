package masecla.modrinth4j.model.version;

import com.google.gson.annotations.SerializedName;

public enum FileHash {
    @SerializedName("sha1")
    SHA1,
    @SerializedName("sha256")
    SHA256;
}
