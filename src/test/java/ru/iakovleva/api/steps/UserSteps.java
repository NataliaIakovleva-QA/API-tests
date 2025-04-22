package ru.iakovleva.api.steps;

import io.restassured.response.ValidatableResponse;
import ru.iakovleva.api.models.User;
import ru.iakovleva.api.spec.Specs;

import static io.restassured.RestAssured.given;

public class UserSteps {
    public static String createUser(User user) {
        return given(Specs.PETSTORE_REQUEST_SPEC)
                .body(user)
                .when()
                .post("/user")
                .then()
                .spec(Specs.OK)
                .extract().path("message");
    }

    public static void updateUser(User user) {
        given(Specs.PETSTORE_REQUEST_SPEC)
                .body(user)
                .when()
                .put("/user/" + user.getUsername())
                .then()
                .spec(Specs.OK);
    }

    public static User getUser(String username) {
        return given(Specs.PETSTORE_REQUEST_SPEC)
                .when()
                .get("/user/" + username)
                .then()
                .spec(Specs.OK)
                .extract().as(User.class);
    }

    public static void deleteUser(String username) {
        given(Specs.PETSTORE_REQUEST_SPEC)
                .when()
                .delete("/user/" + username)
                .then()
                .spec(Specs.OK);
    }

    public static ValidatableResponse getUserNotFound(String username) {
        return given(Specs.PETSTORE_REQUEST_SPEC)
                .when()
                .get("/user/" + username)
                .then()
                .spec(Specs.NOT_FOUND);
    }

    public static ValidatableResponse createWithArray(User[] users) {
        return given(Specs.PETSTORE_REQUEST_SPEC)
                .body(users)
                .when()
                .post("/user/createWithArray")
                .then()
                .spec(Specs.responseSpec(500));
    }

    public static ValidatableResponse createWithList(User[] users) {
        return given(Specs.PETSTORE_REQUEST_SPEC)
                .body(users)
                .when()
                .post("/user/createWithList")
                .then()
                .spec(Specs.responseSpec(500));
    }
}