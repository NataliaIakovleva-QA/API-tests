package ru.iakovleva.api.steps;

import io.restassured.response.ValidatableResponse;
import ru.iakovleva.api.models.Pet;
import ru.iakovleva.api.models.PetStatus;
import ru.iakovleva.api.spec.Specs;

import static io.restassured.RestAssured.given;

public class PetSteps {
    public static ValidatableResponse findByStatus(PetStatus status) {
        return given(Specs.request)
                .when()
                .params("status", status.getCode())
                .get("/v2/pet/findByStatus")
                .then()
                .spec(Specs.OK);
    }

    public static Integer createPet(Pet pet) {
        return given(Specs.request)
                .body(pet)
                .when()
                .post("/v2/pet")
                .then()
                .spec(Specs.OK)
                .extract().path("id");
    }
}