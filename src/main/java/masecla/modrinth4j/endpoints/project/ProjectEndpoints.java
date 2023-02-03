package masecla.modrinth4j.endpoints.project;

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
import masecla.modrinth4j.endpoints.project.ChangeProjectIcon.ChangeProjectIconRequest;
import masecla.modrinth4j.endpoints.project.CreateProject.CreateProjectRequest;
import masecla.modrinth4j.endpoints.project.GetMultipleProjects.GetMultipleProjectsRequest;
import masecla.modrinth4j.endpoints.project.GetProjectDependencies.GetProjectDependenciesResponse;
import masecla.modrinth4j.endpoints.project.ModifyProject.ProjectModifications;
import masecla.modrinth4j.endpoints.project.follow.FollowProject;
import masecla.modrinth4j.endpoints.project.follow.UnfollowProject;
import masecla.modrinth4j.endpoints.project.gallery.CreateGalleryImage;
import masecla.modrinth4j.endpoints.project.gallery.CreateGalleryImage.CreateGalleryImageRequest;
import masecla.modrinth4j.endpoints.project.gallery.DeleteGalleryImage;
import masecla.modrinth4j.endpoints.project.gallery.DeleteGalleryImage.DeleteGalleryImageRequest;
import masecla.modrinth4j.endpoints.project.gallery.ModifyGalleryImage;
import masecla.modrinth4j.endpoints.project.gallery.ModifyGalleryImage.ModifyGalleryImageRequest;
import masecla.modrinth4j.model.project.Project;

/**
 * This class is used to represent the project endpoints.
 */
@AllArgsConstructor
public class ProjectEndpoints {
    /** The gson instance to use. */
    private Gson gson;
    /** The client to use. */
    private HttpClient client;

