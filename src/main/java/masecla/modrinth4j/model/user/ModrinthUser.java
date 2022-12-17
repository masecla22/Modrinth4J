package masecla.modrinth4j.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModrinthUser {
    private String username;
    private String name;
    private String email;

    private String bio;

    private String id;
    private String githubId;
    private String avatarUrl;

    /** ISO 8601 String */
    private String created;

    private ModrinthUserRole role;
}
