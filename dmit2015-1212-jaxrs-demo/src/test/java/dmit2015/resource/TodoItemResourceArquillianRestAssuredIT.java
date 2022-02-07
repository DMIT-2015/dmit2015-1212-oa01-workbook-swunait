package dmit2015.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import dmit2015.config.ApplicationConfig;
import dmit2015.config.JAXRSConfiguration;
import dmit2015.entity.TodoItem;
import dmit2015.repository.TodoItemRepository;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

/**
 * https://github.com/rest-assured/rest-assured
 * https://github.com/rest-assured/rest-assured/wiki/Usage
 * http://www.mastertheboss.com/jboss-frameworks/resteasy/restassured-tutorial
 * https://github.com/FasterXML/jackson-databind
 *
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ArquillianExtension.class)                  // Run with JUnit 5 instead of JUnit 4
public class TodoItemResourceArquillianRestAssuredIT {

    @Deployment
    public static WebArchive createDeployment() throws IOException, XmlPullParserException {
        PomEquippedResolveStage pomFile = Maven.resolver().loadPomFromFile("pom.xml");
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = reader.read(new FileReader("pom.xml"));
        final String archiveName = model.getArtifactId() + ".war";
        return ShrinkWrap.create(WebArchive.class,archiveName)
//                .addAsLibraries(pomFile.resolve("com.h2database:h2:2.1.210").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("org.hsqldb:hsqldb:2.6.1").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("com.microsoft.sqlserver:mssql-jdbc:10.2.0.jre17").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("com.oracle.database.jdbc:ojdbc11:21.5.0.0").withTransitivity().asFile())
                .addClasses(ApplicationConfig.class, JAXRSConfiguration.class)
                .addClasses(TodoItem.class, TodoItemRepository.class, TodoItemResource.class)
//                .addPackage("dmit2015.config")
//                .addPackage("dmit2015.entity")
//                .addPackage("dmit2015.repository")
//                .addPackage("dmit2015.resource")
                .addAsResource("META-INF/persistence.xml")
                .addAsResource("META-INF/sql/import-data.sql")
//                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
                .addAsWebInfResource(EmptyAsset.INSTANCE,"beans.xml");
    }

    @Test
    @RunAsClient
    public void checkSiteIsUp() {
        given().when().get("https://www.nait.ca/").then().statusCode(200);
//        given().when().get("http://localhost:8080/dmit2015-1212-jaxrs-demo/h2-console").then().statusCode(200);
    }

    String testDataResourceLocation;

    @Order(1)
    @Test
    @RunAsClient
    void shouldListAll() throws JsonProcessingException {
        Response response = given()
        		.urlEncodingEnabled(false)
                .accept(ContentType.JSON)
                .when()
                .get("/dmit2015-1212-jaxrs-demo/webapi/TodoItems")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .response();
        String jsonBody = response.getBody().asString();

        Jsonb jsonb = JsonbBuilder.create();
        List<TodoItem> todos = jsonb.fromJson(jsonBody, new ArrayList<TodoItem>(){}.getClass().getGenericSuperclass());

        assertEquals(3, todos.size());
        TodoItem firstTodoItem = todos.get(0);
        assertEquals("Todo 1", firstTodoItem.getName());
        assertFalse(firstTodoItem.isComplete());

        TodoItem lastTodoItem = todos.get(todos.size() - 1);
        assertEquals("Todo 3", lastTodoItem.getName());
        assertFalse(lastTodoItem.isComplete());

    }

    @Order(2)
    @Test
    @RunAsClient
    void shouldCreate() throws JsonProcessingException {
        TodoItem newTodoItem = new TodoItem();
        newTodoItem.setName("Create REST Assured Integration Test");
        newTodoItem.setComplete(false);

        Jsonb jsonb = JsonbBuilder.create();
        String jsonBody = jsonb.toJson(newTodoItem);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post("/dmit2015-1212-jaxrs-demo/webapi/TodoItems")
                .then()
                .statusCode(201)
                .extract()
                .response();
        testDataResourceLocation = response.getHeader("location");
    }

    @Order(3)
    @Test
    @RunAsClient
    void shouldFineOne() throws JsonProcessingException {
        Response response = given()
                .accept(ContentType.JSON)
                .when()
                .get(testDataResourceLocation)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .response();
        String jsonBody = response.getBody().asString();

        Jsonb jsonb = JsonbBuilder.create();
        TodoItem existingTodoItem = jsonb.fromJson(jsonBody, TodoItem.class);

        assertNotNull(existingTodoItem);
        assertEquals("Create REST Assured Integration Test", existingTodoItem.getName());
        assertFalse(existingTodoItem.isComplete());
    }

    @Order(4)
    @Test
    @RunAsClient
    void shouldUpdate() throws JsonProcessingException {
        Response response = given()
                .accept(ContentType.JSON)
                .when()
                .get(testDataResourceLocation)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .response();
        String jsonBody = response.getBody().asString();

        Jsonb jsonb = JsonbBuilder.create();
        TodoItem existingTodoItem = jsonb.fromJson(jsonBody, TodoItem.class);

        assertNotNull(existingTodoItem);
        existingTodoItem.setName("Updated Name");
        existingTodoItem.setComplete(true);

//        String jsonRequestBody = mapper.writeValueAsString(existingTodoItem);
        String jsonRequestBody = jsonb.toJson(existingTodoItem);
        given()
                .contentType(ContentType.JSON)
                .body(jsonRequestBody)
                .when()
                .put(testDataResourceLocation)
                .then()
                .statusCode(200);
    }

    @Order(5)
    @Test
    @RunAsClient
    void shouldDelete() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .delete(testDataResourceLocation)
                .then()
                .statusCode(204);
    }

}

