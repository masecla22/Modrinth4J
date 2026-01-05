package masecla.modrinth4j.endpoints.threads;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;

/**
 * Endpoint for sending a message to a thread.
 */
public class SendThreadMessage extends Endpoint<EmptyResponse, SendThreadMessage.SendMessageRequest> {

    public SendThreadMessage(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/v3/thread/{id}";
    }

    @Override
    public String getMethod() {
        return "POST";
    }

    @Override
    public TypeToken<EmptyResponse> getResponseClass() {
        return TypeToken.get(EmptyResponse.class);
    }

    @Override
    public TypeToken<SendMessageRequest> getRequestClass() {
        return TypeToken.get(SendMessageRequest.class);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SendMessageRequest {
        /** The message body */
        private MessageBody body;
        
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class MessageBody {
            /** The type of message (e.g., "text") */
            private String type;
            
            /** The message content */
            private String body;
            
            /** Whether the message is private */
            private Boolean private_;
            
            /** ID of message being replied to */
            private String replyingTo;
            
            /** Associated image IDs */
            private List<String> associatedImages;
        }
    }
}
