package masecla.modrinth4j.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Represents an exception thrown by the Modrinth API.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class EndpointException extends RuntimeException {
    /** The error name. */
    private String error;

    /** Further description in the error */
    private String description;
}
