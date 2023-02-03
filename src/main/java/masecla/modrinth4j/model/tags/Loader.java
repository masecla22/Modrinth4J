package masecla.modrinth4j.model.tags;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.model.project.ProjectType;

/**
 * Represents a loader.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Loader {
    /** The icon of the loader */
    private String icon;
    /** The name of the loader */
    private String name;

    /** The supported project types */
    private List<ProjectType> supportedProjectTypes;

}
