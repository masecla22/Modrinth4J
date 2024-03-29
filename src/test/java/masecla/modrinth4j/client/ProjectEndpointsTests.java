package masecla.modrinth4j.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import lombok.SneakyThrows;
import masecla.modrinth4j.data.DataUtil;
import masecla.modrinth4j.endpoints.SearchEndpoint.SearchRequest;
import masecla.modrinth4j.endpoints.SearchEndpoint.SearchResponse;
import masecla.modrinth4j.endpoints.project.ModifyProject.ProjectModifications;
import masecla.modrinth4j.endpoints.project.ProjectEndpoints;
import masecla.modrinth4j.endpoints.project.gallery.CreateGalleryImage.CreateGalleryImageRequest;
import masecla.modrinth4j.endpoints.project.gallery.ModifyGalleryImage.ModifyGalleryImageRequest;
import masecla.modrinth4j.environment.EnvReader;
import masecla.modrinth4j.main.ModrinthAPI;
import masecla.modrinth4j.model.project.Project;
import masecla.modrinth4j.model.project.ProjectDonationPlatform;
import masecla.modrinth4j.model.project.ProjectGalleryImage;
import masecla.modrinth4j.model.project.SupportStatus;

/**
 * Tests the {@link ProjectEndpoints} class.
 */
public class ProjectEndpointsTests {
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
     * This method tests the search endpoint.
     */
    @Test
    public void testSearch() {
        // We're looking for the Gravestones mod
        SearchResponse response = client.search(SearchRequest.builder().query("fabric").build()).join();

        assertTrue("The search response did not contain the mod 'Gravestones'!",
                Arrays.stream(response.getHits()).anyMatch(c -> c.getSlug().equals("gravestones")));
    }

    /**
     * This method tests changing the icon of a project.
     */
    @Test
    @SneakyThrows
    public void testIconChange() {
        Project prj = DataUtil.createSampleProject(client);

        String iconPath = getClass().getClassLoader().getResource("icon.png").getFile();
        client.projects().changeProjectIcon(prj.getSlug(), new File(iconPath)).join();

        DataUtil.deleteSampleProject(client);
    }

    /**
     * This method tests checking for slug availability.
     */
    @Test
    public void testSlugAvailability() {
        assertTrue("The slug 'modrinth4j-test-project' is not available!",
                client.projects().checkSlugAvailability("modrinth4j-test-project").join());
        assertFalse("The slug 'gravestones' is available!",
                client.projects().checkSlugAvailability("gravestones").join());
    }

    /**
     * This method tests converting a slug to an ID.
     */
    @Test
    public void testGetIdBySlug() {
        Project prj = DataUtil.createSampleProject(client);
        Project fetched = client.projects().get(prj.getSlug()).join();

        assertEquals(fetched.getId(),
                client.projects().getProjectIdBySlug(fetched.getSlug()).join());

        DataUtil.deleteSampleProject(client);

    }

    /**
     * This method tests creating a project.
     */
    @Test
    public void testCreate() {
        Project prj = DataUtil.createSampleProject(client);
        assertTrue("The project was not created!", prj != null);
        DataUtil.deleteSampleProject(client);
    }

    /**
     * This method tests creating gallery images.
     */
    @Test
    public void testGalleryCreate() {
        Project prj = DataUtil.createSampleProject(client);
        client.projects().createGalleryImage(prj.getSlug(),
                CreateGalleryImageRequest.builder().featured(true)
                        .title("Test Image").description("This is a test image")
                        .file(DataUtil.getImage()).build())
                .join();

        prj = DataUtil.fetchSampleProject(client);

        assertTrue("The project did not have any gallery images!", prj.getGallery().size() > 0);
        ProjectGalleryImage img = prj.getGallery().get(0);

        assertTrue("The image was not featured!", img.isFeatured());
        assertTrue("The image did not have the correct title!", img.getTitle().equals("Test Image"));
        assertTrue("The image did not have the correct description!",
                img.getDescription().equals("This is a test image"));
        assertTrue("The image is not the one provided",
                DataUtil.verifyIdentical(img.getUrl(), DataUtil.getImage()));

        DataUtil.deleteSampleProject(client);
    }

    /**
     * This method tests deleting a gallery image.
     */
    @Test
    public void testDeleteGalleryImage() {
        Project prj = DataUtil.createSampleProject(client);
        client.projects().createGalleryImage(prj.getSlug(),
                CreateGalleryImageRequest.builder().featured(true)
                        .title("Test Image").description("This is a test image")
                        .file(DataUtil.getImage()).build())
                .join();
        prj = DataUtil.fetchSampleProject(client);

        client.projects().deleteGalleryImage(prj.getSlug(), prj.getGallery().get(0).getUrl()).join();

        prj = DataUtil.fetchSampleProject(client);
        assertTrue("The project still has gallery images!", prj.getGallery().size() == 0);

        DataUtil.deleteSampleProject(client);
    }

    /**
     * This method tests modifying a gallery image.
     */
    @Test
    public void testGalleryModify() {
        Project prj = DataUtil.createSampleProject(client);
        client.projects().createGalleryImage(prj.getSlug(),
                CreateGalleryImageRequest.builder().featured(true)
                        .title("Test Image").description("This is a test image")
                        .file(DataUtil.getImage()).build())
                .join();
        prj = DataUtil.fetchSampleProject(client);

        client.projects().modifyGalleryImage(prj.getSlug(),
                ModifyGalleryImageRequest.builder().featured(false)
                        .title("Test Image 2").description("This is a test image 2")
                        .url(prj.getGallery().get(0).getUrl()).build())
                .join();

        prj = DataUtil.fetchSampleProject(client);
        ProjectGalleryImage img = prj.getGallery().get(0);

        assertFalse("The image was still featured!", img.isFeatured());
        assertTrue("The image did not have the correct title!", img.getTitle().equals("Test Image 2"));
        assertTrue("The image did not have the correct description!",
                img.getDescription().equals("This is a test image 2"));
        assertTrue("The image is not the one provided",
                DataUtil.verifyIdentical(img.getUrl(), DataUtil.getImage()));

        DataUtil.deleteSampleProject(client);
    }

