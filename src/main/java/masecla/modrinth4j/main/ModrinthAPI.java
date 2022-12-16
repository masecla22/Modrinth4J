package masecla.modrinth4j.main;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.client.instances.UnlimitedHttpClient;
import masecla.modrinth4j.endpoints.project.ProjectEndpoints;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ModrinthAPI {
    private HttpClient client;
    private String apiKey;

    public static ModrinthAPI unlimited(String apiKey){
        HttpClient client = new UnlimitedHttpClient(apiKey);
        return new ModrinthAPI(client, apiKey);
    }

    public ProjectEndpoints projects() {
        
    }
}