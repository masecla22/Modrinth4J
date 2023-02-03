package masecla.modrinth4j.model.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a notification for a user.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModrinthUserNotification {
    /** The id of the notification */
    private String id;

    /** The id of the user this notification belongs to */
    private String userId;

    // TODO: It's fully undocumented, but finding all possible types should lead to
    // an enum here.
    /** The notification type */
    private String type;

    /** The title of the notification */
    private String title;

    /** The text of the notification */
    private String text;

    /** The link of the notification */
    private String link;

    /** If the notification has been read */
    private boolean read;

    /** ISO 8601 String */
    private String created;

    /** We have no ideea what this Object is, so a Map is given for now */
    private List<Map<String, Object>> actions = new ArrayList<>();

}
