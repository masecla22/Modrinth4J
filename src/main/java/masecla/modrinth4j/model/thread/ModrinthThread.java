package masecla.modrinth4j.model.thread;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.model.user.ModrinthUser;

/**
 * Represents a thread (conversation) on Modrinth.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModrinthThread {
    /** The ID of the thread */
    private String id;
    
    /** The type of thread */
    private ThreadType type;
    
    /** The project ID if this is a project thread */
    @SerializedName("project_id")
    private String projectId;
    
    /** The report ID if this is a report thread */
    @SerializedName("report_id")
    private String reportId;
    
    /** The messages in the thread */
    private List<ThreadMessage> messages;
    
    /** The members of the thread */
    private List<ModrinthUser> members;
}
