package ru.iakovleva.api.steps;

import io.restassured.response.ValidatableResponse;
import ru.iakovleva.api.models.Order;
import ru.iakovleva.api.spec.Specs;

import static io.restassured.RestAssured.given;

public class OrderSteps {
    public static Integer createOrder(Order order) {
        return given(Specs.request)
                .body(order)
                .when()
                .post("/v2/store/order")
                .then()
                .spec(Specs.OK)
                .extract().path("id");
    }

    public static ValidatableResponse findOrder(int orderId) {
        return given(Specs.request)
                .when()
                .get("/v2/store/order/" + orderId)
                .then()
                .spec(Specs.NOT_FOUND);
    }
}