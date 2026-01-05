package masecla.modrinth4j.model.user;

import java.time.Instant;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a friend relationship between users on Modrinth.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModrinthUserFriend {
    /** The user ID who accepted the friend request */
    private String id;
    
    /** The user ID who sent the friend request */
    @SerializedName("friend_id")
    private String friendId;
    
    /** Whether the friend request has been accepted */
    private boolean accepted;
    
    /** When the friend request was created */
    private Instant created;
}
