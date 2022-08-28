package praktikum.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import praktikum.model.Ingredients;

import static io.restassured.RestAssured.given;

public class IngredientsClient extends RestAssuredClient {
    private static final String INGREDIENTS_PATH = "api/ingredients";

    @Step("Get ingredients")
    public ValidatableResponse getIngredients() {
        return given()
                .spec(getBaseSpec())
                //.body(ingredients)
                .when()
                .get(INGREDIENTS_PATH)
                .then();
    }
}
