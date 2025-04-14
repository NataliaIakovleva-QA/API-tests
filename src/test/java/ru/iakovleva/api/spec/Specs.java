package ru.iakovleva.api.spec;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static ru.iakovleva.api.helpers.CustomAllureListener.withCustomTemplates;


//public class Specs {
//    public static RequestSpecification request = with()
//            .filter(withCustomTemplates())
//            .baseUri("https://petstore.swagger.io")
//            .log().all()
//            .contentType(ContentType.JSON);
//
//    public static ResponseSpecification OK = new ResponseSpecBuilder()
//            .expectStatusCode(200)
//            .build();
//
//    public static ResponseSpecification NOT_FOUND = new ResponseSpecBuilder()
//            .expectStatusCode(404)
//            .build();
//}

public class Specs {
    public static RequestSpecification request = with()
            .filter(withCustomTemplates())
            .baseUri("https://petstore.swagger.io")
            .log().all()
            .contentType(ContentType.JSON);

    public static ResponseSpecification responseSpec(int statusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .build();
    }

    public static final ResponseSpecification OK = responseSpec(200);
    public static final ResponseSpecification NOT_FOUND = responseSpec(404);

}
