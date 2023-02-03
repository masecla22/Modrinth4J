package masecla.modrinth4j.environment;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;
import masecla.modrinth4j.client.agent.UserAgent;

/**
 * This class is used to read the environment variables from the .env file
 * during testing.
 */
@Getter
public class EnvReader {
    /** The API key */
    private String apiKey;

    /** The URL of the Modrinth server running the tests on */
    private String stagingUrl;

    /** The UserAgent to identify with */
    private UserAgent agent;

    /**
     * Creates a new EnvReader instance.
     */
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
