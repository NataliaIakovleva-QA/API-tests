package ru.iakovleva.api;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.iakovleva.api.data.DataGenerator;
import ru.iakovleva.api.models.Order;
import ru.iakovleva.api.models.Pet;
import ru.iakovleva.api.models.PetStatus;
import ru.iakovleva.api.models.User;
import ru.iakovleva.api.spec.Specs;
import ru.iakovleva.api.steps.PetSteps;
import ru.iakovleva.api.steps.UserSteps;
import ru.iakovleva.api.steps.OrderSteps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static io.restassured.RestAssured.given;

public class RestApiTests {

    @ParameterizedTest(name = "Find by status {0}")
    @EnumSource(PetStatus.class)
    @Tag("Pet")
    @Tag("positive")
    @Tag("low")
    @Severity(SeverityLevel.NORMAL)
    void findByStatus(PetStatus status) {
        ValidatableResponse resp = PetSteps.findByStatus(status);
        if (!status.isExpectedZeroResult()) {  // Теперь этот метод существует
            resp.body("id", hasSize(greaterThan(0)));
        }
    }

    @Test
    @Tag("Pet")
    @Tag("positive")
    @Tag("smoke")
    @DisplayName("Create pet")
    @Severity(SeverityLevel.BLOCKER)
    void createPetTest() {
        Pet newPet = DataGenerator.getPet();
        Integer response = PetSteps.createPet(newPet);
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
        String response = UserSteps.createUser(newUser);
        assertThat(response).isEqualTo(newUser.getId().toString());
    }

    @Test
    @Tag("User")
    @Tag("positive")
    @Tag("smoke")
    @DisplayName("Create, update and delete user")
    @Severity(SeverityLevel.BLOCKER)
    void crudUserTest() {
        User newUser = DataGenerator.getUser(8, 16, true, true, true);

        // Create
        String response = UserSteps.createUser(newUser);
        assertThat(response).isEqualTo(newUser.getId().toString());

        // Update
        newUser.setFirstName("Vasya");
        newUser.setLastName("is here");
        UserSteps.updateUser(newUser);

        // Check
        User loadedUser = UserSteps.getUser(newUser.getUsername());
        assertThat(loadedUser).isNotNull();
        assertThat(loadedUser.getId()).isEqualTo(newUser.getId());
        assertThat(loadedUser.getFirstName()).isEqualTo(newUser.getFirstName());
        assertThat(loadedUser.getLastName()).isEqualTo(newUser.getLastName());

        // Delete
        UserSteps.deleteUser(newUser.getUsername());
        UserSteps.getUserNotFound(newUser.getUsername());
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
        Order newOrder = DataGenerator.getOrder();
        Integer response = OrderSteps.createOrder(newOrder);
        assertThat(response).isEqualTo(newOrder.getId());
    }

    @Test
    @Tag("Order")
    @Story("Order")
    @DisplayName("Find order")
    @Severity(SeverityLevel.CRITICAL)
    void findOrderTest() {
        OrderSteps.findOrder(2222)
                .body("message", is("Order not found"));
    }
}