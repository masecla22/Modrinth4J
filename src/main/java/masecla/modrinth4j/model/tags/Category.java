package masecla.modrinth4j.model.tags;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.model.project.ProjectType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private String icon;
    private String name;
    private ProjectType projectType;

    private String header;
}
