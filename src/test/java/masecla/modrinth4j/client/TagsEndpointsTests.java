package masecla.modrinth4j.client;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import masecla.modrinth4j.environment.EnvReader;
import masecla.modrinth4j.main.ModrinthAPI;

public class TagsEndpointsTests {
    private ModrinthAPI client;

    @Before
    public void setupClient() {
        EnvReader env = new EnvReader();
        this.client = ModrinthAPI.rateLimited(env.getAgent(), env.getStagingUrl(), env.getApiKey());

    }

    @Test
    public void testGetCategories() {
        assertTrue(client.tags().getCategories().join() != null);
    }

    @Test
    public void testGetDonationPlatforms() {
        assertTrue(client.tags().getDonationPlatforms().join() != null);
    }

    @Test
    public void testGetLoaders() {
        assertTrue(client.tags().getLoaders().join() != null);
    }

    @Test
    public void testGetGameVersions() {
        assertTrue(client.tags().getGameVersions().join() != null);
    }

    @Test
    public void testGetLicenses() {
        assertTrue(client.tags().getLicenses().join() != null);
    }

    @Test
    public void testGetReportTypes() {
        assertTrue(client.tags().getReportTypes().join() != null);
    }
}
