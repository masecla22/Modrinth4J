package masecla.modrinth4j.model.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectGalleryImage {
    private String url;
    private boolean featured;

    private String title;

    private String description;

    /** ISO 8601 format */
    private String created;
}
