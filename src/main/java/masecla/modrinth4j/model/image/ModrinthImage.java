package masecla.modrinth4j.model.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an uploaded image on Modrinth.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModrinthImage {
    /** The URL of the image */
    private String url;
    
    /** The ID of the image */
    private String id;
}
