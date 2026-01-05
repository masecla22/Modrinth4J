package masecla.modrinth4j.model.collection;

import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a collection returned from the Modrinth API.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModrinthCollection {
    /** The ID of the collection, encoded as a base62 string */
    private String id;
    
    /** The person that has ownership of this collection */
    private String user;
    
    /** The title or name of the collection */
    private String name;
    
    /** A short description of the collection */
    private String description;
    
    /** An icon URL for the collection */
    private String iconUrl;
    
    /** Color of the collection */
    private Integer color;
    
    /** The status of the collection (eg: whether collection is public or not) */
    private CollectionStatus status;
    
    /** The date at which the collection was first published */
    private Instant created;
    
    /** The date at which the collection was updated */
    private Instant updated;
    
    /** A list of ProjectIds that are in this collection */
    private List<String> projects;
}
