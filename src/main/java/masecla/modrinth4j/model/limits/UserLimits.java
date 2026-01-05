package masecla.modrinth4j.model.limits;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents user limits for creating resources on Modrinth.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLimits {
    /** The current number of resources */
    private int current;
    
    /** The maximum allowed number of resources */
    private int max;
}
