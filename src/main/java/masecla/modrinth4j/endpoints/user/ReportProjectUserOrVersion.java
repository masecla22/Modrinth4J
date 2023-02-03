package masecla.modrinth4j.endpoints.user;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.user.ReportProjectUserOrVersion.ReportProjectUserOrVersionRequest;
import masecla.modrinth4j.endpoints.user.ReportProjectUserOrVersion.ReportProjectUserOrVersionResponse;

/**
 * This endpoint is used to report a project, user, or version.
 */
public class ReportProjectUserOrVersion
        extends Endpoint<ReportProjectUserOrVersionResponse, ReportProjectUserOrVersionRequest> {

    /**
     * This enum is used to represent the type of object being reported.
     */
    public static enum ReportedObjectType {
        /** A project */
        @SerializedName("project")
        PROJECT,
        /** A user */
        @SerializedName("user")
        USER,
        /** A version */
        @SerializedName("version")
        VERSION;
    }

    /**
     * This enum is used to represent the type of report.
     */
    public static enum ReportType {
        /** The report is spam */
        @SerializedName("spam")
        SPAM,
        /** The report is a copyright violation */
        @SerializedName("copyright")
        COPYRIGHT,
        /** The report is inappropriate */
        @SerializedName("inappropriate")
        INAPPROPRIATE,
        /** The report is a malicious link */
        @SerializedName("malicious")
        MALICIOUS,
        /** The report is a name squatting */
        @SerializedName("name-squatting")
        NAME_SQUATTING,
        /** The report is something else */
        @SerializedName("other")
        OTHER;
    }

    /**
     * This class is used to represent the request.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReportProjectUserOrVersionRequest {
        /** The type of report */
        private ReportType reportType;
        /** The ID of the object being reported */
        private String itemId;
        /** The type of object being reported */
        private ReportedObjectType itemType;
        /** The body of the report */
        private String body;
    }

    /**
     * This class is used to represent the response.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReportProjectUserOrVersionResponse {
        /** The report type */
        private ReportType reportType;
        /** The ID of the object being reported */
        private String itemId;
        /** The type of object being reported */
        private ReportedObjectType itemType;
        /** The body of the report */
        private String body;

        /** The ID of the report */
        private String reporter;

        /** ISO 8601 String */
        private String created;
    }

    /**
     * Creates a new instance of this endpoint.
     * 
     * @param client The client to use.
     * @param gson   The gson instance to use.
     */
    public ReportProjectUserOrVersion(HttpClient client, Gson gson) {
        super(client, gson);
    }

    /**
     * Returns the endpoint
     */
    @Override
    public String getEndpoint() {
        return "/report";
    }

    /**
     * Returns the request class
     */
    @Override
    public TypeToken<ReportProjectUserOrVersionRequest> getRequestClass() {
        return TypeToken.get(ReportProjectUserOrVersionRequest.class);
    }

    /**
     * Returns the response class
     */
    @Override
    public TypeToken<ReportProjectUserOrVersionResponse> getResponseClass() {
        return TypeToken.get(ReportProjectUserOrVersionResponse.class);
    }

    /**
     * Returns the method
     */
    @Override
    public String getMethod() {
        return "POST";
    }
}
