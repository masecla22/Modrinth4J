package masecla.modrinth4j.model.project;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a project gallery image.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectGalleryImage {
    /** The image's URL */
    private String url;

    /** Whether it's featured */
    private boolean featured;

    /** The image's title */
    private String title;

    /** The image's description */
    private String description;

    /** When the gallery image was created */
    private Instant created;
}
