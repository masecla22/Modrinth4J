package masecla.modrinth4j.client;

import static org.junit.Assert.assertTrue;

import java.io.File;

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
import masecla.modrinth4j.main.ModrinthAPI;
import masecla.modrinth4j.model.project.Project;
import masecla.modrinth4j.model.version.FileHash;
import masecla.modrinth4j.model.version.ProjectVersion;
import masecla.modrinth4j.model.version.ProjectVersion.VersionType;
import masecla.modrinth4j.model.version.files.HashProjectVersionMap;

public class VersionEndpointsTests {
    private ModrinthAPI client;

    @Before
    public void setupClient() {
        EnvReader env = new EnvReader();
        this.client = ModrinthAPI.unlimited(env.getAgent(), env.getStagingUrl(), env.getApiKey());

        DataUtil.createSampleProject(client);
    }

    @After
    public void wipeProject() {
        DataUtil.deleteSampleProject(client);
    }

    @Test
    public void testGetVersion() {
        assertTrue(
                client.versions().getVersion("NlIrj4dz").join().getProjectId().equals("AULzIar5"));
    }

    @Test
    public void testGetVersions() {
        String projectId = DataUtil.fetchSampleProject(client).getId();
        ProjectVersion version = DataUtil.appendVersion(client, projectId);
        assertTrue(client.versions().getVersion("NlIrj4dz", version.getId()).join().length == 2);
    }

    @Test
    public void testProjectVersions() {
        Project prj = DataUtil.fetchSampleProject(client);
        ProjectVersion version = DataUtil.appendVersion(client, prj.getId());
        ProjectVersion[] vers = client.versions().getProjectVersions(prj.getSlug(),
                GetProjectVersionsRequest.builder().build()).join();
        assertTrue(vers.length == 1);

        // For some reason datePublished isn't consistent, so wipe it for both before
        // comparison
        version.setDatePublished(null);
        vers[0].setDatePublished(null);

        assertTrue("Versions were not identical!", version.equals(vers[0]));
    }

    @Test
    public void testModifyProjectVersion() {
        Project prj = DataUtil.fetchSampleProject(client);
        ProjectVersion version = DataUtil.appendVersion(client, prj.getId());

        client.versions().modifyProjectVersion(version.getId(), ModifyVersionRequest.builder()
                .changelog("This is a DIFFERENT changelog")
                .featured(false)
                .gameVersions(new String[] { "1.12.2" })
                .loaders(new String[] { "paper" })
                .name("diff name")
                .versionNumber("1.0.1")
                .versionType(VersionType.BETA)
                .build()).join();

        ProjectVersion modified = client.versions().getVersion(version.getId()).join();

        assertTrue("Changelog was not modified!", modified.getChangelog().equals("This is a DIFFERENT changelog"));
        assertTrue("Featured was not modified!", modified.isFeatured() == false);
        assertTrue("Game versions was not modified!", modified.getGameVersions()[0].equals("1.12.2"));
        assertTrue("Loaders was not modified!", modified.getLoaders()[0].equals("paper"));
        assertTrue("Name was not modified!", modified.getName().equals("diff name"));
        assertTrue("Version number was not modified!", modified.getVersionNumber().equals("1.0.1"));
        assertTrue("Version type was not modified!", modified.getVersionType().equals(VersionType.BETA));
    }

    @Test
    public void testDeleteProjectVersion() {
        Project prj = DataUtil.fetchSampleProject(client);
        ProjectVersion version = DataUtil.appendVersion(client, prj.getId());
        client.versions().deleteProjectVersion(version.getId()).join();
        assertTrue(client.versions().getProjectVersions(prj.getSlug(), GetProjectVersionsRequest.builder().build())
                .join().length == 0);
    }

    @Test
    public void testCreateProject() {
        Project prj = DataUtil.fetchSampleProject(client);
        ProjectVersion version = client.versions().createProjectVersion(CreateVersionRequest.builder()
                .changelog("This is a changelog")
                .featured(true)
                .projectId(prj.getId())
                .gameVersions(new String[] { "1.12.2" })
                .loaders(new String[] { "paper" })
                .name("name")
                .versionNumber("1.0.0")
                .files(new File[] { DataUtil.getJar() })
                .versionType(VersionType.RELEASE)
                .build()).join();

        assertTrue(version.getChangelog().equals("This is a changelog"));
        assertTrue(version.isFeatured() == true);
        assertTrue(version.getGameVersions()[0].equals("1.12.2"));
        assertTrue(version.getLoaders()[0].equals("paper"));
        assertTrue(version.getName().equals("name"));
        assertTrue(version.getVersionNumber().equals("1.0.0"));
        assertTrue(version.getVersionType().equals(VersionType.RELEASE));
    }