    /**
     * This endpoint is used to fetch a project by its ID.
     * 
     * @param - project The ID of the project to fetch.
     * @return A {@link CompletableFuture} that will return the project.
     */
    public CompletableFuture<Project> get(String project) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", project);
        return new GetProject(client, gson).sendRequest(new EmptyRequest(), parameters);
    }

    /**
     * This endpoint is used to fetch a list of projects by their IDs.
     * 
     * @param - projects The IDs of the projects to fetch.
     * @return A {@link CompletableFuture} that will return the projects.
     * @see #get(String[])
     */
    public CompletableFuture<List<Project>> get(List<String> projects) {
        return new GetMultipleProjects(client, gson)
                .sendRequest(new GetMultipleProjectsRequest(projects));
    }

    /**
     * This endpoint is used to fetch a list of projects by their IDs.
     * 
     * @param projects - The IDs of the projects to fetch.
     * @return - A {@link CompletableFuture} that will return the projects.
     * @see #get(List)
     */
    public CompletableFuture<List<Project>> get(String... projects) {
        return this.get(Arrays.asList(projects));
    }

    /**
     * This endpoint is used to modify a project by its ID.
     * 
     * @param project - The ID of the project to modify.
     * @param request - The request to send.
     * 
     * @return A {@link CompletableFuture} that will return the modified project.
     */
    public CompletableFuture<ProjectModifications> modify(String project, ProjectModifications request) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", project);
        return new ModifyProject(client, gson).sendRequest(request, parameters);
    }

    /**
     * This endpoint is used to delete a project by its ID.
     * 
     * @param project - The ID of the project to delete.
     * @return A {@link CompletableFuture} that will return an empty response.
     */
    public CompletableFuture<EmptyResponse> delete(String project) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", project);
        return new DeleteProject(client, gson).sendRequest(new EmptyRequest(), parameters);
    }

    /**
     * This endpoint will create a project based on the request.
     * 
     * @param request - The request to send.
     * @return A {@link CompletableFuture} that will return the created project.
     */
    public CompletableFuture<Project> create(CreateProjectRequest request) {
        return new CreateProject(client, gson).sendRequest(request);
    }

    /**
     * This endpoint is used to check slug availability.
     * 
     * @param slug - The slug to check.
     * @return A {@link CompletableFuture} that will return true if the slug is available.
     */
    public CompletableFuture<Boolean> checkSlugAvailability(String slug) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", slug);
        return new CheckSlugAvailability(client, gson).sendRequest(new EmptyRequest(), parameters)
                .thenApply(response -> {
                    if (response == null)
                        return true;
                    return false;
                });
    }

    /**
     * This endpoint is used to get the project ID from a slug
     * 
     * @param slug - The slug to get the ID from.
     * @return A {@link CompletableFuture} that will return the project ID.
     */
    public CompletableFuture<String> getProjectIdBySlug(String slug) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", slug);
        return new GetProjectIdBySlug(client, gson).sendRequest(new EmptyRequest(), parameters).thenApply(c -> {
            if (c == null)
                return null;
            return c.getId();
        });
    }

    /**
     * This endpoint is used to create a gallery image for a project.
     * 
     * @param slug    - The slug of the project to create the image for.
     * @param request - The request to send.
     * 
     * @return A {@link CompletableFuture} that will return an empty response.
     */
    public CompletableFuture<EmptyResponse> createGalleryImage(String slug, CreateGalleryImageRequest request) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", slug);
        return new CreateGalleryImage(client, gson).sendRequest(request, parameters);
    }

    /**
     * This endpoint is used to modify a gallery image for a project.
     * 
     * @param slug    - The slug of the project to modify the image for.
     * @param request - The request to send.
     * 
     * @return A {@link CompletableFuture} that will return an empty response.
     */
    public CompletableFuture<EmptyResponse> modifyGalleryImage(String slug, ModifyGalleryImageRequest request) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", slug);
        return new ModifyGalleryImage(client, gson).sendRequest(request, parameters);
    }

    /**
     * This endpoint is used to delete a gallery image for a project.
     * 
     * @param slug     - The slug of the project to delete the image for.
     * @param imageUrl - The URL of the image to delete.
     * 
     * @return A {@link CompletableFuture} that will return an empty response.
     */
    public CompletableFuture<EmptyResponse> deleteGalleryImage(String slug, String imageUrl) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", slug);
        return new DeleteGalleryImage(client, gson).sendRequest(new DeleteGalleryImageRequest(imageUrl), parameters);
    }

    /**
     * This endpoint is used to get the dependencies for a project.
     * 
     * @param slug - The slug of the project to get the dependencies for.
     * @return A {@link CompletableFuture} that will return the dependencies.
     */
    public CompletableFuture<GetProjectDependenciesResponse> getProjectDependencies(String slug) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", slug);
        return new GetProjectDependencies(client, gson).sendRequest(new EmptyRequest(), parameters);
    }

    /**
     * This endpoint is used to follow a project.
     * 
     * @param slug - The slug of the project to follow.
     * @return A {@link CompletableFuture} that will return an empty response.
     */
    public CompletableFuture<EmptyResponse> followProject(String slug) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", slug);
        return new FollowProject(client, gson).sendRequest(new EmptyRequest(), parameters);
    }

    /**
     * This endpoint is used to unfollow a project.
     * 
     * @param slug - The slug of the project to unfollow.
     * @return A {@link CompletableFuture} that will return an empty response.
     */
    public CompletableFuture<EmptyResponse> unfollowProject(String slug) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", slug);
        return new UnfollowProject(client, gson).sendRequest(new EmptyRequest(), parameters);
    }

    /**
     * This endpoint is used to change the icon of a project.
     * 
     * @param projectId - The ID of the project to change the icon for.
     * @param file - The file to use as the icon.
     * @return - A {@link CompletableFuture} that will return an empty response.
     * @throws FileNotFoundException - If the file is not found.
     * @see #changeProjectIcon(String, InputStream, String)
     */
    public CompletableFuture<EmptyResponse> changeProjectIcon(String projectId, File file)
            throws FileNotFoundException {
        return changeProjectIcon(projectId, new FileInputStream(file), file.getName());
    }

    /**
     * This endpoint is used to change the icon of a project.
     * 
     * @param projectId - The ID of the project to change the icon for.
     * @param stream - The stream to use as the icon.
     * @param fileName - The name of the file.
     * @return - A {@link CompletableFuture} that will return an empty response.
     * @see #changeProjectIcon(String, File)
     */
    public CompletableFuture<EmptyResponse> changeProjectIcon(String projectId, InputStream stream, String fileName) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", projectId);

        return new ChangeProjectIcon(client, gson).sendRequest(new ChangeProjectIconRequest(stream, fileName),
                parameters);
    }
}
