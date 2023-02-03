package masecla.modrinth4j.model.tags;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a donation platform.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonationPlatform {
    /** The donation platform short name */
    @SerializedName("short")
    private String shortName;

    /** The donation platform full name */
    private String name;
}
