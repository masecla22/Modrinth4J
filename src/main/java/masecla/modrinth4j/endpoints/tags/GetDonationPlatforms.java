package masecla.modrinth4j.endpoints.tags;

import com.google.gson.Gson;

import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.model.tags.DonationPlatform;

public class GetDonationPlatforms extends Endpoint<DonationPlatform[], EmptyRequest> {

    public GetDonationPlatforms(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/tag/donation_platform";
    }

    @Override
    public Class<EmptyRequest> getRequestClass() {
        return EmptyRequest.class;
    }

    @Override
    public Class<DonationPlatform[]> getResponseClass() {
        return DonationPlatform[].class;
    }
}
