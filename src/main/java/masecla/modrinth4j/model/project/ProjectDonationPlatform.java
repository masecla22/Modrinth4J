package masecla.modrinth4j.model.project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDonationPlatform {
    private String id;
    private String platform;
    private String url;
}
