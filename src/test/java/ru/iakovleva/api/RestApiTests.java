package ru.iakovleva.api;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import static io.restassured.RestAssured.given;
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

public class RestApiTests extends TestBase {

    @ParameterizedTest(name = "Find by status {0}")
    @EnumSource(PetStatus.class)
    @Tag("Pet")
    @Tag("positive")
    @Tag("low")
    @Severity(SeverityLevel.NORMAL)
    void findByStatus(PetStatus status) {
        ValidatableResponse resp = PetSteps.findByStatus(status);
        if (!status.isExpectedZeroResult()) {
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
        Integer newPetId_fromResponse = PetSteps.createPet(newPet);
        assertThat(newPetId_fromResponse).isEqualTo(newPet.getId());
    }

    @Test
    @Tag("User")
    @Tag("positive")
    @Tag("smoke")
    @DisplayName("Create User")
    @Severity(SeverityLevel.BLOCKER)
    void createUserTest() {
        User newUser = DataGenerator.getUser(8, 16, true, true, true);
        String newUserId_fromResponse = UserSteps.createUser(newUser);
        assertThat(newUserId_fromResponse).isEqualTo(newUser.getId().toString());
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
        String newUserId_fromResponse = UserSteps.createUser(newUser);
        assertThat(newUserId_fromResponse).isEqualTo(newUser.getId().toString());

        // Update
        newUser.setFirstName("Vasya");
        newUser.setLastName("is here");
        UserSteps.updateUser(newUser);

        // Check
        User loadedUser = UserSteps.getUser(newUser.getUsername());
        assertThat(loadedUser).isNotNull();
        SoftAssertions.assertSoftly(s -> {
            assertThat(loadedUser.getId()).isEqualTo(newUser.getId());
            assertThat(loadedUser.getFirstName()).isEqualTo(newUser.getFirstName());
            assertThat(loadedUser.getLastName()).isEqualTo(newUser.getLastName());
        });

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
        String message_fromResponse = given(Specs.PETSTORE_REQUEST_SPEC)
                .body(newUser)
                .when()
                .post("/user/createWithArray")
                .then()
                .spec(Specs.responseSpec(500))
                .extract().path("message");
        assertThat(message_fromResponse).isEqualTo("something bad happened");
    }

    @Test
    @Tag("User")
    @Tag("negative")
    @Tag("low")
    @DisplayName("Create User with list")
    @Severity(SeverityLevel.BLOCKER)
    void createWithListTest() {
        User newUser = DataGenerator.getUser(8, 16, true, true, true);
        String type_fromResp = given(Specs.PETSTORE_REQUEST_SPEC)
                .body(newUser)
                .when()
                .post("/user/createWithList")
                .then()
                .spec(Specs.responseSpec(500))
                .extract().path("type");
        assertThat(type_fromResp).isEqualTo("unknown");
    }

    @Test
    @Tag("User")
    @Tag("negative")
    @Tag("smoke")
    @DisplayName("Get empty User")
    @Severity(SeverityLevel.TRIVIAL)
    void getEmptyUserTest() {
        given(Specs.PETSTORE_REQUEST_SPEC)
                .when()
                .get("/user/user55")
                .then()
                .spec(Specs.NOT_FOUND)
                .body("message", is("User not found"));
    }

    @Test
    @Tag("User")
    @Tag("negative")
    @Tag("smoke")
    @DisplayName("Get null User")
    @Severity(SeverityLevel.TRIVIAL)
    void getNullUserTest() {
        given(Specs.PETSTORE_REQUEST_SPEC)
                .when()
                .get("/user/")
                .then()
                .spec(Specs.responseSpec(405));
    }

    @Test
    @Tag("Order")
    @Tag("positive")
    @Tag("smoke")
    @DisplayName("Create Order")
    @Severity(SeverityLevel.NORMAL)
    void createOrderTest() {
        Order newOrder = DataGenerator.getOrder();
        Integer newOrderId = OrderSteps.createOrder(newOrder);
        assertThat(newOrderId).isEqualTo(newOrder.getId());
    }

    @Test
    @Tag("Order")
    @Story("Order")
    @DisplayName("Find order")
    @Severity(SeverityLevel.CRITICAL)
    void findOrderTest() {
        OrderSteps.findOrder(22222)
                .spec(Specs.NOT_FOUND)
                .body("message", is("Order not found"));
    }
}