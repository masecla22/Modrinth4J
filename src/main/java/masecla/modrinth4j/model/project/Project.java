package masecla.modrinth4j.model.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    private String slug;

    private String title;
    private String description;
    private String[] categories;

    private SupportStatus client_side;
    private SupportStatus server_side;

    private String body;

    private String[] additionalCategories;

    private String issuesUrl;
    private String sourceUrl;
    private String wikiUrl;
    private String discordUrl;
    private ProjectDonationPlatform[] donationUrls;

    private ProjectType projectType;

    private int downloads;

    private String iconUrl;

    private String id;
    private String team;

    private String moderatorMessage;

    /** Date published. ISO 8601 format. */
    private String published;

    /** Date published. ISO 8601 format. */
    private String updated;

    /** Date approved. ISO 8601 format. */
    private String approved;

    private int followers;

    private ProjectStatus status;

    private ProjectLicense license;

    private String[] versions;

    private ProjectGalleryImage[] gallery;
}
