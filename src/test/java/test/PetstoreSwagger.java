package test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class PetstoreSwagger {

    static {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }
    int id = 3535;

    @Test (priority = 1)
    public void CreateTest() {
        Map<String, Object> create = new HashMap<>();
        create.put("id", id);
        create.put("name", "Fatih");
        create.put("status", "available");

        Response response = given()
                .header("Content-Type", "application/json")
                .body(create)
                .when()
                .post("/pet")
                .then()
                .statusCode(200)
                .body("name", equalTo("Fatih"))
                .extract().response();

        Assert.assertEquals(response.jsonPath().getInt("id"), 3535);
    }

    @Test (priority = 2)
    public void GetByIdTest() {
        int petId = id;

        given()
                .when()
                .get("/pet/" + petId)
                .then()
                .statusCode(200)
                .body("id", equalTo(petId))
                .body("name", equalTo("Fatih"));
    }

    @Test (priority = 3)
    public void UpdateTest() {
        Map<String, Object> update = new HashMap<>();
        update.put("id", id);
        update.put("name", "Fatih");
        update.put("status", "sold");

        given()
                .header("Content-Type", "application/json")
                .body(update)
                .when()
                .put("/pet")
                .then()
                .statusCode(200)
                .body("name", equalTo("Fatih"))
                .body("status", equalTo("sold"));
    }

    @Test (priority = 4)
    public void DeleteTest() {
        int deleteId = id;

        given()
                .when()
                .delete("/pet/" + deleteId)
                .then()
                .statusCode(200)
                .body("message", equalTo(String.valueOf(deleteId)));

    }

    @Test (priority = 5)
    public void NegativeCreateTest() {
        Map<String, Object> invalid = new HashMap<>();
        invalid.put("id", "invalid_id");
        invalid.put("name", 455);
        invalid.put("status", "available");

        given()
                .header("Content-Type", "application/json")
                .body(invalid)
                .when()
                .post("/pet")
                .then()
                .statusCode(400);
    }
}
