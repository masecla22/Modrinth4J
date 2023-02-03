package masecla.modrinth4j.client.agent;

import lombok.Builder;

/**
 * This class is used to represent a user agent.
 */
@Builder
public class UserAgent {
    /** The name of the project. */
    private String projectName;
    /** The username of the author. */
    private String authorUsername;
    /** The version of the project. */
    private String projectVersion;
    /** The contact of the project. */
    private String contact;

    /**
     * Converts the user agent to a string.
     */
    @Override
    public String toString() {
        if (projectName == null && authorUsername == null && contact == null) {
            return "Modrinth4J / No User Agent";
        }
        StringBuilder result = new StringBuilder();
        if (projectName != null) {
            if (authorUsername != null)
                result = result.append(authorUsername).append("/").append(projectName);
            else
                result = result.append(projectName);
        } else {
            if (authorUsername != null)
                result = result.append(authorUsername);
        }

        if (projectVersion != null) {
            result = result.append("/").append(projectVersion);
        }

        if (contact != null) {
            result = result.append(" (").append(contact).append(")");
        }

        return result.toString().trim();
    }
}
