package masecla.modrinth4j.endpoints.user;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.user.ReportProjectUserOrVersion.ReportProjectUserOrVersionRequest;
import masecla.modrinth4j.endpoints.user.ReportProjectUserOrVersion.ReportProjectUserOrVersionResponse;

public class ReportProjectUserOrVersion
        extends Endpoint<ReportProjectUserOrVersionResponse, ReportProjectUserOrVersionRequest> {
    public static enum ReportedObjectType {
        @SerializedName("project")
        PROJECT,
        @SerializedName("user")
        USER,
        @SerializedName("version")
        VERSION;
    }

    public static enum ReportType {
        @SerializedName("spam")
        SPAM,
        @SerializedName("copyright")
        COPYRIGHT,
        @SerializedName("inappropriate")
        INAPPROPRIATE,
        @SerializedName("malicious")
        MALICIOUS,
        @SerializedName("name-squatting")
        NAME_SQUATTING,
        @SerializedName("other")
        OTHER;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReportProjectUserOrVersionRequest {
        private ReportType reportType;
        private String itemId;
        private ReportedObjectType itemType;
        private String body;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReportProjectUserOrVersionResponse {
        private ReportType reportType;
        private String itemId;
        private ReportedObjectType itemType;
        private String body;

        private String reporter;

        /** ISO 8601 String */
        private String created;
    }

    public ReportProjectUserOrVersion(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/report";
    }

    @Override
    public Class<ReportProjectUserOrVersionRequest> getRequestClass() {
        return ReportProjectUserOrVersionRequest.class;
    }

    @Override
    public Class<ReportProjectUserOrVersionResponse> getResponseClass() {
        return ReportProjectUserOrVersionResponse.class;
    }

    @Override
    public String getMethod() {
        return "POST";
    }
}
