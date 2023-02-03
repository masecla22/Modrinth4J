package masecla.modrinth4j.model.team;

import lombok.AllArgsConstructor;
import lombok.Data;
import masecla.modrinth4j.model.user.ModrinthUser;

/**
 * Represents a team member.
 */
@Data
@AllArgsConstructor
public class ModrinthTeamMember {
    /** The team ID */
    private String teamId;
    /** The underlying ModrinthUser */
    private ModrinthUser user;
    
    /** The role of the user */
    private String role; // This is not an enum!

    /** The payout split this user has */
    private double payoutsSplit;

    /** The permissions this user has */
    private ModrinthPermissionMask permissions;

    /** If the user has accepted the invite */
    private boolean accepted;
}
