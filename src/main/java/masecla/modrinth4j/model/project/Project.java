package masecla.modrinth4j.model.project;

import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a Modrinth project.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    /** The project slug */
    private String slug;

    /** The project title */
    private String title;
    /** The project description */
    private String description;

    /** The project categories */
    private List<String> categories;

    /** The project support status on the client */
    private SupportStatus clientSide;
    /** The project support status on the server */
    private SupportStatus serverSide;

    /** The project body */
    private String body;

    /** The project additional categories */
    private List<String> additionalCategories;

    /** The project issues URL */
    private String issuesUrl;
    /** The project source URL */
    private String sourceUrl;
    /** The project wiki URL */
    private String wikiUrl;
    /** The project discord URL */
    private String discordUrl;

    /** All donation platforms */
    private List<ProjectDonationPlatform> donationUrls;

    /** The project type */
    private ProjectType projectType;

    /** Download count */
    private int downloads;

    /** The project icon URL */
    private String iconUrl;

    /** The project ID */
    private String id;
    /** The project team ID */
    private String team;

    /** A project moderation message */
    private String moderatorMessage;

    /** Date published. */
    private Instant published;

    /** Date published */
    private Instant updated;

    /** Date approved */
    private Instant approved;

    /** Follower count */
    private int followers;

    /** The project status */
    private ProjectStatus status;

    /** The project license */
    private ProjectLicense license;

    /** The project versions */
    private List<String> versions;

    /** The project gallery */
    private List<ProjectGalleryImage> gallery;
}
