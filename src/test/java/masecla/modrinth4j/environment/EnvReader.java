package masecla.modrinth4j.environment;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;
import masecla.modrinth4j.client.agent.UserAgent;

@Getter
public class EnvReader {
    private String apiKey;
    private String stagingUrl;

    private UserAgent agent;

    public EnvReader() {
        Dotenv env = Dotenv.load();
        this.apiKey = env.get("MODRINTH_STAGING_TOKEN");
        this.stagingUrl = env.get("MODRINTH_STAGING_URL");

        agent = UserAgent.builder().projectName("Modrinth4J Unit Testing")
                                .authorUsername("masecla22")
                                .contact("masecla22#4309")
                                .projectVersion("1.0.0").build();
    }
}
