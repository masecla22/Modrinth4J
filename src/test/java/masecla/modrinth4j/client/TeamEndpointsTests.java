package masecla.modrinth4j.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.CompletionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import masecla.modrinth4j.data.DataUtil;
import masecla.modrinth4j.endpoints.teams.ModifyTeamMemberInfo.ModifyTeamMemberInfoRequest;
import masecla.modrinth4j.environment.EnvReader;
import masecla.modrinth4j.exception.EndpointException;
import masecla.modrinth4j.main.ModrinthAPI;
import masecla.modrinth4j.model.project.Project;
import masecla.modrinth4j.model.team.ModrinthTeamMember;
import masecla.modrinth4j.model.user.ModrinthUser;

public class TeamEndpointsTests {
    private ModrinthAPI client;
    private Project sampleProject;

    @Before
    public void setupClient() {
        EnvReader env = new EnvReader();
        this.client = ModrinthAPI.unlimited(env.getStagingUrl(), env.getApiKey());

        sampleProject = DataUtil.createSampleProject(client);
    }

    @After
    public void wipeProject() {
        DataUtil.deleteSampleProject(client);
    }

    @Test
    public void testGetProjectTeamMembers() {
        ModrinthTeamMember[] mems = client.teams().getProjectTeamMembers(sampleProject.getId()).join();

        assertTrue(mems.length == 1);
        assertTrue(mems[0].getRole().equals("Owner"));
    }

    @Test
    public void testGetTeamMembers() {
        ModrinthTeamMember[] mems = client.teams().getTeamMembers(sampleProject.getTeam()).join();

        assertTrue(mems.length == 1);
        assertTrue(mems[0].getRole().equals("Owner"));
    }

    @Test
    public void testGetTeamsMembers() {
        ModrinthTeamMember[][] mems = client.teams().getTeamMembers(sampleProject.getTeam(), "pnm2l6xn").join();

        assertTrue(mems.length == 2);
        assertTrue(mems[0].length == 1);
        assertTrue(mems[1].length == 1);
        assertTrue(mems[0][0].getRole().equals("Owner"));
        assertTrue(mems[1][0].getRole().equals("Owner"));
    }

    @Test
    public void testAddMemberToTeam() {
        client.teams().addMemberToTeam(sampleProject.getTeam(),
                "thisusershouldneverexistandifitdoespleasegetalifeyoumadeanaccounttomakeaunittestfail");
        ModrinthTeamMember[] mems = client.teams().getTeamMembers(sampleProject.getTeam()).join();

        // Since it's a non-existent user, it should fail
        assertTrue(mems.length == 1);
    }

    @Test
    public void testJoinTeam() {
        EndpointException e = null;
        try {
            client.teams().joinTeam("pnm2l6xn").join();
        } catch (CompletionException ex) {
            e = (EndpointException) ex.getCause();
        }

        assertTrue(e != null);
        assertTrue(e.getError().equals("invalid_input"));
    }

    @Test
    public void testModifyTeamMemberInfo() {
        EndpointException e = null;

        try {
            client.teams().modifyTeamMemberInfo("pnm2l6xn", "lhMEt1C8", ModifyTeamMemberInfoRequest.builder()
                    .build()).join();
        } catch (CompletionException ex) {
            e = (EndpointException) ex.getCause();
        }

        assertTrue(e != null);
        assertEquals("unauthorized", e.getError());
    }

    @Test
    public void testRemoveMemberFromTeam() {
        EndpointException e = null;

        try {
            client.teams().removeMemberFromTeam("pnm2l6xn", "lhMEt1C8").join();
        } catch (CompletionException ex) {
            e = (EndpointException) ex.getCause();
        }

        assertTrue(e != null);
        assertEquals("unauthorized", e.getError());
    }

    @Test
    public void testTransferTeam() {
        EndpointException e = null;

        try {
            ModrinthUser self = client.users().getSelf().join();
            client.teams().transferOwnership("pnm2l6xn", self.getId()).join();
        } catch (CompletionException ex) {
            e = (EndpointException) ex.getCause();
        }

        assertTrue(e != null);
        assertEquals("unauthorized", e.getError());
    }

}
