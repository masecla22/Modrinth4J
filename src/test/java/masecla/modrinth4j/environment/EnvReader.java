package masecla.modrinth4j.environment;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;

@Getter
public class EnvReader {
    private String apiKey;
    private String stagingUrl;

    public EnvReader() {
        Dotenv env = Dotenv.load();
        this.apiKey = env.get("MODRINTH_STAGING_TOKEN");
        this.stagingUrl = env.get("MODRINTH_STAGING_URL");
    }
}