    /**
     * This method tests deleting a project.
     */
    @Test
    public void testProjectDelete() {
        Project prj = DataUtil.createSampleProject(client);
        client.projects().delete(prj.getSlug()).join();
        assertTrue("The project was not deleted!", client.projects().get(prj.getSlug()).join() == null);
    }

    /**
     * This method tests fetching a project.
     */
    @Test
    public void testGetSingle() {
        Project prj = DataUtil.createSampleProject(client);
        Project fetched = client.projects().get(prj.getSlug()).join();
        assertTrue("The project was not fetched!", fetched != null);
        DataUtil.deleteSampleProject(client);
    }

    /**
     * This method tests fetching multiple projects.
     */
    @Test
    public void testGetMultiple() {
        Project prj = DataUtil.createSampleProject(client);
        List<Project> fetched = client.projects().get(Arrays.asList(prj.getId(), "AULzIar5")).join();
        assertEquals("The project was not fetched!", 2, fetched.size());
        DataUtil.deleteSampleProject(client);
    }

    /**
     * This method tests following and unfollowing projects.
     */
    @Test
    public void testFollowUnfollow() {
        String id = "AULzIar5";
        client.projects().followProject(id).join();
        List<Project> follows = client.users().getUserFollowedProjects(client.users().getSelf().join().getId())
                .join();

        assertTrue("The project was not followed!", follows.stream().anyMatch(c -> c.getId().equals(id)));
        client.projects().unfollowProject(id).join();

        follows = client.users().getUserFollowedProjects(client.users().getSelf().join().getId()).join();
        assertFalse("The project was not unfollowed!", follows.stream().anyMatch(c -> c.getId().equals(id)));
    }

    /**
     * This method tests fetching a project's dependencies.
     */
    @Test
    public void testProjectDependencies() {
        assertTrue("The project 'gravestones' has dependencies?",
                client.projects().getProjectDependencies("gravestones").join().getProjects()
                        .size() == 0);
    }

    /**
     * This method tests modifying a project.
     */
    @Test
    public void testModifyProject() {
        Project prj = DataUtil.createSampleProject(client);

        client.projects().modify(prj.getId(), ProjectModifications.builder()
                .additionalCategories(Arrays.asList("cursed"))
                .body("Different body")
                .categories(Arrays.asList("adventure"))
                .clientSide(SupportStatus.UNSUPPORTED)
                .description("Different description")
                .discordUrl("https://discord.gg/1234")
                .donationUrls(Arrays.asList(
                        ProjectDonationPlatform.builder().id("other")
                                .url("https://example.com/donate").platform("other")
                                .build()))
                .issuesUrl("https://example.com/issues")
                .serverSide(SupportStatus.UNSUPPORTED)
                .slug("diff-slug-too")
                .sourceUrl("https://example.com/source")
                .title("Different title")
                .wikiUrl("https://example.com/wiki")
                .build()).join();

        prj = client.projects().get("diff-slug-too").join();

        assertTrue("The project did not have the correct additional categories!",
                prj.getAdditionalCategories().stream().anyMatch(c -> c.equals("cursed")));
        assertTrue("The project did not have the correct body!", prj.getBody().equals("Different body"));
        assertTrue("The project did not have the correct categories!",
                prj.getCategories().stream().anyMatch(c -> c.equals("adventure")));
        assertTrue("The project did not have the correct client side support status!",
                prj.getClientSide() == SupportStatus.UNSUPPORTED);
        assertTrue("The project did not have the correct description!",
                prj.getDescription().equals("Different description"));
        assertTrue("The project did not have the correct discord url!",
                prj.getDiscordUrl().equals("https://discord.gg/1234"));
        assertTrue("The project did not have the correct donation urls!",
                prj.getDonationUrls().stream()
                        .anyMatch(c -> c.getUrl().equals("https://example.com/donate")));
        assertTrue("The project did not have the correct issues url!",
                prj.getIssuesUrl().equals("https://example.com/issues"));
        assertTrue("The project did not have the correct server side support status!",
                prj.getServerSide() == SupportStatus.UNSUPPORTED);
        assertTrue("The project did not have the correct slug!", prj.getSlug().equals("diff-slug-too"));
        assertTrue("The project did not have the correct source url!",
                prj.getSourceUrl().equals("https://example.com/source"));
        assertTrue("The project did not have the correct title!", prj.getTitle().equals("Different title"));
        assertTrue("The project did not have the correct wiki url!",
                prj.getWikiUrl().equals("https://example.com/wiki"));

        client.projects().delete("diff-slug-too").join();
    }

    @Test
    public void testModifyProjectIdenticalSlug() {
        Project prj = DataUtil.createSampleProject(client);

        client.projects().modify(prj.getId(), ProjectModifications.builder()
                .slug(prj.getSlug())
                .build()).join();

        prj = client.projects().get(prj.getSlug()).join();

        assertTrue("The project did not have the correct slug!", prj.getSlug().equals("modrinth4j-test-project"));

        client.projects().delete("modrinth4j-test-project").join();
    }
}
