package masecla.modrinth4j.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import lombok.SneakyThrows;
import masecla.modrinth4j.data.DataUtil;
import masecla.modrinth4j.endpoints.version.CreateVersion.CreateVersionRequest;
import masecla.modrinth4j.endpoints.version.GetProjectVersions.GetProjectVersionsRequest;
import masecla.modrinth4j.endpoints.version.ModifyVersion.ModifyVersionRequest;
import masecla.modrinth4j.endpoints.version.files.GetProjectLatestVersionFromHash.GetProjectLatestVersionFromHashRequest;
import masecla.modrinth4j.endpoints.version.files.GetProjectLatestVersionsFromHashes.GetProjectLatestVersionsFromHashesRequest;
import masecla.modrinth4j.environment.EnvReader;
import masecla.modrinth4j.exception.EndpointException;
import masecla.modrinth4j.main.ModrinthAPI;
import masecla.modrinth4j.model.project.Project;
import masecla.modrinth4j.model.version.FileHash;
import masecla.modrinth4j.model.version.ProjectVersion;
import masecla.modrinth4j.model.version.ProjectVersion.VersionType;
import masecla.modrinth4j.model.version.files.HashProjectVersionMap;

/**
 * This class tests the version endpoints.
 */
public class VersionEndpointsTests {
    /** The API used for testing */
    private ModrinthAPI client;

    /**
     * This method sets up the client for testing.
     */
    @Before
    public void setupClient() {
        EnvReader env = new EnvReader();
        this.client = ModrinthAPI.rateLimited(env.getAgent(), env.getStagingUrl(), env.getApiKey());

        DataUtil.createSampleProject(client);
    }

    /**
     * This method wipes the project after testing.
     */
    @After
    public void wipeProject() {
        DataUtil.deleteSampleProject(client);
    }

    /**
     * This method tests the fecthing of a version.
     */
    @Test
    public void testGetVersion() {
        assertTrue(
                client.versions().getVersion("NlIrj4dz").join().getProjectId().equals("AULzIar5"));
    }

    /**
     * This method tests the fecthing of multiple versions.
     */
    @Test
    public void testGetVersions() {
        String projectId = DataUtil.fetchSampleProject(client).getId();
        ProjectVersion version = DataUtil.appendFeaturedVersion(client, projectId);
        assertTrue(client.versions().getVersion("NlIrj4dz", version.getId()).join().size() == 2);
    }

    /**
     * This method tests the creation and fetching of a version.
     */
    @Test
    public void testProjectVersions() {
        Project prj = DataUtil.fetchSampleProject(client);
        ProjectVersion version = DataUtil.appendFeaturedVersion(client, prj.getId());
        List<ProjectVersion> vers = client.versions().getProjectVersions(prj.getSlug(),
                GetProjectVersionsRequest.builder().build()).join();

        assertEquals("There should only be one version!", vers.size(), 1);

        // For some reason datePublished isn't consistent, so wipe it for both before
        // comparison
        // https://github.com/modrinth/labrinth/issues/532
        version.setDatePublished(null);
        vers.get(0).setDatePublished(null);

        assertTrue("Versions were not identical!", version.equals(vers.get(0)));
    }

    /**
     * This method will check searching by featured and unfeatured.
     */
    @Test
    public void testFeaturedLookup() {
        Project prj = DataUtil.fetchSampleProject(client);
        ProjectVersion featVer = DataUtil.appendFeaturedVersion(client, prj.getId());
        ProjectVersion unfeatVer = DataUtil.appendUnfeaturedVersion(client, prj.getId());

        List<ProjectVersion> featVers = client.versions().getProjectVersions(prj.getSlug(),
                GetProjectVersionsRequest.builder().featured(true).build()).join();
        List<ProjectVersion> unfeatVers = client.versions().getProjectVersions(prj.getSlug(),
                GetProjectVersionsRequest.builder().featured(false).build()).join();
        List<ProjectVersion> allVers = client.versions().getProjectVersions(prj.getSlug(),
                GetProjectVersionsRequest.builder().build()).join();

        assertEquals("Featured versions should only be 1!", featVers.size(), 1);
        assertEquals("Unfeatured versions should only be 1!", unfeatVers.size(), 1);
        assertEquals("All versions should only be 2!", allVers.size(), 2);

        // Same consistency issue as above
        featVers.get(0).setDatePublished(null);
        unfeatVers.get(0).setDatePublished(null);
        featVer.setDatePublished(null);
        unfeatVer.setDatePublished(null);

        assertTrue("Featured version was not the featured version!", featVers.get(0).equals(featVer));
        assertTrue("Unfeatured version was not the unfeatured version!",
                unfeatVers.get(0).equals(unfeatVer));
    }

