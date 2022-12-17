package masecla.modrinth4j.model.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModrinthUserNotification {
    private String id;
    private String userId;

    // TODO: It's fully undocumented, but finding all possible types should lead to
    // an enum here.
    private String type;

    private String title;

    private String text;
    private String link;
    private boolean read;

    /** ISO 8601 String */
    private String created;

    /** We have no ideea what this Object is, so a Map is given for now */
    private List<Map<String, Object>> actions = new ArrayList<>();

}
