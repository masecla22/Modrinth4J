package masecla.modrinth4j.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.UUID;
import java.util.concurrent.CompletionException;

import org.junit.Before;
import org.junit.Test;

import lombok.SneakyThrows;
import masecla.modrinth4j.data.DataUtil;
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

public class UserEndpointsTests {
    private ModrinthAPI client;

    @Before
    public void setupClient() {
        EnvReader env = new EnvReader();
        this.client = ModrinthAPI.unlimited(env.getAgent(), env.getStagingUrl(), env.getApiKey());
    }

    @Test
    public void testGetUser() {
        ModrinthUser user = client.users().getUser("Geometrically").join();
        assertTrue(user != null);
    }

    @Test
    public void testGetUsers() {
        assertTrue(client.users().getUser("5XoMa0C4", "g97WV39V").join().length == 2);
    }

    @Test
    public void testModifyUser() {
        ModrinthUser self = client.users().getSelf().join();
        UUID random = UUID.randomUUID();
        client.users().modifyUser(self.getId(), ModifyUserRequest.builder()
                .bio(random.toString()).build()).join();

        assertTrue(client.users().getSelf().join().getBio().equals(random.toString()));
    }

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

    @Test
    public void testGetSelf() {
        ModrinthUser self = client.users().getSelf().join();
        assertTrue(self != null);
    }

    @Test
    public void testGetUserProjects() {
        DataUtil.createSampleProject(client);
        ModrinthUser self = client.users().getSelf().join();
        assertTrue(client.users().getUserProjects(self.getId()).join().length > 0);
        DataUtil.deleteSampleProject(client);
    }

    @Test
    public void testGetNotifications() {
        ModrinthUser self = client.users().getSelf().join();
        assertTrue(client.users().getNotifications(self.getId()).join() != null);
    }

    @Test
    @SneakyThrows
    public void testChangeProfilePicture() {
        ModrinthUser self = client.users().getSelf().join();
        client.users().changeProfilePicture(self.getId(), DataUtil.getImage()).join();

        self = client.users().getSelf().join();
        assertTrue(self.getAvatarUrl() != null);
    }

    @Test
    public void testGetUserFollowedProjects() {
        ModrinthUser self = client.users().getSelf().join();

        Project prj = DataUtil.createSampleProject(client);
        client.projects().followProject(prj.getId()).join();

        assertTrue(client.users().getUserFollowedProjects(self.getId()).join().length > 0);
        DataUtil.deleteSampleProject(client);
    }

    @Test
    public void testReportProjectUserOrVersion() {
        Project prj = DataUtil.createSampleProject(client);
        ReportProjectUserOrVersionResponse response = client.users().reportProjectUserOrVersion(
                ReportProjectUserOrVersionRequest.builder()
                        .reportType(ReportType.SPAM)
                        .body("please ignore this report. this is unit testing done for Modrinth4J. If you want this test removed, contact me on discord masecl22#4309")
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
