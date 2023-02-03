package masecla.modrinth4j.model.tags;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.model.project.ProjectType;

/**
 * Represents a category.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    /** The category's icon */
    private String icon;
    /** The category's name */
    private String name;
    /** The category's project type */
    private ProjectType projectType;

    /** The category's header */
    private String header;
}
