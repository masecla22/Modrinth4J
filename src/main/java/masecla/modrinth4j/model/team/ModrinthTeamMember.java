package masecla.modrinth4j.model.team;

import lombok.AllArgsConstructor;
import lombok.Data;
import masecla.modrinth4j.model.user.ModrinthUser;

@Data
@AllArgsConstructor
public class ModrinthTeamMember {
    private String teamId;
    private ModrinthUser user;
    
    private String role; // This is not an enum!

    private double payoutsSplit;

    private ModrinthPermissionMask permissions;

    private boolean accepted;
}
