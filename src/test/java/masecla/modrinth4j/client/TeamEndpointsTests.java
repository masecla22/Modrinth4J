package masecla.modrinth4j.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.concurrent.CompletionException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import masecla.modrinth4j.data.DataUtil;
import masecla.modrinth4j.endpoints.teams.ModifyTeamMemberInfo.ModifyTeamMemberInfoRequest;
import masecla.modrinth4j.environment.EnvReader;
import masecla.modrinth4j.exception.EndpointException;
import masecla.modrinth4j.main.ModrinthAPI;
import masecla.modrinth4j.model.project.Project;
import masecla.modrinth4j.model.team.ModrinthTeamMember;
import masecla.modrinth4j.model.user.ModrinthUser;

/**
 * Tests the {@link TeamEndpoints} class.
 */
public class TeamEndpointsTests {
    /** The client to be used */
    private ModrinthAPI client;
    /** The sample project to be used */
    private Project sampleProject;

    /**
     * Sets up the client.
     */
    @BeforeEach
    public void setupClient() {
        EnvReader env = new EnvReader();
        this.client = ModrinthAPI.rateLimited(env.getAgent(), env.getStagingUrl(), env.getApiKey());

        sampleProject = DataUtil.createSampleProject(client);
    }

    /**
     * Wipes the project.
     */
    @AfterEach
    public void wipeProject() {
        DataUtil.deleteSampleProject(client);
    }

    /**
     * This method tests getting a project's team members.
     */
    @Test
    public void testGetProjectTeamMembers() {
        List<ModrinthTeamMember> mems = client.teams().getProjectTeamMembers(sampleProject.getId()).join();

        assertEquals(1, mems.size(), "Members should be 1");
        assertEquals("Owner", mems.get(0).getRole(), "Role should be Owner");
    }

    /**
     * This method tests getting team members from the team ID.
     */
    @Test
    public void testGetTeamMembers() {
        List<ModrinthTeamMember> mems = client.teams().getTeamMembers(sampleProject.getTeam()).join();

        assertEquals(1, mems.size(), "Members should be 1");
        assertEquals("Owner", mems.get(0).getRole(), "Role should be Owner");
    }

    /**
     * This method tests getting multiple teams from multiple projects.
     */
    @Test
    public void testGetTeamsMembers() {
        List<List<ModrinthTeamMember>> mems = client.teams().getTeamMembers(sampleProject.getTeam(), "pnm2l6xn").join();

        assertEquals(2, mems.size(), "There should 2 teams!");
        assertEquals("Owner", mems.get(0).get(0).getRole(), "Role should be Owner");
        assertEquals("Owner", mems.get(1).get(0).getRole(), "Role should be Owner");
        assertEquals(1, mems.get(0).size(), "Members should be 1");
        assertEquals(1, mems.get(1).size(), "Members should be 1");
    }

    /**
     * This method tests adding a member to a team.
     */
    @Test
    public void testAddMemberToTeam() {
        client.teams().addMemberToTeam(sampleProject.getTeam(),
                "thisusershouldneverexistandifitdoespleasegetalifeyoumadeanaccounttomakeaunittestfail");
        List<ModrinthTeamMember> mems = client.teams().getTeamMembers(sampleProject.getTeam()).join();

        // Since it's a non-existent user, it should fail
        assertEquals(1, mems.size(), "Members should be 1");
    }

    /**
     * This method tests joining a team.
     */
    @Test
    public void testJoinTeam() {
        EndpointException e = null;
        try {
            client.teams().joinTeam("pnm2l6xn").join();
        } catch (CompletionException ex) {
            e = (EndpointException) ex.getCause();
        }

        assertNotNull(e, "Exception should not be null");
        assertEquals("invalid_input", e.getError(), "Error should be invalid_input");
    }

    /**
     * This method tests modifying permissions of a team member.
     */
    @Test
    public void testModifyTeamMemberInfo() {
        EndpointException e = null;

        try {
            client.teams().modifyTeamMemberInfo("pnm2l6xn", "lhMEt1C8", ModifyTeamMemberInfoRequest.builder()
                    .build()).join();
        } catch (CompletionException ex) {
            e = (EndpointException) ex.getCause();
        }

        assertNotNull(e);
        assertEquals("unauthorized", e.getError());
    }

    /**
     * This method tests removing a member from a team.
     */
    @Test
    public void testRemoveMemberFromTeam() {
        EndpointException e = null;

        try {
            client.teams().removeMemberFromTeam("pnm2l6xn", "lhMEt1C8").join();
        } catch (CompletionException ex) {
            e = (EndpointException) ex.getCause();
        }

        assertNotNull(e);
        assertEquals("unauthorized", e.getError());
    }

    /**
     * This method tests transferring ownership of a team.
     */
    @Test
    public void testTransferTeam() {
        EndpointException e = null;

        try {
            ModrinthUser self = client.users().getSelf().join();
            client.teams().transferOwnership("pnm2l6xn", self.getId()).join();
        } catch (CompletionException ex) {
            e = (EndpointException) ex.getCause();
        }

        assertNotNull(e, "Exception should not be null");
        assertEquals("unauthorized", e.getError(), "Error should be unauthorized");
    }

}
