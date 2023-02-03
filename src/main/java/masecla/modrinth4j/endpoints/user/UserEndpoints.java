package masecla.modrinth4j.endpoints.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
import masecla.modrinth4j.endpoints.user.ChangeUserIcon.ChangeUserIconRequest;
import masecla.modrinth4j.endpoints.user.GetUsers.GetUsersRequest;
import masecla.modrinth4j.endpoints.user.ModifyUser.ModifyUserRequest;
import masecla.modrinth4j.endpoints.user.ReportProjectUserOrVersion.ReportProjectUserOrVersionRequest;
import masecla.modrinth4j.endpoints.user.ReportProjectUserOrVersion.ReportProjectUserOrVersionResponse;
import masecla.modrinth4j.model.project.Project;
import masecla.modrinth4j.model.user.ModrinthUser;
import masecla.modrinth4j.model.user.ModrinthUserNotification;

/**
 * This class is used to represent the endpoints for users.
 */
@AllArgsConstructor
public class UserEndpoints {
    /** The Gson instance to use */
    private Gson gson;
    /** The HTTP client to use */
    private HttpClient client;

    /**
     * Gets a user by their ID or username
     * 
     * @param id - The ID or username of the user
     * @return - The user
     */
    public CompletableFuture<ModrinthUser> getUser(String id) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", id);

        return new GetUser(client, gson).sendRequest(new EmptyRequest(), parameters);
    }

    /**
     * Gets multiple users by their IDs. <b> Note: This will only work with IDs, not
     * usernames. </b> unlike {@link #getUser(String)}
     * 
     * @param ids - The IDs of the users
     * @return - The users
     */
    public CompletableFuture<List<ModrinthUser>> getUser(String... ids) {
        return this.getUser(Arrays.asList(ids));
    }

    /**
     * Gets multiple users by their IDs. <b> Note: This will only work with IDs, not
     * usernames. </b> unlike {@link #getUser(String)}
     * 
     * @param ids - The IDs of the users
     * @return - The users
     */
    public CompletableFuture<List<ModrinthUser>> getUser(List<String> ids) {
        return new GetUsers(client, gson).sendRequest(new GetUsersRequest(ids));
    };

    /**
     * Modifies a user by their ID
     * 
     * @param id      - The ID of the user
     * @param request - The request to send
     * @return - A {@link CompletableFuture} that will return an
     *         {@link EmptyResponse} when complete
     */
    public CompletableFuture<EmptyResponse> modifyUser(String id, ModifyUserRequest request) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", id);

        return new ModifyUser(client, gson).sendRequest(request, parameters);
    }

    /**
     * Deletes a user by their ID
     * 
     * @param id - The ID of the user
     * @return - A {@link CompletableFuture} that will return an
     *         {@link EmptyResponse} when complete
     */
    public CompletableFuture<EmptyResponse> deleteUser(String id) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", id);

        return new DeleteUser(client, gson).sendRequest(new EmptyRequest(), parameters);
    }

    /**
     * Gets the current user from the API
     * 
     * @return - The current user
     */
    public CompletableFuture<ModrinthUser> getSelf() {
        return new GetSelf(client, gson).sendRequest(new EmptyRequest(), new HashMap<>());
    }

    /**
     * Modifies the profile picture of a user by their ID
     * 
     * @param id   - The ID of the user
     * @param file - The file to use as the profile picture
     * @return - A {@link CompletableFuture} that will return an
     *         {@link EmptyResponse} when complete
     * @throws FileNotFoundException - If the file cannot be found
     */
    public CompletableFuture<EmptyResponse> changeProfilePicture(String id, File file) throws FileNotFoundException {
        return changeProfilePicture(id, new FileInputStream(file), file.getName());
    }

    /**
     * Modifies the profile picture of a user by their ID
     * 
     * @param id       - The ID of the user
     * @param stream   - The stream to use as the profile picture
     * @param filename - The filename of the profile picture
     * @return - A {@link CompletableFuture} that will return an
     *         {@link EmptyResponse} when complete
     */
    public CompletableFuture<EmptyResponse> changeProfilePicture(String id, InputStream stream, String filename) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", id);

        return new ChangeUserIcon(client, gson).sendRequest(new ChangeUserIconRequest(filename, stream), parameters);
    }

    /**
     * Gets the projects of a user by their ID
     * 
     * @param id - The ID of the user
     * @return - A {@link CompletableFuture} that will return a {@link List} of
     *         {@link Project}s when complete
     */
    public CompletableFuture<List<Project>> getUserProjects(String id) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", id);

        return new GetUserProjects(client, gson).sendRequest(new EmptyRequest(), parameters);
    }

    /**
     * Gets the followed projects of a user by their ID
     * 
     * @param id - The ID of the user
     * @return - A {@link CompletableFuture} that will return a {@link List} of
     *         {@link Project}s when complete
     */
    public CompletableFuture<List<Project>> getUserFollowedProjects(String id) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", id);

        return new GetUserFollowedProjects(client, gson).sendRequest(new EmptyRequest(), parameters);
    }

    /**
     * Gets the notifications of a user by their ID
     * 
     * @param id - The ID of the user
     * @return - A {@link CompletableFuture} that will return a {@link List} of
     *         {@link ModrinthUserNotification}s when complete
     */
    public CompletableFuture<List<ModrinthUserNotification>> getNotifications(String id) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", id);

        return new GetUserNotifications(client, gson).sendRequest(new EmptyRequest(), parameters);
    }

    /**
     * Reports a project, user, or version
     * 
     * @param request - The request to send
     * @return - A {@link CompletableFuture} that will return a
     *         {@link ReportProjectUserOrVersionResponse} when complete
     */
    public CompletableFuture<ReportProjectUserOrVersionResponse> reportProjectUserOrVersion(
            ReportProjectUserOrVersionRequest request) {
        return new ReportProjectUserOrVersion(client, gson).sendRequest(request);
    }
}
