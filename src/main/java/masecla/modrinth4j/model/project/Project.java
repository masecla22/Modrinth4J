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

    /** The project status */
    private ProjectStatus status;

    /**
     * The requested status when submitting for review or scheduling the project for
     * release
     */
    private ProjectStatus requestedStatus;

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

    /**
     * The RGB color of the project, automatically generated from the project icon
     */
    private int color;

    /** The ID of the moderation thread associated with this project */
    private String threadId;

    /** The monetization status of the project */
    private ProjectMonetizationStatus monetizationStatus;

    /** The project ID */
    private String id;

    /** The project team ID */
    private String team;

    /**
     * A project moderation message
     *
     * @deprecated This field is deprecated in favor of {@link #threadId}
     */
    @Deprecated
    private String moderatorMessage;

    /** Date published. */
    private Instant published;

    /** Date published */
    private Instant updated;

    /** Date approved */
    private Instant approved;

    /** Date queued */
    private Instant queued;

    /** Follower count */
    private int followers;

    /** The project license */
    private ProjectLicense license;

    /** The project versions */
    private List<String> versions;

    /** A list of all of the game versions supported by the project */
    private List<String> gameVersions;

    /** A list of all of the loaders supported by the project */
    private List<String> loaders;

    /** The project gallery */
    private List<ProjectGalleryImage> gallery;
}
