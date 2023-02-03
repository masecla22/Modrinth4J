package masecla.modrinth4j.model.version;

import com.google.gson.annotations.SerializedName;

/**
 * The FileHash enum represents the hash type of a file.
 */
public enum FileHash {
    /** Represents the SHA1 hash */
    @SerializedName("sha1")
    SHA1,
    /** Represents the SHA256 hash */
    @SerializedName("sha256")
    SHA256;
}
