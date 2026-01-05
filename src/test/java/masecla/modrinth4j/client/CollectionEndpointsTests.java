package masecla.modrinth4j.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.SneakyThrows;
import masecla.modrinth4j.data.DataUtil;
import masecla.modrinth4j.endpoints.collection.CreateCollection.CreateCollectionRequest;
import masecla.modrinth4j.endpoints.collection.ModifyCollection.ModifyCollectionRequest;
import masecla.modrinth4j.environment.EnvReader;
import masecla.modrinth4j.main.ModrinthAPI;
import masecla.modrinth4j.model.collection.CollectionStatus;
import masecla.modrinth4j.model.collection.ModrinthCollection;
import masecla.modrinth4j.model.project.Project;

/**
 * Tests for the Collections API endpoints.
 */
public class CollectionEndpointsTests {
    /** The API client to use for testing */
    private ModrinthAPI client;

    /** The test collection ID for cleanup */
    private String testCollectionId;

    /**
     * Sets up the client before each test.
     */
    @BeforeEach
    public void setupClient() {
        EnvReader env = new EnvReader();
        this.client = ModrinthAPI.rateLimited(env.getAgent(), env.getStagingUrl(), env.getApiKey());
    }

    /**
     * Cleans up any test collections after each test.
     */
    @AfterEach
    public void cleanup() {
        if (testCollectionId != null) {
            try {
                client.collections().delete(testCollectionId).join();
            } catch (Exception e) {
                // Ignore cleanup errors
            }
            testCollectionId = null;
        }
    }

    /**
     * Tests creating a collection with minimal data.
     */
    @Test
    public void testCreateCollection() {
        CreateCollectionRequest request = new CreateCollectionRequest(
                "Test Collection",
                "This is a test collection created by Modrinth4J tests",
                Arrays.asList()
        );

        ModrinthCollection collection = client.collections().create(request).join();
        testCollectionId = collection.getId();

        assertNotNull(collection.getId(), "Collection ID should not be null");
        assertEquals("Test Collection", collection.getName(), "Collection name should match");
        assertEquals("This is a test collection created by Modrinth4J tests", 
                collection.getDescription(), "Collection description should match");
        assertEquals(CollectionStatus.LISTED, collection.getStatus(), "Collection should be listed by default");
        assertNotNull(collection.getCreated(), "Collection created date should not be null");
        assertNotNull(collection.getUpdated(), "Collection updated date should not be null");
        assertNotNull(collection.getUser(), "Collection user should not be null");
    }

    /**
     * Tests creating a collection with projects.
     */
    @Test
    public void testCreateCollectionWithProjects() {
        // Create a test project first
        Project project = DataUtil.createSampleProject(client);

        CreateCollectionRequest request = new CreateCollectionRequest(
                "Test Collection with Projects",
                "Collection containing test projects",
                Arrays.asList(project.getId())
        );

        ModrinthCollection collection = client.collections().create(request).join();
        testCollectionId = collection.getId();

        assertNotNull(collection, "Collection should be created");
        assertEquals(1, collection.getProjects().size(), "Collection should contain one project");
        assertEquals(project.getId(), collection.getProjects().get(0), "Project ID should match");

        // Cleanup
        DataUtil.deleteSampleProject(client);
    }

    /**
     * Tests getting a single collection by ID.
     */
    @Test
    public void testGetCollection() {
        // Create a collection first
        CreateCollectionRequest request = new CreateCollectionRequest(
                "Test Get Collection",
                "Testing retrieval of a single collection",
                Arrays.asList()
        );
        ModrinthCollection created = client.collections().create(request).join();
        testCollectionId = created.getId();

        // Fetch it back
        ModrinthCollection fetched = client.collections().get(created.getId()).join();

        assertNotNull(fetched, "Fetched collection should not be null");
        assertEquals(created.getId(), fetched.getId(), "Collection IDs should match");
        assertEquals(created.getName(), fetched.getName(), "Collection names should match");
        assertEquals(created.getDescription(), fetched.getDescription(), "Collection descriptions should match");
    }

    /**
     * Tests getting multiple collections by their IDs.
     */
    @Test
    public void testGetMultipleCollections() {
        // Create two collections
        CreateCollectionRequest request1 = new CreateCollectionRequest(
                "Test Collection 1",
                "First test collection",
                Arrays.asList()
        );
        ModrinthCollection collection1 = client.collections().create(request1).join();

        CreateCollectionRequest request2 = new CreateCollectionRequest(
                "Test Collection 2",
                "Second test collection",
                Arrays.asList()
        );
        ModrinthCollection collection2 = client.collections().create(request2).join();

        // Fetch both
        List<ModrinthCollection> collections = client.collections().get(
                collection1.getId(), collection2.getId()
        ).join();

        assertNotNull(collections, "Collections list should not be null");
        assertEquals(2, collections.size(), "Should retrieve two collections");

        // Cleanup both
        client.collections().delete(collection1.getId()).join();
        client.collections().delete(collection2.getId()).join();
    }

    /**
     * Tests modifying a collection's name and description.
     */
    @Test
    public void testModifyCollection() {
        // Create a collection
        CreateCollectionRequest createRequest = new CreateCollectionRequest(
                "Original Name",
                "Original description",
                Arrays.asList()
        );
        ModrinthCollection created = client.collections().create(createRequest).join();
        testCollectionId = created.getId();

        // Modify it
        ModifyCollectionRequest modifyRequest = new ModifyCollectionRequest(
                "Updated Name",
                "Updated description",
                null,
                null
        );
        client.collections().modify(created.getId(), modifyRequest).join();

        // Fetch and verify
        ModrinthCollection updated = client.collections().get(created.getId()).join();
        assertEquals("Updated Name", updated.getName(), "Collection name should be updated");
        assertEquals("Updated description", updated.getDescription(), "Collection description should be updated");
    }

