package masecla.modrinth4j.client.agent;

import lombok.Builder;

@Builder
public class UserAgent {
    private String projectName;
    private String authorUsername;
    private String projectVersion;
    private String contact;

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
