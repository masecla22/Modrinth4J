package masecla.modrinth4j.model.project;

import java.util.List;

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
    private List<String> categories;

    private SupportStatus clientSide;
    private SupportStatus serverSide;

    private String body;

    private List<String> additionalCategories;

    private String issuesUrl;
    private String sourceUrl;
    private String wikiUrl;
    private String discordUrl;
    private List<ProjectDonationPlatform> donationUrls;

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

    private List<String> versions;

    private List<ProjectGalleryImage> gallery;
}
