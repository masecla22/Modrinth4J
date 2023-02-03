package masecla.modrinth4j.client;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

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
    @Before
    public void setupClient() {
        EnvReader env = new EnvReader();
        this.client = ModrinthAPI.rateLimited(env.getAgent(), env.getStagingUrl(), env.getApiKey());

    }

    /**
     * This method tests getting all the categories.
     */
    @Test
    public void testGetCategories() {
        assertTrue(client.tags().getCategories().join() != null);
    }

    /**
     * This method tests getting all the donation platforms.
     */
    @Test
    public void testGetDonationPlatforms() {
        assertTrue(client.tags().getDonationPlatforms().join() != null);
    }

    /**
     * This method tests getting all the loaders.
     */
    @Test
    public void testGetLoaders() {
        assertTrue(client.tags().getLoaders().join() != null);
    }

    /**
     * This method tests getting all the game versions.
     */
    @Test
    public void testGetGameVersions() {
        assertTrue(client.tags().getGameVersions().join() != null);
    }

    /**
     * This method tests getting all the licenses.
     */
    @Test
    public void testGetLicenses() {
        assertTrue(client.tags().getLicenses().join() != null);
    }

    /**
     * This method tests getting all the mod types.
     */
    @Test
    public void testGetReportTypes() {
        assertTrue(client.tags().getReportTypes().join() != null);
    }
}
