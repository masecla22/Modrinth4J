package masecla.modrinth4j.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 
 * Represents a user on Modrinth.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModrinthUser {
    /** The user's username */
    private String username;

    /** The user's name */
    private String name;

    /** The user's email */
    private String email;

    /** The user's bio */
    private String bio;

    /** The user's id */
    private String id;

    /** The user's github id */
    private String githubId;

    /** The user's avatar url */
    private String avatarUrl;

    /** ISO 8601 String */
    private String created;

    /** The user's role on Modrinth */
    private ModrinthUserRole role;
}
