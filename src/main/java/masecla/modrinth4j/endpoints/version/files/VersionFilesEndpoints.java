package masecla.modrinth4j.endpoints.version.files;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import masecla.modrinth4j.client.HttpClient;

@AllArgsConstructor
public class VersionFilesEndpoints {
    private Gson gson;
    private HttpClient client;
}
