package dmit2015.restclient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://api.ipgeolocation.io/ipgeo")
public interface GeoLocationService {

    @GET
    GeoLocation withIPAddress(
            @QueryParam("apiKey") String apiKey,
            @QueryParam("ip") String ip);

    @GET
    GeoLocation withoutIPAddress(@QueryParam("apiKey") String apiKey );

}
