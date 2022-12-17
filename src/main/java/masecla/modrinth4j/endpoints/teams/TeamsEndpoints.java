package masecla.modrinth4j.endpoints.teams;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import masecla.modrinth4j.client.HttpClient;

@AllArgsConstructor
public class TeamsEndpoints {
    private Gson gson;
    private HttpClient client;
}
