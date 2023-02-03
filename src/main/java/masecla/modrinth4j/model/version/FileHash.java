package masecla.modrinth4j.model.version;

import com.google.gson.annotations.SerializedName;

/** 
 * The FileHash enum represents the hash type of a file.
 */
public enum FileHash {
    @SerializedName("sha1")
    SHA1,
    @SerializedName("sha256")
    SHA256;
}
