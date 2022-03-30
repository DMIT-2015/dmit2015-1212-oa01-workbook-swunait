package dmit2015.restclient;

import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.Map;

/**
 * The baseUri for the web service be set in either microprofile-config.properties (recommended)
 * or in this file using @RegisterRestClient(baseUri = "http://server/path").
 *
 * To set the baseUri in microprofile-config.properties:
 *    1) Open src/main/resources/META-INF/microprofile-config.properties
 *    2) Add a key/value pair in the following format:
 *          package-name.ClassName/mp-rest/url=baseUri
 *       For example:
 *          package-name:    dmit2015.restclient
 *          ClassName:       MovieService
 *          baseUri:         https://yourFirebaseProjectName-default-rtdb.firebaseio.com
 *       The key/value pair you need to add is:
 *          dmit2015.restclient.MovieService/mp-rest/url=https://yourFirebaseProjectName-default-rtdb.firebaseio.com
 *
 *
 * To use the client interface from an environment does support CDI, add @Inject and @RestClient before the field declaration such as:
 *
 *     @Inject
 *     @RestClient
 *     private MovieService _movieService;
 *
 * To use the client interface from an environment that does not support CDI, you can use the RestClientBuilder class to programmatically build an instance as follows:
 *
 *      URI apiURi = new URI("http://sever/path");
 *      MovieService _movieService = RestClientBuilder.newBuilder()
 *                 .baseUri(apiURi)
 *                 .build(MovieService.class);
 *
 */
//@RegisterRestClient(baseUri = "https://yourFirebaseProjectName-default-rtdb.firebaseio.com")
@RegisterRestClient
public interface MovieService {

    @POST
    @Path("/movies.json")
    JsonObject create(Movie newMovie);

    @GET
    @Path("/movies.json")
    Map<String, Movie> list();

    @GET
    @Path("/movies/{key}.json")
    Movie findById(@PathParam("key") String id);

    @PUT
    @Path("/movies/{key}.json")
    Movie update(@PathParam("key") String id, Movie existingMovie);

    @DELETE
    @Path("/movies/{key}.json")
    void delete(@PathParam("key") String id);

}
