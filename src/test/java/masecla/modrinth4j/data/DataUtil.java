package masecla.modrinth4j.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;

import lombok.SneakyThrows;
import masecla.modrinth4j.endpoints.project.CreateProject.CreateProjectRequest;
import masecla.modrinth4j.endpoints.project.CreateProject.ProjectData;
import masecla.modrinth4j.endpoints.version.CreateVersion.CreateVersionRequest;
import masecla.modrinth4j.main.ModrinthAPI;
import masecla.modrinth4j.model.project.Project;
import masecla.modrinth4j.model.project.SupportStatus;
import masecla.modrinth4j.model.tags.License;
import masecla.modrinth4j.model.version.ProjectVersion;
import masecla.modrinth4j.model.version.ProjectVersion.VersionType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * This class is used to create sample data for testing.
 */
public class DataUtil {
    /**
     * Create a sample project for testing.
     * 
     * @param api - The API to use.
     * @return - The created project.
     */
    @SneakyThrows
    public static Project createSampleProject(ModrinthAPI api) {
        License randomLicense = api.tags().getLicenses().join().get(0);

        File icon = getAnotherImage();
        FileInputStream fis = new FileInputStream(icon);

        if (api.projects().checkSlugAvailability("modrinth4j-test-project").join())
            return api.projects().create(CreateProjectRequest.builder()
                    .iconData(fis)
                    .iconFilename(icon.getName())
                    .data(ProjectData.builder()
                            .body("This nifty builder")
                            .categories(Arrays.asList("utility"))
                            .clientSide(SupportStatus.REQUIRED)
                            .serverSide(SupportStatus.REQUIRED)
                            .description("this is a test project")
                            .discordUrl("https://google.com")
                            .licenseId(randomLicense.getShortName())
                            .slug("modrinth4j-test-project")
                            .title("bruh")
                            .build())
                    .build()).join();
        fis.close();
        return fetchSampleProject(api);
    }

    /**
     * Creates a 2nd sample project for testing.
     * 
     * @param api
     * @return
     */
    @SneakyThrows
    public static Project createAnotherSampleProject(ModrinthAPI api) {
        License randomLicense = api.tags().getLicenses().join().get(0);

        File icon = getImage();
        FileInputStream fis = new FileInputStream(icon);

        if (api.projects().checkSlugAvailability("modrinth4j-test-project-2").join())
            return api.projects().create(CreateProjectRequest.builder()
                    .iconData(fis)
                    .iconFilename(icon.getName())
                    .data(ProjectData.builder()
                            .body("This is another nifty builder")
                            .categories(Arrays.asList("utility"))
                            .clientSide(SupportStatus.REQUIRED)
                            .serverSide(SupportStatus.REQUIRED)
                            .description("this is another test project")
                            .discordUrl("https://google.com")
                            .licenseId(randomLicense.getShortName())
                            .slug("modrinth4j-test-project-2")
                            .title("bruh 2")
                            .build())
                    .build()).join();
        fis.close();
        return fetchSampleProject(api);
    }

    /**
     * Fetches the sample project.
     * 
     * @param api - The API to use.
     * @return - The fetched project.
     */
    public static Project fetchSampleProject(ModrinthAPI api) {
        return api.projects().get("modrinth4j-test-project").join();
    }

    /**
     * Deletes the sample project.
     * 
     * @param api - The API to use.
     */
    public static void deleteSampleProject(ModrinthAPI api) {
        api.projects().delete("modrinth4j-test-project").join();
    }

    /**
     * Deletes the 2nd sample project.
     * 
     * @param api - The API to use.
     */
    public static void deleteAnotherSampleProject(ModrinthAPI api) {
        api.projects().delete("modrinth4j-test-project-2").join();
    }

    /**
     * This will append a version to the sample project.
     * 
     * @param api       - The API to use.
     * @param projectId - The ID of the project to append the version to.
     * @return - The created version.
     */
    public static ProjectVersion appendFeaturedVersion(ModrinthAPI api, String projectId) {
        return api.versions().createProjectVersion(CreateVersionRequest.builder()
                .projectId(projectId)
                .changelog("This is a changelog")
                .gameVersions(Arrays.asList("1.16.5"))
                .versionNumber("1.0.0")
                .name("Version Name!")
                .featured(true)
                .versionType(VersionType.RELEASE)
                .loaders(Arrays.asList("spigot"))
                .files(getJar())
                .build()).join();
    }

    public static ProjectVersion appendUnfeaturedVersion(ModrinthAPI api, String projectId) {
        return api.versions().createProjectVersion(CreateVersionRequest.builder()
                .projectId(projectId)
                .changelog("This is a changelog")
                .gameVersions(Arrays.asList("1.16.5"))
                .versionNumber("1.0.1")
                .name("Version Name!")
                .featured(false)
                .versionType(VersionType.RELEASE)
                .loaders(Arrays.asList("spigot"))
                .files(getJar())
                .build()).join();
    }

    /**
     * This will return a sample image to be used in testing.
     * 
     * @return - The sample image.
     */
    public static File getImage() {
        return new File(DataUtil.class.getClassLoader().getResource("icon.png").getFile());
    }

    /**
     * This will return a different sample image to be used in testing.
     * 
     * @return - The sample image.
     */
    public static File getAnotherImage() {
        return new File(DataUtil.class.getClassLoader().getResource("icon2.png").getFile());
    }

    /**
     * This will return a sample jar to be used in testing.
     * 
     * @return - The sample jar.
     */
    public static File getJar() {
        return new File(DataUtil.class.getClassLoader().getResource("AntiCaps.jar").getFile());
    }

    /**
     * This will return a different sample jar to be used in testing.
     * 
     * @return - The sample jar.
     */
    public static File getAnotherJar() {
        return new File(DataUtil.class.getClassLoader().getResource("AntiCaps2.jar").getFile());
    }

    /**
     * This method downloads the file at URL, and checks if the hash of the file is
     * identical to the hash of the file provided.
     */
    @SneakyThrows
    public static boolean verifyIdentical(String url, File file) {
        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(new Request.Builder().url(url).build()).execute();
        InputStream stream = response.body().byteStream();

        // Calculate the hash of the stream
        String hash = DigestUtils.sha256Hex(stream);
        stream.close();

        FileInputStream fileStream = new FileInputStream(file);
        String fileHash = DigestUtils.sha256Hex(fileStream);

        // Close the stream
        fileStream.close();

        // Return if the hashes are identical
        return hash.equals(fileHash);
    }
}
