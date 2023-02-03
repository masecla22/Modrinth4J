package masecla.modrinth4j.endpoints.teams;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;
import masecla.modrinth4j.endpoints.teams.AddMemberToTeam.AddMemberToTeamRequest;
import masecla.modrinth4j.endpoints.teams.GetTeamsMembers.GetTeamsMembersRequest;
import masecla.modrinth4j.endpoints.teams.ModifyTeamMemberInfo.ModifyTeamMemberInfoRequest;
import masecla.modrinth4j.endpoints.teams.TransferOwnership.TransferOwnershipRequest;
import masecla.modrinth4j.model.team.ModrinthTeamMember;

/**
 * This class is used to represent the endpoints for teams.
 */
@AllArgsConstructor
public class TeamsEndpoints {
    /** The Gson instance to use */
    private Gson gson;
    /** The HTTP client to use */
    private HttpClient client;

    /**
     * This endpoint is used to get the team members of a project.
     * 
     * @param projectId - The ID of the project to get the team members of.
     * @return - A {@link CompletableFuture} that will return the team members of
     *         the project.
     */
    public CompletableFuture<List<ModrinthTeamMember>> getProjectTeamMembers(String projectId) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", projectId);

        return new GetProjectTeamMembers(client, gson).sendRequest(new EmptyRequest(), parameters);
    }

    /**
     * This endpoint is used to get the team members of a team.
     * 
     * @param teamId - The ID of the team to get the members of.
     * @return - A {@link CompletableFuture} that will return the team members of
     *         the
     *         team.
     */
    public CompletableFuture<List<ModrinthTeamMember>> getTeamMembers(String teamId) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", teamId);

        return new GetTeamMembers(client, gson).sendRequest(new EmptyRequest(), parameters);
    }

    /**
     * This endpoint is used to get the team members of multiple teams by their IDs.
     * 
     * @param teamIds - The IDs of the teams to get the members of.
     * @return - A {@link CompletableFuture} that will return the team members of
     *         the
     *         teams.
     * @see #getTeamMembers(String[])
     */
    public CompletableFuture<List<List<ModrinthTeamMember>>> getTeamMembers(List<String> teamIds) {
        return new GetTeamsMembers(client, gson).sendRequest(new GetTeamsMembersRequest(teamIds));
    }

    /**
     * This endpoint is used to get the team members of multiple teams by their IDs.
     * 
     * @param teamIds - The IDs of the teams to get the members of.
     * @return - A {@link CompletableFuture} that will return the team members of
     *         the teams.
     * @see #getTeamMembers(List)
     */
    public CompletableFuture<List<List<ModrinthTeamMember>>> getTeamMembers(String... teamIds) {
        return this.getTeamMembers(Arrays.asList(teamIds));
    }

    /**
     * This endpoint is used to add a member to a team.
     * 
     * @param teamId - The ID of the team
     * @param userId - The ID of the user (NOT the username)
     * @return - A {@link CompletableFuture} that will return an empty response when
     *         completed
     */
    public CompletableFuture<EmptyResponse> addMemberToTeam(String teamId, String userId) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", teamId);

        return new AddMemberToTeam(client, gson).sendRequest(new AddMemberToTeamRequest(userId), parameters);
    }

    /**
     * This endpoint is used to join a team.
     * 
     * @param teamId - The ID of the team
     * @return - A {@link CompletableFuture} that will return an empty response when
     *         completed
     */
    public CompletableFuture<EmptyResponse> joinTeam(String teamId) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", teamId);

        return new JoinTeam(client, gson).sendRequest(new EmptyRequest(), parameters);
    }

    /**
     * This endpoint is used to modify a team member's information.
     * 
     * @param teamId   - The ID of the team
     * @param memberId - The ID of the member (NOT the username)
     * @param request  - The request to send
     * @return - A {@link CompletableFuture} that will return an empty response when
     *         completed
     */
    public CompletableFuture<EmptyResponse> modifyTeamMemberInfo(String teamId, String memberId,
            ModifyTeamMemberInfoRequest request) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", teamId);
        parameters.put("user", memberId);

        return new ModifyTeamMemberInfo(client, gson).sendRequest(request, parameters);
    }

    /**
     * This endpoint is used to remove a member from a team.
     * 
     * @param teamId   - The ID of the team
     * @param memberId - The ID of the member (NOT the username)
     * @return - A {@link CompletableFuture} that will return an empty response when
     *         completed
     */
    public CompletableFuture<EmptyResponse> removeMemberFromTeam(String teamId, String memberId) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", teamId);
        parameters.put("user", memberId);

        return new RemoveMemberFromTeam(client, gson).sendRequest(new EmptyRequest(), parameters);
    }

    /**
     * This endpoint is used to transfer ownership of a team to another user.
     * 
     * @param teamId     - The ID of the team
     * @param newOwnerId - The ID of the new owner (NOT the username)
     * @return - A {@link CompletableFuture} that will return an empty response when
     *         completed
     */
    public CompletableFuture<EmptyResponse> transferOwnership(String teamId,
            String newOwnerId) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", teamId);

        return new TransferOwnership(client, gson).sendRequest(new TransferOwnershipRequest(newOwnerId), parameters);
    }
}
