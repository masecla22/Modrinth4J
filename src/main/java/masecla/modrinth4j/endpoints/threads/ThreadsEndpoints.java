package masecla.modrinth4j.endpoints.threads;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;
import masecla.modrinth4j.model.thread.ModrinthThread;

/**
 * Endpoints for managing threads (conversations).
 */
@AllArgsConstructor
public class ThreadsEndpoints {
    /** The Gson instance to use */
    private Gson gson;
    /** The HTTP client to use */
    private HttpClient client;

    /**
     * Gets a single thread by its ID.
     * 
     * @param threadId - The ID of the thread to fetch
     * @return A {@link CompletableFuture} that will return the thread
     */
    public CompletableFuture<ModrinthThread> get(String threadId) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", threadId);
        return new GetThread(client, gson).sendRequest(new EmptyRequest(), parameters);
    }

    /**
     * Gets all threads for the current user.
     * 
     * @return A {@link CompletableFuture} that will return the threads
     */
    public CompletableFuture<List<ModrinthThread>> getAll() {
        return new GetThreads(client, gson).sendRequest(new EmptyRequest());
    }

    /**
     * Sends a message to a thread.
     * 
     * @param threadId - The ID of the thread
     * @param request  - The message to send
     * @return A {@link CompletableFuture} that will return an empty response
     */
    public CompletableFuture<EmptyResponse> sendMessage(String threadId, SendThreadMessage.SendMessageRequest request) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", threadId);
        return new SendThreadMessage(client, gson).sendRequest(request, parameters);
    }

    /**
     * Deletes a message from a thread.
     * 
     * @param messageId - The ID of the message to delete
     * @return A {@link CompletableFuture} that will return an empty response
     */
    public CompletableFuture<EmptyResponse> deleteMessage(String messageId) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", messageId);
        return new DeleteThreadMessage(client, gson).sendRequest(new EmptyRequest(), parameters);
    }
}