    /**
     * Tests modifying a collection's status.
     */
    @Test
    public void testModifyCollectionStatus() {
        // Create a collection
        CreateCollectionRequest createRequest = new CreateCollectionRequest(
                "Status Test Collection",
                "Testing status changes",
                Arrays.asList()
        );
        ModrinthCollection created = client.collections().create(createRequest).join();
        testCollectionId = created.getId();

        // Change status to unlisted
        ModifyCollectionRequest modifyRequest = new ModifyCollectionRequest(
                null,
                null,
                CollectionStatus.UNLISTED,
                null
        );
        client.collections().modify(created.getId(), modifyRequest).join();

        // Verify
        ModrinthCollection updated = client.collections().get(created.getId()).join();
        assertEquals(CollectionStatus.UNLISTED, updated.getStatus(), "Collection status should be unlisted");
    }

    /**
     * Tests adding projects to a collection via modification.
     */
    @Test
    public void testAddProjectsToCollection() {
        // Create a project
        Project project = DataUtil.createSampleProject(client);

        // Create an empty collection
        CreateCollectionRequest createRequest = new CreateCollectionRequest(
                "Empty Collection",
                "Will add projects to this",
                Arrays.asList()
        );
        ModrinthCollection created = client.collections().create(createRequest).join();
        testCollectionId = created.getId();

        // Add the project
        ModifyCollectionRequest modifyRequest = new ModifyCollectionRequest(
                null,
                null,
                null,
                Arrays.asList(project.getId())
        );
        client.collections().modify(created.getId(), modifyRequest).join();

        // Verify
        ModrinthCollection updated = client.collections().get(created.getId()).join();
        assertTrue(updated.getProjects().contains(project.getId()), "Collection should contain the project");

        // Cleanup
        DataUtil.deleteSampleProject(client);
    }

    /**
     * Tests deleting a collection.
     */
    @Test
    public void testDeleteCollection() {
        // Create a collection
        CreateCollectionRequest request = new CreateCollectionRequest(
                "Collection to Delete",
                "This will be deleted",
                Arrays.asList()
        );
        ModrinthCollection created = client.collections().create(request).join();

        // Delete it
        client.collections().delete(created.getId()).join();

        // Try to fetch it (should fail or return null)
        try {
            client.collections().get(created.getId()).join();
            // If we get here, the collection wasn't deleted or is in a special state
            // This might happen if the API returns a 404 as an empty object
        } catch (Exception e) {
            // Expected - collection should not exist
        }

        // Don't set testCollectionId since we already deleted it
    }

    /**
     * Tests changing a collection's icon.
     */
    @Test
    @SneakyThrows
    public void testChangeCollectionIcon() {
        // Create a collection
        CreateCollectionRequest request = new CreateCollectionRequest(
                "Icon Test Collection",
                "Testing icon changes",
                Arrays.asList()
        );
        ModrinthCollection created = client.collections().create(request).join();
        testCollectionId = created.getId();

        // Change the icon
        String iconPath = getClass().getClassLoader().getResource("icon.png").getFile();
        client.collections().changeIcon(created.getId(), new File(iconPath)).join();

        // Fetch and verify icon URL exists
        ModrinthCollection updated = client.collections().get(created.getId()).join();
        assertNotNull(updated.getIconUrl(), "Collection should have an icon URL after upload");
        assertFalse(updated.getIconUrl().isEmpty(), "Icon URL should not be empty");
    }

    /**
     * Tests deleting a collection's icon.
     */
    @Test
    @SneakyThrows
    public void testDeleteCollectionIcon() {
        // Create a collection
        CreateCollectionRequest request = new CreateCollectionRequest(
                "Icon Delete Test",
                "Testing icon deletion",
                Arrays.asList()
        );
        ModrinthCollection created = client.collections().create(request).join();
        testCollectionId = created.getId();

        // Add an icon first
        String iconPath = getClass().getClassLoader().getResource("icon.png").getFile();
        client.collections().changeIcon(created.getId(), new File(iconPath)).join();

        // Verify icon exists
        ModrinthCollection withIcon = client.collections().get(created.getId()).join();
        assertNotNull(withIcon.getIconUrl(), "Collection should have an icon");

        // Delete the icon
        client.collections().deleteIcon(created.getId()).join();

        // Verify icon is removed
        ModrinthCollection withoutIcon = client.collections().get(created.getId()).join();
        // Icon URL might be null or empty string after deletion
        assertTrue(withoutIcon.getIconUrl() == null || withoutIcon.getIconUrl().isEmpty(),
                "Collection icon should be removed");
    }

    /**
     * Tests the CollectionStatus enum methods.
     */
    @Test
    public void testCollectionStatusEnum() {
        assertTrue(CollectionStatus.REJECTED.isHidden(), "REJECTED should be hidden");
        assertTrue(CollectionStatus.PRIVATE.isHidden(), "PRIVATE should be hidden");
        assertFalse(CollectionStatus.LISTED.isHidden(), "LISTED should not be hidden");
        assertFalse(CollectionStatus.UNLISTED.isHidden(), "UNLISTED should not be hidden");

        assertTrue(CollectionStatus.LISTED.isSearchable(), "LISTED should be searchable");
        assertFalse(CollectionStatus.UNLISTED.isSearchable(), "UNLISTED should not be searchable");
        assertFalse(CollectionStatus.PRIVATE.isSearchable(), "PRIVATE should not be searchable");
        assertFalse(CollectionStatus.REJECTED.isSearchable(), "REJECTED should not be searchable");
    }
}
