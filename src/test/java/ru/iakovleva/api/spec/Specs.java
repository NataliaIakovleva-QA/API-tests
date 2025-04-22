package ru.iakovleva.api.spec;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static ru.iakovleva.api.helpers.CustomAllureListener.withCustomTemplates;

public class Specs {

    public static final RequestSpecification PETSTORE_REQUEST_SPEC = with()
            .filter(withCustomTemplates())
            .contentType(ContentType.JSON)
            .log().all();

    public static ResponseSpecification responseSpec(int statusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .build();
    }

    public static final ResponseSpecification OK = responseSpec(200);
    public static final ResponseSpecification NOT_FOUND = responseSpec(404);
}