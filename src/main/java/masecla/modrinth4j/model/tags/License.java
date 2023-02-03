package masecla.modrinth4j.model.tags;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a license.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class License {
    /** The license short name */
    @SerializedName("short")
    private String shortName;

    /** The license full name */
    private String name;
}
