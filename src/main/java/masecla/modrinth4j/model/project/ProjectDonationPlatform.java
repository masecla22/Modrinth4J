package masecla.modrinth4j.model.project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a project donation platform.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDonationPlatform {
    /** The donation platform's ID */
    private String id;
    /** The donation platform's name */
    private String platform;
    /** The donation platform's URL */
    private String url;
}