    @Test
    public void testProjectDateCreationDate() {
        Project prj = DataUtil.fetchSampleProject(client);
        DataUtil.appendFeaturedVersion(client, prj.getId());
        List<ProjectVersion> vers = client.versions().getProjectVersions(prj.getSlug(),
                GetProjectVersionsRequest.builder().build()).join();

        Instant now = Instant.now();
        Instant versionDate = vers.get(0).getDatePublished();

        // Should be less than 10 seconds apart (this should test date parsing)
        assertTrue("Version date was not within 10 seconds of now!",
                now.plusSeconds(10).isAfter(versionDate));
    }

    /**
     * This method tests the modification of a version.
     */
    @Test
    public void testModifyProjectVersion() {
        Project prj = DataUtil.fetchSampleProject(client);
        ProjectVersion version = DataUtil.appendFeaturedVersion(client, prj.getId());

        client.versions().modifyProjectVersion(version.getId(), ModifyVersionRequest.builder()
                .changelog("This is a DIFFERENT changelog")
                .featured(false)
                .gameVersions(Arrays.asList("1.12.2"))
                .loaders(Arrays.asList("paper"))
                .name("diff name")
                .versionNumber("1.0.1")
                .versionType(VersionType.BETA)
                .build()).join();

        ProjectVersion modified = client.versions().getVersion(version.getId()).join();

        assertTrue("Changelog was not modified!", modified.getChangelog().equals("This is a DIFFERENT changelog"));
        assertTrue("Featured was not modified!", modified.isFeatured() == false);
        assertTrue("Game versions was not modified!", modified.getGameVersions().get(0).equals("1.12.2"));
        assertTrue("Loaders was not modified!", modified.getLoaders().get(0).equals("paper"));
        assertTrue("Name was not modified!", modified.getName().equals("diff name"));
        assertTrue("Version number was not modified!", modified.getVersionNumber().equals("1.0.1"));
        assertTrue("Version type was not modified!", modified.getVersionType().equals(VersionType.BETA));
    }

    /**
     * This method tests the deletion of a version.
     */
    @Test
    public void testDeleteProjectVersion() {
        Project prj = DataUtil.fetchSampleProject(client);
        ProjectVersion version = DataUtil.appendFeaturedVersion(client, prj.getId());
        client.versions().deleteProjectVersion(version.getId()).join();
        assertTrue(client.versions().getProjectVersions(prj.getSlug(), GetProjectVersionsRequest.builder().build())
                .join().size() == 0);
    }

    /**
     * This method will make sure .join() throws an exception when making a version
     */
    @Test
    public void testJoinThrow() {
        Project prj = DataUtil.fetchSampleProject(client);
        try {
            client.versions().createProjectVersion(CreateVersionRequest.builder()
                    .changelog("This is a changelog")
                    .featured(true)
                    .projectId(prj.getId())
                    .loaders(Arrays.asList("paper"))
                    .name("name")
                    .versionNumber("1.0.0")
                    .files(Arrays.asList(DataUtil.getJar()))
                    .versionType(VersionType.RELEASE)
                    .build()).join();
        } catch (CompletionException e) {
            assertTrue(e.getCause() instanceof EndpointException);
            EndpointException ex = (EndpointException) e.getCause();
            assertEquals("invalid_input", ex.getError());
            assertTrue(ex.getDescription().contains("game_versions"));
        }
    }

    /**
     * This method tests the creation of a version and asserts all properties
     */
    @Test
    public void testCreateProject() {
        Project prj = DataUtil.fetchSampleProject(client);
        ProjectVersion version = client.versions().createProjectVersion(CreateVersionRequest.builder()
                .changelog("This is a changelog")
                .featured(true)
                .projectId(prj.getId())
                .gameVersions(Arrays.asList("1.12.2"))
                .loaders(Arrays.asList("paper"))
                .name("name")
                .versionNumber("1.0.0")
                .files(Arrays.asList(DataUtil.getJar()))
                .versionType(VersionType.RELEASE)
                .build()).join();

        assertTrue(version.getChangelog().equals("This is a changelog"));
        assertTrue(version.isFeatured() == true);
        assertTrue(version.getGameVersions().get(0).equals("1.12.2"));
        assertTrue(version.getLoaders().get(0).equals("paper"));
        assertTrue(version.getName().equals("name"));
        assertTrue(version.getVersionNumber().equals("1.0.0"));
        assertTrue(version.getVersionType().equals(VersionType.RELEASE));
    }

    /**
     * This method tests adding files to a version
     */
    @Test
    @SneakyThrows
    public void testAddFilesToVersion() {
        Project prj = DataUtil.fetchSampleProject(client);
        ProjectVersion version = DataUtil.appendFeaturedVersion(client, prj.getId());
        client.versions().addFilesToVersion(version.getId(), Arrays.asList(DataUtil.getAnotherJar())).join();

        assertTrue(client.versions().getVersion(version.getId()).join().getFiles().size() == 2);
    }

