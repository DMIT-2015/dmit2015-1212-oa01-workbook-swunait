package dmit2015.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import dmit2015.entity.TodoItem;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

/**
 * https://github.com/rest-assured/rest-assured
 * https://github.com/rest-assured/rest-assured/wiki/Usage
 * http://www.mastertheboss.com/jboss-frameworks/resteasy/restassured-tutorial
 * https://eclipse-ee4j.github.io/jsonb-api/docs/user-guide.html
 * https://github.com/FasterXML/jackson-databind
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TodoItemResourceRestAssuredUsingJsonbIT {

    String todoResourceUrl = "http://localhost:8080/dmit2015-1212-jaxrs-demo/webapi/TodoItems";
    String testDataResourceLocation;

    @Order(1)
    @Test
    void shouldListAll() throws JsonProcessingException {
        Response response = given()
//                .accept(ContentType.JSON)
                .when()
                .get(todoResourceUrl)
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
                .post(todoResourceUrl)
                .then()
                .statusCode(201)
                .extract()
                .response();
        testDataResourceLocation = response.getHeader("location");
    }

    @Order(3)
    @Test
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
    void shouldUpdate() throws JsonProcessingException {
        // Fetch the test data record that was created in shouldCreate method
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
        // Use Jakarta JSON Binding to convert a Java object to a JSON string
        Jsonb jsonb = JsonbBuilder.create();
        // Convert the response body JSON string to a TodoItem object
        TodoItem existingTodoItem = jsonb.fromJson(jsonBody, TodoItem.class);

        // Update the name and complete flag
        assertNotNull(existingTodoItem);
        existingTodoItem.setName("REST Assured updated data");
        existingTodoItem.setComplete(true);

        // Convert the TodoItem object to a JSON string
        String jsonRequestBody = jsonb.toJson(existingTodoItem);
        // Update the resource should return a status of 200
        given()
                .contentType(ContentType.JSON)
                .body(jsonRequestBody)
                .when()
                .put(testDataResourceLocation)
                .then()
                .statusCode(200);

        // Verify that record has been updated
        String responseData = given()
                .accept(ContentType.JSON)
                .when()
                .get(testDataResourceLocation)
                .asString();
        // Convert the JSON string to a TodoItem object
        TodoItem updatedTodoItem = jsonb.fromJson(responseData, TodoItem.class);
        // Verify the value of the id has not changed and the name and complete property values has changed
        assertEquals(existingTodoItem.getId(), updatedTodoItem.getId());
        assertEquals("REST Assured updated data", updatedTodoItem.getName());
        assertEquals(true, updatedTodoItem.isComplete());

        // Try updating the same resource again and it should now return a status of 400
        // since we are no longer updating the resource using the latest version of the data
        existingTodoItem.setName("Updated name again should fail");
        existingTodoItem.setComplete(false);
        jsonRequestBody = jsonb.toJson(existingTodoItem);
        given()
                .contentType(ContentType.JSON)
                .body(jsonRequestBody)
                .when()
                .put(testDataResourceLocation)
                .then()
                .statusCode(400);

    }

    @Order(5)
    @Test
    void shouldDelete() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .delete(testDataResourceLocation)
                .then()
                .statusCode(204);
    }

}

