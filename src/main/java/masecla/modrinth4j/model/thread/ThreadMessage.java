package masecla.modrinth4j.model.thread;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a message in a thread.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThreadMessage {
    /** The ID of the message */
    private String id;
    
    /** The author's user ID */
    @SerializedName("author_id")
    private String authorId;
    
    /** The message body/content */
    private MessageBody body;
    
    /** When the message was created */
    private Instant created;
    
    /** Whether to hide the author's identity */
    @SerializedName("hide_identity")
    private boolean hideIdentity;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MessageBody {
        /** The type of message */
        private String type;
        
        /** The text body (if type is "text") */
        private String body;
        
        /** Whether the message is private */
        private Boolean private_;
        
        /** ID of message being replied to */
        @SerializedName("replying_to")
        private String replyingTo;
        
        /** Associated image IDs */
        @SerializedName("associated_images")
        private List<String> associatedImages;
        
        /** New status (if type is "status_change") */
        @SerializedName("new_status")
        private String newStatus;
        
        /** Old status (if type is "status_change") */
        @SerializedName("old_status")
        private String oldStatus;
        
        /** Additional fields for other message types */
        private Map<String, Object> additionalProperties;
    }
}
