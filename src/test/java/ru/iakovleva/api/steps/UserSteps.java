package ru.iakovleva.api.steps;

import io.restassured.response.ValidatableResponse;
import ru.iakovleva.api.models.User;
import ru.iakovleva.api.spec.Specs;

import static io.restassured.RestAssured.given;

public class UserSteps {
    public static String createUser(User user) {
        return given(Specs.request)
                .body(user)
                .when()
                .post("/v2/user")
                .then()
                .spec(Specs.OK)
                .extract().path("message");
    }

    public static void updateUser(User user) {
        given(Specs.request)
                .body(user)
                .when()
                .put("/v2/user/" + user.getUsername())
                .then()
                .spec(Specs.OK);
    }

    public static User getUser(String username) {
        return given(Specs.request)
                .when()
                .get("/v2/user/" + username)
                .then()
                .spec(Specs.OK)
                .extract().as(User.class);
    }

    public static void deleteUser(String username) {
        given(Specs.request)
                .when()
                .delete("/v2/user/" + username)
                .then()
                .spec(Specs.OK);
    }

    public static ValidatableResponse getUserNotFound(String username) {
        return given(Specs.request)
                .when()
                .get("/v2/user/" + username)
                .then()
                .spec(Specs.NOT_FOUND);
    }
}