package masecla.modrinth4j.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.UUID;
import java.util.concurrent.CompletionException;

import org.junit.Before;
import org.junit.Test;

import lombok.SneakyThrows;
import masecla.modrinth4j.data.DataUtil;
import masecla.modrinth4j.endpoints.user.UserEndpoints;
import masecla.modrinth4j.endpoints.user.ModifyUser.ModifyUserRequest;
import masecla.modrinth4j.endpoints.user.ReportProjectUserOrVersion.ReportProjectUserOrVersionRequest;
import masecla.modrinth4j.endpoints.user.ReportProjectUserOrVersion.ReportProjectUserOrVersionResponse;
import masecla.modrinth4j.endpoints.user.ReportProjectUserOrVersion.ReportType;
import masecla.modrinth4j.endpoints.user.ReportProjectUserOrVersion.ReportedObjectType;
import masecla.modrinth4j.environment.EnvReader;
import masecla.modrinth4j.exception.EndpointException;
import masecla.modrinth4j.main.ModrinthAPI;
import masecla.modrinth4j.model.project.Project;
import masecla.modrinth4j.model.user.ModrinthUser;

/**
 * Tests the {@link UserEndpoints} class.
 */
public class UserEndpointsTests {
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
     * This method tests getting a user.
     */
    @Test
    public void testGetUser() {
        ModrinthUser user = client.users().getUser("Geometrically").join();
        assertTrue(user != null);
    }

    /**
     * This method tests getting multiple users.
     */
    @Test
    public void testGetUsers() {
        assertTrue(client.users().getUser("5XoMa0C4", "g97WV39V").join().size() == 2);
    }

    /**
     * This method tests modifying a user
     */
    @Test
    public void testModifyUser() {
        ModrinthUser self = client.users().getSelf().join();
        UUID random = UUID.randomUUID();
        client.users().modifyUser(self.getId(), ModifyUserRequest.builder()
                .bio(random.toString()).build()).join();

        assertTrue(client.users().getSelf().join().getBio().equals(random.toString()));
    }

    /**
     * This method tests deleting a user.
     */
    @Test
    public void testDeleteUser() {
        EndpointException error = null;
        try {
            client.users().deleteUser("5XoMa0C4").join();
        } catch (CompletionException e) {
            error = (EndpointException) e.getCause();
        }

        assertTrue(error != null);
        assertEquals("unauthorized", error.getError());
    }

    /**
     * This method tests the get self endpoint
     */
    @Test
    public void testGetSelf() {
        ModrinthUser self = client.users().getSelf().join();
        assertTrue(self != null);
    }

    /**
     * This method tests getting a user's projects
     */
    @Test
    public void testGetUserProjects() {
        DataUtil.createSampleProject(client);
        ModrinthUser self = client.users().getSelf().join();
        assertTrue(client.users().getUserProjects(self.getId()).join().size() > 0);
        DataUtil.deleteSampleProject(client);
    }

    /**
     * This method tests getting a user's versions
     */
    @Test
    public void testGetNotifications() {
        ModrinthUser self = client.users().getSelf().join();
        assertTrue(client.users().getNotifications(self.getId()).join() != null);
    }

    /**
     * This method tests changing a user's profile picture
     */
    @Test
    @SneakyThrows
    public void testChangeProfilePicture() {
        ModrinthUser self = client.users().getSelf().join();
        client.users().changeProfilePicture(self.getId(), DataUtil.getImage()).join();

        self = client.users().getSelf().join();
        assertTrue(self.getAvatarUrl() != null);
    }

    /**
     * This method tests getting a user's followed projects
     */
    @Test
    public void testGetUserFollowedProjects() {
        ModrinthUser self = client.users().getSelf().join();

        Project prj = DataUtil.createSampleProject(client);
        client.projects().followProject(prj.getId()).join();

        assertTrue(client.users().getUserFollowedProjects(self.getId()).join().size() > 0);
        DataUtil.deleteSampleProject(client);
    }

    /**
     * This method tests reporting a project, user or version
     */
    @Test
    public void testReportProjectUserOrVersion() {
        Project prj = DataUtil.createSampleProject(client);
        ReportProjectUserOrVersionResponse response = client.users().reportProjectUserOrVersion(
                ReportProjectUserOrVersionRequest.builder()
                        .reportType(ReportType.SPAM)
                        .body("Please ignore this report. This is unit testing done for Modrinth4J. If you want this test removed, contact me on discord masecl22#4309")
                        .itemId(prj.getId())
                        .itemType(ReportedObjectType.PROJECT)
                        .build())
                .join();

        assertTrue(response != null);

        ModrinthUser self = client.users().getSelf().join();
        assertTrue(response.getReporter().equals(self.getId()));

        DataUtil.deleteSampleProject(client);
    }
}
