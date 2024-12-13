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

        Assert.assertEquals(response.jsonPath().getInt("id"), id);
    }

    @Test (priority = 2)
    public void GetByIdTest() {

        given()
                .when()
                .get("/pet/" + id)
                .then()
                .statusCode(200)
                .body("id", equalTo(id))
                .body("name", equalTo("Fatih"));
    }

    @Test (priority = 3)
    public void UpdateTest() {

        Map<String, Object> update = new HashMap<>();
        update.put("id", id);
        update.put("name", "FatihG");
        update.put("status", "sold");

        given()
                .header("Content-Type", "application/json")
                .body(update)
                .when()
                .put("/pet")
                .then()
                .statusCode(200)
                .body("name", equalTo("FatihG"))
                .body("status", equalTo("sold"));
    }

    @Test (priority = 4)
    public void DeleteTest() {

        given()
                .when()
                .delete("/pet/" + id)
                .then()
                .statusCode(200)
                .body("message", equalTo(String.valueOf(id)));

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
