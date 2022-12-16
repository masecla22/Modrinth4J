package masecla.modrinth4j.model.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectLicense {
    private String id;
    private String name;
    private String url;
}
