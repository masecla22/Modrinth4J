package masecla.modrinth4j.model.tags;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a game version.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameVersion {
    /** The game version's name */
    private String version;
    /** The game version's type */
    private String versionType;

    /** When this game version was released */
    private Instant date;

    /** If this is a major version */
    private boolean major;
}
