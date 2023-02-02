package masecla.modrinth4j.model.tags;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.model.project.ProjectType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Loader {
    private String icon;
    private String name;
    private List<ProjectType> supportedProjectTypes;

}
