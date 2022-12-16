package masecla.modrinth4j.main;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.client.instances.UnlimitedHttpClient;
import masecla.modrinth4j.endpoints.SearchEndpoint;
import masecla.modrinth4j.endpoints.SearchEndpoint.SearchRequest;
import masecla.modrinth4j.endpoints.SearchEndpoint.SearchResponse;
import masecla.modrinth4j.endpoints.project.ProjectEndpoints;
import masecla.modrinth4j.endpoints.version.VersionEndpoints;
import masecla.modrinth4j.model.search.FacetCollection;
import masecla.modrinth4j.model.search.FacetCollection.FacetAdapter;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ModrinthAPI {

    static {
        allowMethods("PATCH");
    }

    @NonNull
    private HttpClient client;
    @NonNull
    private String apiKey;

    private Gson gson;

    public static ModrinthAPI unlimited(String apiKey) {
        HttpClient client = new UnlimitedHttpClient(apiKey);
        ModrinthAPI result = new ModrinthAPI(client, apiKey);

        result.initializeGson();
        return result;
    }

    private void initializeGson() {
        this.gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(FacetCollection.class, new FacetAdapter())
                .create();
    }

    public CompletableFuture<SearchResponse> search(SearchRequest request) {
        SearchEndpoint endpoint = new SearchEndpoint(client, gson);
        return endpoint.sendRequest(request);
    }

    public ProjectEndpoints projects() {
        return new ProjectEndpoints(gson, client);
    }

    public VersionEndpoints versions() {
        return new VersionEndpoints(gson, client);
    }

    private static void allowMethods(String... methods) {
        try {
            Field methodsField = HttpURLConnection.class.getDeclaredField("methods");

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);

            methodsField.setAccessible(true);

            String[] oldMethods = (String[]) methodsField.get(null);
            Set<String> methodsSet = new LinkedHashSet<>(Arrays.asList(oldMethods));
            methodsSet.addAll(Arrays.asList(methods));
            String[] newMethods = methodsSet.toArray(new String[0]);

            methodsField.set(null/* static field */, newMethods);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }
}