package masecla.modrinth4j.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.codec.digest.DigestUtils;

import lombok.SneakyThrows;
import masecla.modrinth4j.endpoints.project.CreateProject.CreateProjectRequest;
import masecla.modrinth4j.endpoints.project.CreateProject.ProjectData;
import masecla.modrinth4j.main.ModrinthAPI;
import masecla.modrinth4j.model.project.Project;
import masecla.modrinth4j.model.project.SupportStatus;
import masecla.modrinth4j.model.tags.License;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DataUtil {
    public static Project createSampleProject(ModrinthAPI api) {
        License randomLicense = api.tags().getLicenses().join()[0];

        if (api.projects().checkSlugAvailability("modrinth4j-test-project").join())
            return api.projects().create(CreateProjectRequest.builder()
                    .data(ProjectData.builder()
                            .body("This nifty builder")
                            .categories(new String[] { "utility" })
                            .clientSide(SupportStatus.REQUIRED)
                            .serverSide(SupportStatus.REQUIRED)
                            .description("this is a test project")
                            .discordUrl("https://google.com")
                            .licenseId(randomLicense.getShortName())
                            .slug("modrinth4j-test-project")
                            .title("bruh")
                            .build())
                    .build()).join();
        return fetchSampleProject(api);
    }

    public static Project fetchSampleProject(ModrinthAPI api) {
        return api.projects().get("modrinth4j-test-project").join();
    }

    public static void deleteSampleProject(ModrinthAPI api) {
        api.projects().delete("modrinth4j-test-project").join();
    }

    public static File getImage() {
        return new File(DataUtil.class.getClassLoader().getResource("icon.png").getFile());
    }

    public static File getAnotherImage() {
        return new File(DataUtil.class.getClassLoader().getResource("icon2.png").getFile());
    }

    public static File getJar() {
        return new File(DataUtil.class.getClassLoader().getResource("AntiCaps.jar").getFile());
    }

    /**
     * This method downloads the file at URL, and checks if the hash of the file is
     * identical to the hash of the file provided.
     */
    @SneakyThrows
    public static boolean verifyIdentical(String url, File file){
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
