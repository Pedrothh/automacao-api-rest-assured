package utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Token {

    public static String token = "";

    public static String getAccessToken() {
        if (!token.equalsIgnoreCase("")) {
            return token;
        }

        Login login = new Login();
        login.setUsername("admin");
        login.setPassword("12345");

        Response response = given()
                .filter(new AllureRestAssured())
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(login)
                .when()
                .post("https://my-public-api-for-tests-production.up.railway.app/api/login")
                .then().log().all().extract().response();

        JsonObject obj = new Gson().fromJson(response.asString(), JsonObject.class);
        token = "Bearer " + obj.get("token").getAsString();
        System.out.println(token);
        return token;
    }
}