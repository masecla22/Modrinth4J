package masecla.modrinth4j.model.tags;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameVersion {
    private String version;
    private String versionType;

    /** ISO 8601 date */
    private String date;
    private boolean major;
}