    @Test
    @SneakyThrows
    public void testAddFilesToVersion() {
        Project prj = DataUtil.fetchSampleProject(client);
        ProjectVersion version = DataUtil.appendVersion(client, prj.getId());
        client.versions().addFilesToVersion(version.getId(), new File[] { DataUtil.getAnotherJar() }).join();

        assertTrue(client.versions().getVersion(version.getId()).join().getFiles().length == 2);
    }

    @Test
    public void testGetVersionByHash() {
        Project prj = DataUtil.fetchSampleProject(client);
        ProjectVersion version = DataUtil.appendVersion(client, prj.getId());

        ProjectVersion vers = client.versions().files()
                .getVersionByHash(FileHash.SHA1, version.getFiles()[0].getHashes().getSha1()).join();

        assertTrue(vers.getId().equals(version.getId()));
    }

    @Test
    @SneakyThrows
    public void testGetVersionsByHash() {
        Project prj = DataUtil.fetchSampleProject(client);
        ProjectVersion version = DataUtil.appendVersion(client, prj.getId());

        // Add a file
        client.versions().addFilesToVersion(version.getId(), new File[] { DataUtil.getAnotherJar() }).join();

        version = client.versions().getVersion(version.getId()).join();

        HashProjectVersionMap vers = client.versions().files()
                .getVersionByHash(FileHash.SHA1, version.getFiles()[0].getHashes().getSha1(),
                        version.getFiles()[1].getHashes().getSha1())
                .join();

        assertTrue(vers.size() == 2);
        assertTrue(vers.get(version.getFiles()[0].getHashes().getSha1()).getId().equals(version.getId()));
        assertTrue(vers.get(version.getFiles()[1].getHashes().getSha1()).getId().equals(version.getId()));
    }

    @Test
    @SneakyThrows
    public void testDeleteFileByHash() {
        Project prj = DataUtil.fetchSampleProject(client);
        ProjectVersion version = DataUtil.appendVersion(client, prj.getId());

        // Add file
        client.versions().addFilesToVersion(version.getId(), new File[] { DataUtil.getAnotherJar() }).join();
        version = client.versions().getVersion(version.getId()).join();

        client.versions().files().deleteFileByHash(FileHash.SHA1, version.getFiles()[0].getHashes().getSha1()).join();

        assertTrue(client.versions().getVersion(version.getId()).join().getFiles().length == 1);
    }

    @Test
    public void testGetLatestVersionByHash() {
        Project prj = DataUtil.fetchSampleProject(client);
        ProjectVersion version = DataUtil.appendVersion(client, prj.getId());

        ProjectVersion vers = client.versions().files()
                .getLatestVersionByHash(FileHash.SHA1, version.getFiles()[0].getHashes().getSha1(),
                        GetProjectLatestVersionFromHashRequest.builder().build())
                .join();

        assertTrue(vers.getId().equals(version.getId()));
    }

    @Test
    @SneakyThrows
    public void testGetLatestVersionsByHash() {
        Project prj = DataUtil.fetchSampleProject(client);
        ProjectVersion version = DataUtil.appendVersion(client, prj.getId());

        // Add a file
        client.versions().addFilesToVersion(version.getId(), new File[] { DataUtil.getAnotherJar() }).join();

        version = client.versions().getVersion(version.getId()).join();

        HashProjectVersionMap vers = client.versions().files()
                .getLatestVersionsByHash(GetProjectLatestVersionsFromHashesRequest.builder()
                        .algorithm(FileHash.SHA1)
                        .hashes(new String[] { 
                                version.getFiles()[0].getHashes().getSha1(),
                                version.getFiles()[1].getHashes().getSha1() })
                        .build())
                .join();

        assertTrue(vers.size() == 1);
        assertTrue(vers.values().stream().findAny().orElse(null).getId().equals(version.getId()));
    }
}