    /**
     * This method tests getting a version by hash
     */
    @Test
    public void testGetVersionByHash() {
        Project prj = DataUtil.fetchSampleProject(client);
        ProjectVersion version = DataUtil.appendFeaturedVersion(client, prj.getId());

        ProjectVersion vers = client.versions().files()
                .getVersionByHash(FileHash.SHA1, version.getFiles().get(0).getHashes().getSha1()).join();

        assertTrue(vers.getId().equals(version.getId()));
    }

    /**
     * This method tests getting a version by project number and version
     * 
     */
    @Test
    public void testGetVersionByProjectNumberAndVersion() {
        Project prj = DataUtil.fetchSampleProject(client);
        ProjectVersion version = DataUtil.appendFeaturedVersion(client, prj.getId());

        ProjectVersion vers = client.versions().getVersionByNumber(prj.getSlug(), version.getVersionNumber()).join();

        assertTrue(vers.getId().equals(version.getId()));
    }

    /**
     * This method tests getting a version by hash SHA-512
     */
    @Test
    public void testGetVersionByHash512() {
        Project prj = DataUtil.fetchSampleProject(client);
        ProjectVersion version = DataUtil.appendFeaturedVersion(client, prj.getId());

        ProjectVersion vers = client.versions().files()
                .getVersionByHash(FileHash.SHA512, version.getFiles().get(0).getHashes().getSha512()).join();

        assertEquals(vers.getId(), version.getId());
    }

    /**
     * This method tests getting multiple versions by hash
     */
    @Test
    @SneakyThrows
    public void testGetVersionsByHash() {
        Project prj = DataUtil.fetchSampleProject(client);
        ProjectVersion version = DataUtil.appendFeaturedVersion(client, prj.getId());

        // Add a file
        client.versions().addFilesToVersion(version.getId(), Arrays.asList(DataUtil.getAnotherJar())).join();

        version = client.versions().getVersion(version.getId()).join();

        HashProjectVersionMap vers = client.versions().files()
                .getVersionByHash(FileHash.SHA1, version.getFiles().get(0).getHashes().getSha1(),
                        version.getFiles().get(1).getHashes().getSha1())
                .join();

        assertTrue(vers.size() == 2);
        assertTrue(vers.get(version.getFiles().get(0).getHashes().getSha1()).getId().equals(version.getId()));
        assertTrue(vers.get(version.getFiles().get(1).getHashes().getSha1()).getId().equals(version.getId()));
    }

    /**
     * This method tests the deletion of a file by hash
     */
    @Test
    @SneakyThrows
    public void testDeleteFileByHash() {
        Project prj = DataUtil.fetchSampleProject(client);
        ProjectVersion version = DataUtil.appendFeaturedVersion(client, prj.getId());

        // Add file
        client.versions().addFilesToVersion(version.getId(), Arrays.asList(DataUtil.getAnotherJar())).join();
        version = client.versions().getVersion(version.getId()).join();

        client.versions().files().deleteFileByHash(FileHash.SHA1, version.getFiles().get(0).getHashes().getSha1())
                .join();

        assertTrue(client.versions().getVersion(version.getId()).join().getFiles().size() == 1);
    }

    /**
     * This method tests getting the latest version by hash
     */
    @Test
    public void testGetLatestVersionByHash() {
        Project prj = DataUtil.fetchSampleProject(client);
        ProjectVersion version = DataUtil.appendFeaturedVersion(client, prj.getId());

        ProjectVersion vers = client.versions().files()
                .getLatestVersionByHash(FileHash.SHA1, version.getFiles().get(0).getHashes().getSha1(),
                        GetProjectLatestVersionFromHashRequest.builder().build())
                .join();

        assertTrue(vers.getId().equals(version.getId()));
    }

    /**
     * This method tests getting the latest versions by hash
     */
    @Test
    @SneakyThrows
    public void testGetLatestVersionsByHash() {
        Project prj = DataUtil.fetchSampleProject(client);
        ProjectVersion version = DataUtil.appendFeaturedVersion(client, prj.getId());

        // Add a file
        client.versions().addFilesToVersion(version.getId(), Arrays.asList(DataUtil.getAnotherJar())).join();

        version = client.versions().getVersion(version.getId()).join();

        HashProjectVersionMap vers = client.versions().files()
                .getLatestVersionsByHash(GetProjectLatestVersionsFromHashesRequest.builder()
                        .algorithm(FileHash.SHA1)
                        .hashes(Arrays.asList(
                                version.getFiles().get(0).getHashes().getSha1(),
                                version.getFiles().get(1).getHashes().getSha1()))
                        .build())
                .join();

        assertTrue(vers.size() == 1);
        assertTrue(vers.values().stream().findAny().orElse(null).getId().equals(version.getId()));
    }
}