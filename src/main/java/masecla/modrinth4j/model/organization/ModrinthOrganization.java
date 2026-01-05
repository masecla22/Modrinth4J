package masecla.modrinth4j.model.organization;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.model.team.ModrinthTeamMember;

/**
 * Represents an organization on Modrinth.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModrinthOrganization {
    /** The ID of the organization */
    private String id;
    
    /** The slug/URL identifier of the organization */
    private String slug;
    
    /** The name of the organization */
    private String name;
    
    /** The team ID associated with this organization */
    @SerializedName("team_id")
    private String teamId;
    
    /** The description of the organization */
    private String description;
    
    /** The URL of the organization's icon */
    @SerializedName("icon_url")
    private String iconUrl;
    
    /** The color picked from the organization's icon */
    private Integer color;
    
    /** The members of the organization */
    private List<ModrinthTeamMember> members;
}
