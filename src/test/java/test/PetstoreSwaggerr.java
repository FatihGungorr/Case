package test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class PetstoreSwaggerr {

    static {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @Test (priority = 1)
    public void CreateTest() {
        String requestBody = "{" +
                "\"id\": 3535," +
                "\"name\": \"Fatih\"," +
                "\"status\": \"available\"" +
                "}";

        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/pet")
                .then()
                .statusCode(200)
                .body("name", equalTo("Fatih"))
                .extract().response();

        Assert.assertEquals(response.jsonPath().getInt("id"), 3535);
    }

    @Test (priority = 2)
    public void GetById() {
        int GetId = 3535;

        given()
                .when()
                .get("/pet/" + GetId)
                .then()
                .statusCode(200)
                .body("id", equalTo(GetId))
                .body("name", equalTo("Fatih"));
    }

    @Test (priority = 3)
    public void UpdateTest() {
        String updated = "{" +
                "\"id\": 35355," +
                "\"name\": \"FatihG\"," +
                "\"status\": \"sold\"" +
                "}";

        given()
                .header("Content-Type", "application/json")
                .body(updated)
                .when()
                .put("/pet")
                .then()
                .statusCode(200)
                .body("name", equalTo("FatihG"))
                .body("status", equalTo("sold"));
    }

    @Test (priority = 4)
    public void DeleteTest() {
        int DeleteId = 35355;

        given()
                .when()
                .delete("/pet/" + DeleteId)
                .then()
                .statusCode(200)
                .body("message", equalTo(String.valueOf(DeleteId)));

    }

    @Test (priority = 5)
    public void NegativeCreateTest() {
        String invalid = "{" +
                "\"id\": \"invalid_id\"," +
                "\"name\": 123," +
                "\"status\": \"available\"" +
                "}";

        given()
                .header("Content-Type", "application/json")
                .body(invalid)
                .when()
                .post("/pet")
                .then()
                .statusCode(400);
    }
}
