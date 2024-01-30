package masecla.modrinth4j.model.version.files;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.model.version.FileHash;

/**
 * Represents a file type that can be edited.
 * 
 * Used when editing versions.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditableFileType {
    /** The hash algorithm of the hash specified in the hash field */
    private FileHash algorithm;

    /** The hash of the file you're editing */
    private String hash;

    /**
     * The type of the file.
     * 
     * The documentation is unclear on what this is used for.
     */
    public static enum FileType {
        @SerializedName("required-resource-pack")
        REQUIRED_RESOURCE_PACK,

        @SerializedName("optional-resource-pack")
        OPTIONAL_RESOURCE_PACK,
    }

    /**
     * The official docs say this about this field:
     * "The hash algorithm of the file you're editing"
     * 
     * However, that makes no sense, so I assume it's the type of the file.
     */
    private FileType fileType;
}
