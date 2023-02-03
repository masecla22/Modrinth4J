package masecla.modrinth4j.model.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a project license.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectLicense {
    /** The license's ID */
    private String id;
    /** The license's name */
    private String name;
    /** The license's URL */
    private String url;
}
