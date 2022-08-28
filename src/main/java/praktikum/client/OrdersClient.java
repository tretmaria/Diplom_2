package praktikum.client;

import io.restassured.response.ValidatableResponse;
import io.qameta.allure.Step;
import praktikum.model.Ingredients;

import static io.restassured.RestAssured.given;

public class OrdersClient extends RestAssuredClient {
    private static final String ORDERS_PATH = "api/orders";

    @Step("Create an order")
    public ValidatableResponse createOrder(Ingredients ingredients, String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .spec(getBaseSpec())
                .body(ingredients)
                .when()
                .post(ORDERS_PATH)
                .then();
    }

    @Step("Get a list of orders")
    public ValidatableResponse getOrders(String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .spec(getBaseSpec())
                .when()
                .get(ORDERS_PATH)
                .then();
    }

}
