package ru.iakovleva.api;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.iakovleva.api.data.DataGenerator;
import ru.iakovleva.api.models.Order;
import ru.iakovleva.api.models.Pet;
import ru.iakovleva.api.models.User;
import ru.iakovleva.api.spec.Specs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

public class RestApiTests {

    @Test
    @Tag("Pet")
    @Tag("positive")
    @Tag("low")
    @DisplayName("Find by status Available")
    @Severity(SeverityLevel.NORMAL)
    void findByStatusAvailableTest() {
        given(Specs.request)
                .when()
                .params("status", "available")
                .get("/v2/pet/findByStatus")
                .then()
                .spec(Specs.responseSpec)
                .body("id", hasSize(greaterThan(0)));
    }

    @Test
    @Tag("Pet")
    @Tag("positive")
    @Tag("smoke")
    @DisplayName("Find by status Pending")
    @Severity(SeverityLevel.NORMAL)
    void findByStatusPendingTest() {
        given(Specs.request)
                .when()
                .params("status", "pending")
                .get("/v2/pet/findByStatus")
                .then()
                .spec(Specs.responseSpec)
                .body("id", hasSize(greaterThan(0)));
    }

    @Test
    @Tag("Pet")
    @Tag("positive")
    @Tag("smoke")
    @DisplayName("Find by status Sold")
    @Severity(SeverityLevel.NORMAL)
    void findByStatusSoldTest() {
        given(Specs.request)
                .when()
                .params("status", "sold")
                .get("/v2/pet/findByStatus")
                .then()
                .spec(Specs.responseSpec)
                .body("id", hasSize(greaterThan(0)));
    }

    @Test
    @Tag("Pet")
    @Tag("negative")
    @Tag("low")
    @DisplayName("Find by status Null")
    @Severity(SeverityLevel.NORMAL)
    void findByStatusNullTest() {
        given(Specs.request)
                .when()
                .params("status", "null")
                .get("/v2/pet/findByStatus")
                .then()
                .spec(Specs.responseSpec);
    }

    @Test
    @Tag("Pet")
    @Tag("positive")
    @Tag("smoke")
    @DisplayName("Create pet")
    @Severity(SeverityLevel.BLOCKER)
    void createPetTest() {
        Pet newPet = DataGenerator.getPet(8, 16, true, true, true);

        Integer response = given(Specs.request)
                .body(newPet)
                .when()
                .post("/v2/pet")
                .then()
                .spec(Specs.responseSpec)
                .extract().path("id");

        assertThat(response).isEqualTo(newPet.getId());
    }

    @Test
    @Tag("User")
    @Tag("positive")
    @Tag("smoke")
    @DisplayName("Create User")
    @Severity(SeverityLevel.BLOCKER)
    void createUserTest() {
        User newUser = DataGenerator.getUser(8, 16, true, true, true);

        String response = given(Specs.request)
                .body(newUser)
                .when()
                .post("/v2/user")
                .then()
                .spec(Specs.responseSpec)
                .extract().path("message");

        assertThat(response).isEqualTo(newUser.getId().toString());
    }

    @Test
    @Tag("User")
    @Tag("negative")
    @Tag("low")
    @DisplayName("Create User with array")
    @Severity(SeverityLevel.BLOCKER)
    void createWithArrayTest() {
        User newUser = DataGenerator.getUser(8, 16, true, true, true);

        String response = given(Specs.request)
                .body(newUser)
                .when()
                .post("/v2/user/createWithArray")
                .then()
                .statusCode(500)
                .extract().path("message");

        assertThat(response).isEqualTo("something bad happened");
    }

    @Test
    @Tag("User")
    @Tag("negative")
    @Tag("low")
    @DisplayName("Create User with list")
    @Severity(SeverityLevel.BLOCKER)
    void createWithListTest() {
        User newUser = DataGenerator.getUser(8, 16, true, true, true);

        String response = given(Specs.request)
                .body(newUser)
                .when()
                .post("/v2/user/createWithList")
                .then()
                .statusCode(500)
                .extract().path("type");

        assertThat(response).isEqualTo("unknown");
    }


    @Test
    @Tag("User")
    @Tag("negative")
    @Tag("smoke")
    @DisplayName("Get empty User")
    @Severity(SeverityLevel.TRIVIAL)
    void getEmptyUserTest() {
        given(Specs.request)
                .when()
                .get("v2/user/user55")
                .then()
                .statusCode(404)
                .body("message", is("User not found"));
    }

    @Test
    @Tag("User")
    @Tag("negative")
    @Tag("smoke")
    @DisplayName("Get null User")
    @Severity(SeverityLevel.TRIVIAL)
    void getNullUserTest() {
        given(Specs.request)
                .when()
                .get("v2/user/")
                .then()
                .statusCode(405);
    }

    @Test
    @Tag("Order")
    @Tag("positive")
    @Tag("smoke")
    @DisplayName("Create Order")
    @Severity(SeverityLevel.NORMAL)
    void createOrderTest() {
        Order newOrder = DataGenerator.getOrder(8, 16, true, true, true);

        Integer response = given(Specs.request)
                .body(newOrder)
                .when()
                .post("/v2/store/order")
                .then()
                .spec(Specs.responseSpec)
                .extract().path("id");

        assertThat(response).isEqualTo(newOrder.getId());
    }


    @Test
    @Tag("Order")
    @Story("Order")
    @DisplayName("Find order")
    @Severity(SeverityLevel.CRITICAL)
    void findOrderTest() {
        given(Specs.request)
                .when()
                .get("/v2/store/order/20")
                .then()
                .statusCode(404)
                .body("message", is("Order not found"));
    }

}
