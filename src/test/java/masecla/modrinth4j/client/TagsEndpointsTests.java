package masecla.modrinth4j.client;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import masecla.modrinth4j.environment.EnvReader;
import masecla.modrinth4j.main.ModrinthAPI;

/**
 * Tests the {@link TagsEndpoints} class.
 */
public class TagsEndpointsTests {
    /** The client to be used */
    private ModrinthAPI client;

    /**
     * Sets up the client.
     */
    @BeforeEach
    public void setupClient() {
        EnvReader env = new EnvReader();
        this.client = ModrinthAPI.rateLimited(env.getAgent(), env.getStagingUrl(), env.getApiKey());

    }

    /**
     * This method tests getting all the categories.
     */
    @Test
    public void testGetCategories() {
        assertNotNull(client.tags().getCategories().join());
    }

    /**
     * This method tests getting all the donation platforms.
     */
    @Test
    public void testGetDonationPlatforms() {
        assertNotNull(client.tags().getDonationPlatforms().join());
    }

    /**
     * This method tests getting all the loaders.
     */
    @Test
    public void testGetLoaders() {
        assertNotNull(client.tags().getLoaders().join());
    }

    /**
     * This method tests getting all the game versions.
     */
    @Test
    public void testGetGameVersions() {
        assertNotNull(client.tags().getGameVersions().join());
    }

    /**
     * This method tests getting all the licenses.
     */
    @Test
    public void testGetLicenses() {
        assertNotNull(client.tags().getLicenses().join());
    }

    /**
     * This method tests getting all the mod types.
     */
    @Test
    public void testGetReportTypes() {
        assertNotNull(client.tags().getReportTypes().join());
    }
}
