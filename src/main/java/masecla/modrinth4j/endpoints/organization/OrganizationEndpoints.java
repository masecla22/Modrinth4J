package masecla.modrinth4j.endpoints.organization;

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
import masecla.modrinth4j.model.organization.ModrinthOrganization;
import masecla.modrinth4j.model.project.Project;

/**
 * Endpoints for managing organizations.
 */
@AllArgsConstructor
public class OrganizationEndpoints {
    /** The Gson instance to use */
    private Gson gson;
    /** The HTTP client to use */
    private HttpClient client;

    /**
     * Creates a new organization.
     * 
     * @param request - The request containing organization details
     * @return A {@link CompletableFuture} that will return the created organization
     */
    public CompletableFuture<ModrinthOrganization> create(CreateOrganization.CreateOrganizationRequest request) {
        return new CreateOrganization(client, gson).sendRequest(request);
    }

    /**
     * Gets a single organization by its ID or slug.
     * 
     * @param organizationId - The ID or slug of the organization to fetch
     * @return A {@link CompletableFuture} that will return the organization
     */
    public CompletableFuture<ModrinthOrganization> get(String organizationId) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", organizationId);
        return new GetOrganization(client, gson).sendRequest(new EmptyRequest(), parameters);
    }

    /**
     * Gets multiple organizations by their IDs.
     * 
     * @param organizationIds - The IDs of the organizations to fetch
     * @return A {@link CompletableFuture} that will return the organizations
     */
    public CompletableFuture<List<ModrinthOrganization>> get(List<String> organizationIds) {
        return new GetOrganizations(client, gson)
                .sendRequest(new GetOrganizations.GetOrganizationsRequest(organizationIds));
    }

    /**
     * Gets multiple organizations by their IDs.
     * 
     * @param organizationIds - The IDs of the organizations to fetch
     * @return A {@link CompletableFuture} that will return the organizations
     */
    public CompletableFuture<List<ModrinthOrganization>> get(String... organizationIds) {
        return this.get(Arrays.asList(organizationIds));
    }

    /**
     * Modifies an existing organization.
     * 
     * @param organizationId - The ID of the organization to modify
     * @param request        - The modifications to apply
     * @return A {@link CompletableFuture} that will return an empty response
     */
    public CompletableFuture<EmptyResponse> modify(String organizationId,
            ModifyOrganization.ModifyOrganizationRequest request) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", organizationId);
        return new ModifyOrganization(client, gson).sendRequest(request, parameters);
    }

    /**
     * Deletes an organization.
     * 
     * @param organizationId - The ID of the organization to delete
     * @return A {@link CompletableFuture} that will return an empty response
     */
    public CompletableFuture<EmptyResponse> delete(String organizationId) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", organizationId);
        return new DeleteOrganization(client, gson).sendRequest(new EmptyRequest(), parameters);
    }

    /**
     * Gets all projects belonging to an organization.
     * 
     * @param organizationId - The ID of the organization
     * @return A {@link CompletableFuture} that will return the projects
     */
    public CompletableFuture<List<Project>> getProjects(String organizationId) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", organizationId);
        return new GetOrganizationProjects(client, gson).sendRequest(new EmptyRequest(), parameters);
    }

    /**
     * Adds a project to an organization.
     * 
     * @param organizationId - The ID of the organization
     * @param request        - The request containing project ID to add
     * @return A {@link CompletableFuture} that will return an empty response
     */
    public CompletableFuture<EmptyResponse> addProject(String organizationId,
            AddProjectToOrganization.AddProjectRequest request) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", organizationId);
        return new AddProjectToOrganization(client, gson).sendRequest(request, parameters);
    }

    /**
     * Removes a project from an organization.
     * 
     * @param organizationId - The ID of the organization
     * @param projectId      - The ID of the project to remove
     * @return A {@link CompletableFuture} that will return an empty response
     */
    public CompletableFuture<EmptyResponse> removeProject(String organizationId, String projectId) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", organizationId);
        parameters.put("project_id", projectId);
        return new RemoveProjectFromOrganization(client, gson).sendRequest(new EmptyRequest(), parameters);
    }

    /**
     * Changes an organization's icon.
     * 
     * @param organizationId - The ID of the organization
     * @param icon           - The icon file
     * @return A {@link CompletableFuture} that will return an empty response
     * @throws FileNotFoundException if the icon file is not found
     */
    public CompletableFuture<EmptyResponse> changeIcon(String organizationId, File icon)
            throws FileNotFoundException {
        return changeIcon(organizationId, new FileInputStream(icon), icon.getName());
    }

    /**
     * Changes an organization's icon.
     * 
     * @param organizationId - The ID of the organization
     * @param icon           - The icon stream
     * @param fileName       - The file name
     * @return A {@link CompletableFuture} that will return an empty response
     */
    public CompletableFuture<EmptyResponse> changeIcon(String organizationId, InputStream icon, String fileName) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", organizationId);
        return new ChangeOrganizationIcon(client, gson)
                .sendRequest(new ChangeOrganizationIcon.ChangeOrganizationIconRequest(icon, fileName), parameters);
    }

    /**
     * Deletes an organization's icon.
     * 
     * @param organizationId - The ID of the organization
     * @return A {@link CompletableFuture} that will return an empty response
     */
    public CompletableFuture<EmptyResponse> deleteIcon(String organizationId) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", organizationId);
        return new DeleteOrganizationIcon(client, gson).sendRequest(new EmptyRequest(), parameters);
    }
}
